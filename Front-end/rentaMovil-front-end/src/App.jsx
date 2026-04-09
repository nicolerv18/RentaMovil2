import './App.css'

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Reservation from  './pages/Reservation';
import Count from './pages/Count';
import Notificacion from "./pages/notification";
import Pago from "./pages/page";

function App() {
  return (
    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path="/reservation" element={<Reservation/>} />
        <Route path="/count" element={<Count/>} />
        <Route path="/notification" element={<Notificacion/>} />
        <Route path="/pago" element={<Pago/>} />
      </Routes>
      <Footer/>
    </BrowserRouter>
  );
}
export default App;
