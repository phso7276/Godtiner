// 로그인 버튼 클릭 시
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MyUpper from "../Components/MyUpper";
import "./Login.css";
import Axios from "axios";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const onChangeEmail = (e) => {
    setEmail(e.target.value);
  };

  const onChangePassword = (e) => {
    setPassword(e.target.value);
  };

  const onSubmit = (e) => {
    Axios.post("http://localhost:8080/oauth/jwt/notSocial", {
      password: password,
      email: email,
    })
      .then((response) => {
        const { accessToken } = response.data;
        Axios.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${accessToken}`;
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const navigate = useNavigate();

  return (
    <div>
      <MyUpper />
      <div className="welcome">
        <h2>
          오늘도 힘차게 <br /> 달려봐요!
        </h2>
      </div>

      <div className="idDiv">
        <input
          placeholder="이메일"
          value={email}
          className="loginId"
          onChange={onChangeEmail}
        />
      </div>
      <div className="pwDiv">
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          className="loginPw"
          onChange={onChangePassword}
        />
      </div>
      <div className="buttonDiv">
        <button type="button" className="loginButton" onClick={onSubmit}>
          로그인
        </button>
      </div>
    </div>
  );
};

export default Login;
