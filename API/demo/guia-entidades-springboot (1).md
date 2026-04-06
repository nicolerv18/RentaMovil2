# Guía: Cómo crear el CRUD completo de una entidad en Spring Boot N-Capas

## Flujo general

```
Diagrama de clases
      ↓
1. Model (@Entity)
      ↓
2. Repository
      ↓
3. DTO
      ↓
4. Service (interfaz + implementación)
      ↓
5. Controller
      ↓
6. Probar en Thunder Client
```

---

## Paso 1 — Model (@Entity)

Cada clase del diagrama se convierte en una `@Entity`.

### Reglas clave

- Si la clase **no hereda** de nadie → `@Entity` simple
- Si la clase **hereda** de otra → usar `@Inheritance(strategy = InheritanceType.JOINED)` en el padre y `@DiscriminatorValue` en los hijos
- Los atributos simples (String, Double, int) → `@Column`
- Las relaciones con otras clases → anotaciones JPA

### Tipos de relaciones

| Diagrama | Anotación | Cuándo usarla |
|---|---|---|
| Un vehículo tiene muchos mantenimientos | `@OneToMany` | Un padre, muchos hijos |
| Muchos mantenimientos pertenecen a un vehículo | `@ManyToOne` | Muchos hijos, un padre |
| Una reserva genera un contrato | `@OneToOne` | Uno a uno |
| Muchos clientes tienen muchos roles | `@ManyToMany` | Muchos a muchos |

### Composición vs Agregación

```java
// Composición (*--) → el hijo no existe sin el padre
// Si se borra el padre, se borran los hijos
@OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Route> routes;

// Agregación (o--) → el hijo existe independientemente
// Si se borra el padre, el hijo sobrevive
@ManyToOne
@JoinColumn(name = "gps_id")
private Gps gps;
```

### Palabras reservadas en H2

Evita estos nombres de tabla porque H2 los rechaza:
- `user` → usa `users`
- `order` → usa `orders`
- `group` → usa `groups`
- `vehicle` → usa `vehicles`

### Evitar loops infinitos con Lombok

Cuando tienes relaciones bidireccionales, `@Data` puede causar loops. Agrega siempre:

```java
@ToString(exclude = {"relacion1", "relacion2"})
@EqualsAndHashCode(exclude = {"relacion1", "relacion2"})
```

### Ejemplo completo

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "vehicleType")
@ToString(exclude = {"maintenances", "insurance", "branch"})
@EqualsAndHashCode(exclude = {"maintenances", "insurance", "branch"})
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String plate;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Maintenance> maintenances;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private Insurance insurance;
}
```

---

## Paso 2 — Repository

El Repository es la capa que se comunica con la base de datos. Spring genera el SQL automáticamente leyendo el nombre del método.

### Métodos gratuitos de JpaRepository

```java
save(entity)        // insertar o actualizar
findById(id)        // buscar por ID
findAll()           // traer todos
deleteById(id)      // eliminar por ID
existsById(id)      // verificar si existe
count()             // contar registros
```

### Métodos personalizados

Spring lee el nombre del método y genera el SQL:

```java
// El nombre del método DEBE coincidir exactamente con el campo en la entidad
Optional<Vehicle> findByPlate(String plate);
// → SELECT * FROM vehicles WHERE plate = ?

List<Vehicle> findByStatus(String status);
// → SELECT * FROM vehicles WHERE status = ?

boolean existsByPlate(String plate);
// → SELECT COUNT(*) > 0 FROM vehicles WHERE plate = ?

List<Vehicle> findByBrandAndStatus(String brand, String status);
// → SELECT * FROM vehicles WHERE brand = ? AND status = ?
```

### Usa `boolean` no `Boolean`

```java
// ❌ puede ser null → SonarQube se queja
Boolean existsByPlate(String plate);

