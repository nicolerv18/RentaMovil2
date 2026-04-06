import './App.css'
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Reservation from  './pages/Reservation';
import Count from './pages/Count';

function App() {
  return(
    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route path="/reservation" element={<Reservation/>} />
        <Route path="/count" element={<Count/>} />
      </Routes>
      <Footer/>
    </BrowserRouter>
  );
}

export default App;