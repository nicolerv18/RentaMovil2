const containerText = ({ title, children }) => {
  return (
    <div className="container">
      {title && <h2 className="container-title">{title}</h2>}
      <div className="container-content">
        {children}
      </div>
    </div>
  );
};

export default containerText;