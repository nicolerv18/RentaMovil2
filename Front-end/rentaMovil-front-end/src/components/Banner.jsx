import '../components/Banner.css'
import img1 from "../assets/img/img1.png"
import img2 from "../assets/img/img2.png"
import img3 from "../assets/img/img3.webp"

function Banner(){
    return(
<div className="container-banner">
        <div className="slides">
            <div className="slide">
                <img src={img1} alt="imagen de RentaMovil" />
            </div>
            <div className="slide">
                <img src={img2} alt="imagen de RentaMovil" />
            </div>
            <div className="slide">
                <img src={img3} alt="imagen de RentaMovil" />
            </div>
    
    </div>
    <p className="text-banner">Disfruta la libertad de moverte cuando quieras con vehículos cómodos, seguros y en excelente estado. Ofrecemos alquiler rápido, precios accesibles y planes flexibles que se adaptan a tu viaje, ya sea por trabajo o placer. Elige tu vehículo ideal y conduce sin preocupaciones.</p>
</div>
    );
}
export default Banner;