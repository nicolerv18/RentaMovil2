import './navar.css'
import { Link } from "react-router-dom";
import {FaCar} from "react-icons/fa";
import {MdPerson} from "react-icons/md";

function Navbar(){
    return (
        <header className='navbar'>

            <div className='logo-container'>
                <h1 className='Title'>RentaMovil</h1>
                <FaCar className='icon'/>
            </div>

            <nav className='nav-links-container'>
                <Link to="/cars">Inicio</Link>
                <Link to="/pago">Notificaciones</Link>
                <Link to="/reservation">Reservación</Link>
                <MdPerson className='icon user-icon'/>
            </nav>

        </header>
    );
}

export default Navbar;