
import './App.css'
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { BrowserRouter } from "react-router-dom";

function App() {
  return(
    <BrowserRouter>
     <Home/>
      
    </BrowserRouter>
  );
}

export default App;