// ✅ nunca es null → correcto
boolean existsByPlate(String plate);
```

### Ejemplo completo

```java
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlate(String plate);
    List<Vehicle> findByStatus(String status);
    List<Vehicle> findByBranch(Branch branch);
    boolean existsByPlate(String plate);
}
```

---

## Paso 3 — DTOs

Los DTOs controlan qué datos entran y salen de la API.

### ¿Por qué no usar la entidad directamente?

- Evitas exponer campos sensibles (password, campos internos)
- Controlas exactamente qué recibe y qué devuelve el cliente
- Puedes tener diferentes vistas del mismo objeto

### Tipos de DTOs

| DTO | Para qué sirve | Cuándo se usa |
|---|---|---|
| `CreateVehicleDTO` | Datos que llegan al crear | POST |
| `UpdateVehicleDTO` | Datos que llegan al actualizar | PUT |
| `VehicleDTO` | Datos que salen como respuesta | GET, POST, PUT |

### Ejemplo

```java
// Lo que el admin envía al crear un vehículo
@Data
public class CreateVehicleDTO {
    private String brand;
    private String model;
    private String plate;
    private Integer year;
    private String fuelType;
    private Double price;
    private String vehicleType;  // "heavy", "casual", "offroad"
    private Long branchId;
}

// Lo que el servidor devuelve
@Data
@JsonPropertyOrder({"id", "brand", "model", "plate", "year", "status", "price"})
public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private String plate;
    private Integer year;
    private String fuelType;
    private String status;
    private Double price;
    private String vehicleType;
    // NO incluir relaciones completas, solo IDs si los necesitas
}
```

---

## Paso 4 — Service

El Service tiene dos partes: la interfaz y la implementación.

### La interfaz define QUÉ métodos existen

```java
public interface VehicleService {
    VehicleDTO create(CreateVehicleDTO dto);
    VehicleDTO getById(Long id);
    List<VehicleDTO> getAll();
    List<VehicleDTO> getByStatus(String status);
    VehicleDTO update(Long id, UpdateVehicleDTO dto);
    void delete(Long id);
}
```

### La implementación define CÓMO funcionan

```java
@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final BranchRepository branchRepository;

    // Inyección por constructor (buena práctica)
    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                               BranchRepository branchRepository) {
        this.vehicleRepository = vehicleRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public VehicleDTO create(CreateVehicleDTO dto) {
        // 1. Validar que no exista la placa
        if (vehicleRepository.existsByPlate(dto.getPlate())) {
            throw new DuplicateResourceException("Plate already registered");
        }

        // 2. Buscar la sucursal
        Branch branch = branchRepository.findById(dto.getBranchId())
            .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        // 3. Crear el vehículo según el tipo
        Vehicle vehicle = switch (dto.getVehicleType()) {
            case "heavy" -> new HeavyVehicle();
            case "casual" -> new CasualVehicle();
            case "offroad" -> new OffRoadVehicle();
            default -> throw new RuntimeException("Invalid vehicle type");
        };

        // 4. Asignar atributos
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setPlate(dto.getPlate());
        vehicle.setYear(dto.getYear());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setPrice(dto.getPrice());
        vehicle.setStatus("AVAILABLE");
        vehicle.setBranch(branch);

        // 5. Guardar y devolver
        return toDTO(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleDTO getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        return toDTO(vehicle);
    }

    @Override
    public List<VehicleDTO> getAll() {
        return vehicleRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found");
        }
        vehicleRepository.deleteById(id);
    }

    // Convierte Vehicle → VehicleDTO
    private VehicleDTO toDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setPlate(vehicle.getPlate());
        dto.setYear(vehicle.getYear());
        dto.setStatus(vehicle.getStatus());
        dto.setPrice(vehicle.getPrice());
        // Detectar tipo con instanceof
        dto.setVehicleType(vehicle instanceof HeavyVehicle ? "heavy" :
                           vehicle instanceof CasualVehicle ? "casual" : "offroad");
        return dto;
    }
}
```

### Los métodos del diagrama van en el Service

```java
// Si el diagrama tiene:
// Vehicle { +cambiarEstado() }

