import Container from "../components/containerText";
import ButtonBack from "../components/buttonBack";

function Notification() {

    return (
        <div>
            <h1>Notificaciones</h1>

            <ButtonBack
                onClick={() => console.log("Prueba")}
              
            />
            <Container title="Recibidas">
        <p>Tienes una nueva notificación</p>
        <p>Tu reserva fue confirmada</p>
      </Container>
        </div>
    );
}




export default Notification;




