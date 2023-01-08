import { useNavigate } from "react-router-dom";
import React from "react";
const SignupComplete = () => {
  const navigate = useNavigate();

  return (
    <div>
      <div className="welcome">
        <h2>
          Godtiner가 되신 것을 <br /> 축하드려요!
        </h2>
      </div>
      <div className="buttonDiv">
        <button className="loginButton" onClick={() => navigate("/login")}>
          로그인하러 가기
        </button>
      </div>
    </div>
  );
};

export default SignupComplete;
