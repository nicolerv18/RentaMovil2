

CREATE TABLE Branch (
  branch_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  address VARCHAR(150),
  city VARCHAR(50),
  phone VARCHAR(20),
  shedules VARCHAR(100)
);

CREATE TABLE Person (
  person_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  email VARCHAR(100),
  phone VARCHAR(20),
  branch_id INT,
  FOREIGN KEY (branch_id) REFERENCES Branch(branch_id)
);

CREATE TABLE User (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  person_id INT UNIQUE,
  username VARCHAR(50),
  password_hash VARCHAR(255),
  status VARCHAR(20),
  last_login DATETIME,
  FOREIGN KEY (person_id) REFERENCES Person(person_id)
);

CREATE TABLE Role (
  role_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  description VARCHAR(150)
);

CREATE TABLE Permission (
  permission_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  description VARCHAR(150)
);

CREATE TABLE Role_Permission (
  role_id INT,
  permission_id INT,
  PRIMARY KEY (role_id, permission_id),
  FOREIGN KEY (role_id) REFERENCES Role(role_id),
  FOREIGN KEY (permission_id) REFERENCES Permission(permission_id)
);

CREATE TABLE User_Role (
  user_id INT,
  role_id INT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (role_id) REFERENCES Role(role_id)
);

CREATE TABLE Audit (
  audit_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  action VARCHAR(100),
  affected_table VARCHAR(100),
  date DATETIME,
  detail VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);




CREATE TABLE Vehicle (
  vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
  plate VARCHAR(20),
  brand VARCHAR(50),
  model VARCHAR(50),
  year INT,
  type VARCHAR(50),
  status VARCHAR(20),
  mileage DOUBLE,
  price DOUBLE,
  branch_id INT,
  FOREIGN KEY (branch_id) REFERENCES Branch(branch_id)
);

CREATE TABLE Asignation (
  asignation_id INT AUTO_INCREMENT PRIMARY KEY,
  vehicle_id INT,
  branch_id INT,
  checkIn_date DATE,
  checkOut_date DATE,
  FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id),
  FOREIGN KEY (branch_id) REFERENCES Branch(branch_id)
);

CREATE TABLE Location (
  location_id INT AUTO_INCREMENT PRIMARY KEY,
  latitude VARCHAR(50),
  longitude VARCHAR(50),
  timestamp DATETIME,
  vehicle_id INT,
  FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);

CREATE TABLE Route (
  route_id INT AUTO_INCREMENT PRIMARY KEY,
  location_id INT,
  distance DOUBLE,
  estimated_time DOUBLE,
  origin VARCHAR(100),
  destination VARCHAR(100),
  FOREIGN KEY (location_id) REFERENCES Location(location_id)
);

CREATE TABLE Vehicle_Status_History (
  history_id INT AUTO_INCREMENT PRIMARY KEY,
  vehicle_id INT,
  status VARCHAR(20),
  start_date DATE,
  end_date DATE,
  FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
CREATE TABLE Reservation (
  reservation_id INT AUTO_INCREMENT PRIMARY KEY,
  client_id INT,
  reservation_date DATE,
  status VARCHAR(20),
  FOREIGN KEY (client_id) REFERENCES Person(person_id)
);

CREATE TABLE Reservation_Detail (
  detail_id INT AUTO_INCREMENT PRIMARY KEY,
  reservation_id INT,
  vehicle_id INT,
  ResponsibleName VARCHAR(100),
  start_date DATE,
  end_date DATE,
  pickupTime TIME,
  returnTime TIME,
  pickupLocation VARCHAR(100),
  returnLocation VARCHAR(100),
  amountToPay DOUBLE,
  totalValue DOUBLE,
  FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),
  FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
CREATE TABLE Gps (
  gps_id INT AUTO_INCREMENT PRIMARY KEY,
  serial VARCHAR(50),
  model VARCHAR(50),
  available BOOLEAN
);

CREATE TABLE Rental (
  rental_id INT AUTO_INCREMENT PRIMARY KEY,
  reservation_id INT UNIQUE,
  route_id INT,
  location_id INT,
  gps_id INT,
  status VARCHAR(20),
  start_date DATE,
  end_date DATE,
  FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),
  FOREIGN KEY (route_id) REFERENCES Route(route_id),
  FOREIGN KEY (location_id) REFERENCES Location(location_id),
  FOREIGN KEY (gps_id) REFERENCES Gps(gps_id)
);

CREATE TABLE Contract (
  contract_id INT AUTO_INCREMENT PRIMARY KEY,
  start_date DATE,
  end_date DATE,
  reservation_id INT UNIQUE,
  contract_date DATE,
  terms VARCHAR(255),
  amount DOUBLE,
  description VARCHAR(255),
  FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id)
);

CREATE TABLE Maintenance_Type (
  maintenance_type_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  description VARCHAR(150)
);

CREATE TABLE Vehicle_Maintenance (
  maintenance_id INT AUTO_INCREMENT PRIMARY KEY,
  vehicle_id INT,
  maintenance_type_id INT,
  start_date DATE,
  end_date DATE,
  status VARCHAR(20),
  FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id),
  FOREIGN KEY (maintenance_type_id) REFERENCES Maintenance_Type(maintenance_type_id)
);

CREATE TABLE Insurance (
  insurance_id INT AUTO_INCREMENT PRIMARY KEY,
  vehicle_id INT UNIQUE,
  policy VARCHAR(100),
  insurance VARCHAR(100),
  start_date DATE,
  end_date DATE,
  status VARCHAR(20),
  FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);


CREATE TABLE Payment_Method (
  method_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50)
);

CREATE TABLE Payment (
  payment_id INT AUTO_INCREMENT PRIMARY KEY,
  reservation_id INT,
  payment_date DATE,
  amount DOUBLE,
  method_id INT,
  FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),
  FOREIGN KEY (method_id) REFERENCES Payment_Method(method_id)
);


CREATE TABLE Notification (
  notification_id INT AUTO_INCREMENT PRIMARY KEY,
  person_id INT,
  message VARCHAR(255),
  sent_date DATETIME,
  is_read BOOLEAN,
  reservation_id INT,
  maintenance_id INT,
  payment_id INT,
  contract_id INT,
  insurance_id INT,
  FOREIGN KEY (person_id) REFERENCES Person(person_id),
  FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),
  FOREIGN KEY (maintenance_id) REFERENCES Vehicle_Maintenance(maintenance_id),
  FOREIGN KEY (payment_id) REFERENCES Payment(payment_id),
  FOREIGN KEY (contract_id) REFERENCES Contract(contract_id),
  FOREIGN KEY (insurance_id) REFERENCES Insurance(insurance_id)
);


