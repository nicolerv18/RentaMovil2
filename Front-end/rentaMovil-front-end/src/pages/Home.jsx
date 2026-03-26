import NavbarTwo from "../components/NavbarTwo";
import  CartVehicule from "../components/CartVehicule"
import Filtrer from "../components/Filtrer";
import Banner  from "../components/Banner";
import img from "../assets/carts/mustang.jpg";
function Home(){
    return (
        <>
        <NavbarTwo/>
        <div>
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />
            <CartVehicule  name="MustangGT 500"age="2020" price="140.000" img={img} />

        </div>
        <Filtrer/>
        <Banner/>
        </> 
    )
}

export default Home;