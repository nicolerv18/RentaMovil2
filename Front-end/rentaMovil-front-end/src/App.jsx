<<<<<<< HEAD
import Home from "./pages/Home";
import React from 'react';
import './App.css'
function App() {
  return <Home />;
}
  console.log(React.version);
=======
import './App.css'
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { BrowserRouter } from "react-router-dom";

function App() {
  return(
    <BrowserRouter>
      <Navbar/>
      <Footer/>
      
    </BrowserRouter>
  );
}
>>>>>>> main

export default App;