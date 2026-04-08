
import './App.css'
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
/* import Login from "./pages/Login"; */

import { BrowserRouter } from "react-router-dom";
function App() {
  return(
    <BrowserRouter>
     <Home/> 
   {/* 
   <Login/> */}

      
    </BrowserRouter>
  );
}

export default App;