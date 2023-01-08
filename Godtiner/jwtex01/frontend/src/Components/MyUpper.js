import { useNavigate } from "react-router-dom";
import React from "react";

const MyUpper = ({ text }) => {
  const navigate = useNavigate();
  return (
    <div className="MyUpper">
      <button
        className="backButton"
        onClick={() => navigate(-1)}
        style={{ padding: "20px" }}
      >
        <img
          src="../img/arrow.png"
          alt="arrow"
          style={{ width: "30px", height: "30px" }}
        />
      </button>
      <span className="pageDescript" style={{ fontSize: "30px" }}>
        {text}
      </span>
    </div>
  );
};

export default MyUpper;
