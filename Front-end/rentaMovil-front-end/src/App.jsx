import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Notificacion from "./pages/notification";
import Pago from "./pages/page";

function App() {
  return (
    <BrowserRouter>
      {/* Navegación */}
      <nav>
        <Link to="/notification">Notificaciones</Link> |{" "}
        <Link to="/pago">Pago</Link>
      </nav>

      {/* Rutas */}
      <Routes>
        <Route path="/notification" element={<Notificacion />} />
        <Route path="/pago" element={<Pago />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;