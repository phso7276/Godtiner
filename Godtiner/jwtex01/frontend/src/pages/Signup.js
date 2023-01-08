import React, { useState } from "react";
import "./Signup.css";
import { useNavigate } from "react-router-dom";
import LoginGoogle from "../Components/LoginGoogle";
import Axios from "axios";

const config = {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("jwtToken"),
  },
};

function Signup() {
  const [user, setUser] = useState(null);

  const getUser = async () => {
    let res = await Axios.get("http://localhost:8080/user", config);
    setUser(res.data);
  };

  const navigate = useNavigate();

  return (
    <div className="Signup">
      <h2>
        일상을 굴리는 톱니바퀴, <br /> GODTINER와 함께해요
      </h2>

      <img className="mainImg" src="img/Signup_image1.png" alt="Signup_img" />

      <div className="button1">
        <LoginGoogle />
      </div>

      <div className="button2">
        <button
          className="w-btn w-btn-indigo"
          type="button"
          onClick={() => navigate("/emailsignup")}
        >
          이메일로 계정 만들기
        </button>
      </div>

      <div className="button3">
        <h1>이미 계정이 있으신가요?</h1>
        <button
          className="w-btn w-btn-blue"
          type="button"
          onClick={() => navigate("/login")}
        >
          로그인
        </button>
      </div>
    </div>
  );
}

export default Signup;
