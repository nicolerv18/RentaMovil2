import NavbarTwo from "../components/NavbarTwo";
import '../pages/Home.css';
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import  CartVehicule from "../components/CartVehicule"
import Filtrer from "../components/Filtrer";
import Banner  from "../components/Banner";
import img from "../assets/carts/mustang.jpg";
function Home(){
    return (
        <>
        <Navbar/>
        <section className="home-container">
        <div className="card-vehicule-container">
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />

        </div>
        <div className="sildebar-containner">
        <div className="filtrer-container">
        <Filtrer/>
        </div>
        <div className="banner-container">
        <Banner/>
        </div>
        </div>
        </section>
        <Footer/>

        </> 
    )
}

export default Home;