import "./notification.css";
import { useNavigate } from "react-router-dom";
import Container from "../components/containerText";
import ButtonBack from "../components/buttonBack";

function Notification() {
  const navigate = useNavigate();

  return (
    <div className="notification-page">
        <div className="line-vertical"></div>
      {/* HEADER */}
      <div className="header-page">
        <ButtonBack onClick={() => navigate(-1)} />
        <h1>Notificaciones</h1>
      </div>

      {/* CONTENIDO */}
      <div className="content">
        <Container title="Recibidas">   
  <div className="notification">
    <span className="icon">👤</span>
    <p className="text">Tienes una nueva notificación</p>
  </div>

  <div className="notification">
    <span className="icon">👤</span>
    <p className="text">Tu reserva fue confirmada</p>
  </div>
</Container>
      </div>

    </div>
  );
}

export default Notification;