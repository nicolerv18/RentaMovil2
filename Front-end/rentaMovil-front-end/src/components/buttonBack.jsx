import React from 'react';
const buttonBack = ({ onClick, type = 'button' }) => {
    return(
        <button className = "buttonBack" onClick={onClick} type={type}>
            🔙 Regresar
        </button>
    )
}
export default buttonBack;