// Va aquí, no en la entidad
public VehicleDTO changeStatus(Long id, String newStatus) {
    Vehicle vehicle = vehicleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    vehicle.setStatus(newStatus);
    return toDTO(vehicleRepository.save(vehicle));
}
```

---

## Paso 5 — Controller

El Controller expone los endpoints REST y delega todo al Service.

### Códigos HTTP

| Situación | Código |
|---|---|
| Recurso creado | `201 CREATED` |
| Consulta exitosa | `200 OK` |
| Eliminación exitosa | `204 NO CONTENT` |
| No encontrado | `404 NOT FOUND` |
| Datos duplicados | `409 CONFLICT` |
| Datos inválidos | `400 BAD REQUEST` |
| Error del servidor | `500 INTERNAL SERVER ERROR` |

### Ejemplo completo

```java
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Primero los endpoints con rutas fijas
    @PostMapping
    public ResponseEntity<VehicleDTO> create(@RequestBody CreateVehicleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAll() {
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<VehicleDTO>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(vehicleService.getByStatus(status));
    }

    // Al final los endpoints con variables /{id}
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> update(@PathVariable Long id,
                                              @RequestBody UpdateVehicleDTO dto) {
        return ResponseEntity.ok(vehicleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

> ⚠️ **Importante:** Los endpoints con rutas fijas (`/status/{status}`) siempre deben ir **antes** que los de variables (`/{id}`). Si no, Spring confunde la ruta fija con el ID.

---

## Paso 6 — Probar en Thunder Client

### Orden de pruebas recomendado

1. **POST** → crear el recurso
2. **GET** → listar todos y verificar que se creó
3. **GET /{id}** → buscar el que creaste
4. **PUT /{id}** → actualizar y verificar cambios
5. **DELETE /{id}** → eliminar y verificar que desapareció
6. **Casos de error** → intentar crear duplicado, buscar ID inexistente

### Configuración en Thunder Client

- Method: `POST`, `GET`, `PUT`, `DELETE`
- Headers: `Content-Type: application/json`
- Body: seleccionar **JSON**

### Ejemplo de pruebas para Vehicle

```
POST http://localhost:8080/api/vehicles
{
    "brand": "Toyota",
    "model": "Corolla",
    "plate": "ABC123",
    "year": 2022,
    "fuelType": "Gasolina",
    "price": 150.0,
    "vehicleType": "casual",
    "branchId": 1
}

GET http://localhost:8080/api/vehicles
GET http://localhost:8080/api/vehicles/1
GET http://localhost:8080/api/vehicles/status/AVAILABLE
DELETE http://localhost:8080/api/vehicles/1
```

---

## Consejos generales

### Orden de creación de entidades

Crea las entidades de **menos dependencias a más**. Primero las que no dependen de nadie, al final las que dependen de todo.

Para este proyecto:
1. `Branch` → sin dependencias
2. `Vehicle` + subclases → depende de `Branch`
3. `Maintenance` → depende de `Vehicle`
4. `Insurance` → depende de `Vehicle`
5. `GPS` → independiente
6. `Reservation` → depende de `Customer` y `Vehicle`
7. `Contract` → depende de `Reservation`
8. `Payment` → depende de `Contract`
9. `Route` → depende de `Rental` y `GPS`
10. `Rental` → depende de casi todo

### Los patrones de diseño NO son entidades

| Patrón | Dónde va |
|---|---|
| Observer (`NotificationService`) | `@Service` |
| Command (`CreateReservationCommand`) | Carpeta `service/command/` |
| Strategy (`CreditCard`, `DebitCard`) | Carpeta `service/payment/` |
| Iterator (`MaintenanceIterator`) | Carpeta `service/iterator/` |
| Singleton (`VehicleManager`) | Spring lo maneja con `@Service` |

### NotificationService en Spring

```java
// No necesitas implementar el Singleton manualmente
// Spring ya lo hace con @Service → una sola instancia
@Service
public class NotificationService {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) { observers.add(o); }
    public void removeObserver(Observer o) { observers.remove(o); }
    public void notify(String message) {
        observers.forEach(o -> o.update(message));
    }
}
```

### Errores comunes

| Error | Causa | Solución |
|---|---|---|
| `Table not found` | Nombre de tabla es palabra reservada | Cambiar nombre en `@Table(name = "...")` |
| `No property found` | Método del repository no coincide con campo | Verificar nombre exacto del campo en la entidad |
| `Method Not Allowed 405` | Método HTTP incorrecto | Verificar que sea POST, GET, PUT o DELETE |
| `Unsupported Media Type 415` | Falta `Content-Type` | Agregar header `Content-Type: application/json` |
| `StackOverflow` en relaciones | Loop entre entidades con `@Data` | Agregar `@ToString(exclude=...)` y `@EqualsAndHashCode(exclude=...)` |
| `OneToOne` con `@Column` | Relación mal anotada | Cambiar `@Column` por `@JoinColumn` |
| Endpoint 404 | Rutas fijas después de variables | Poner rutas fijas antes que `/{id}` |
