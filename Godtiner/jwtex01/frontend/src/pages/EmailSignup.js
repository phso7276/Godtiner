import { useNavigate } from "react-router-dom";
import React, { useState } from "react";

import MyUpper from "../Components/MyUpper";
import Popup from "../Components/Popup";

import Axios from "axios";

const EmailSignup = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userName, setUserName] = useState("");
  const [nickName, setNickName] = useState("");

  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [userNameError, setUserNameError] = useState(false);
  const [nickNameError, setNickNameError] = useState(false);

  const [popup, setPopup] = useState({
    open: false,
    title: "",
    message: "",
    callback: false,
  });

  const onChangeUserName = (e) => {
    setUserNameError(false);
    setUserName(e.target.value);
  };

  const onChangeNickName = (e) => {
    setNickNameError(false);
    setNickName(e.target.value);
  };

  const onChangeEmail = (e) => {
    const emailRegex =
      /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
    if (!e.target.value || emailRegex.test(e.target.value))
      setEmailError(false);
    else setEmailError(true);
    setEmail(e.target.value);
  };

  const onChangePassword = (e) => {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    if (!e.target.value || passwordRegex.test(e.target.value))
      setPasswordError(false);
    else setPasswordError(true);
    setPassword(e.target.value);
  };

  const onSubmit = (e) => {
    if (!validation()) {
      setPopup({
        open: true,
        title: "Error",
        message: "내용을 올바르게 입력해 주세요 .",
      });
      return;
    } else {
      Axios.post("http://localhost:8080/join", {
        password: password,
        email: email,
        name: userName,
        nickname: nickName,
      })
        .then(function (response) {
          console.log(response.data);
          if (response.data === "회원가입 완료") {
            alert("회원가입 완료");
            window.location.href = "http://localhost:3000/login";

            //window.location.href("/signupcomplete");
          } else {
            let message = response.data.message;
            if (response.data === "이메일 중복") {
              console.log(response);
              alert("이메일 중복");
              window.location.href = "http://localhost:3000/emailsignup";
              message =
                "User ID is duplicated. Please enter a different User ID. ";
            }

          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  };

  const validation = () => {
    if (!password) setPasswordError(true);
    if (!userName) setUserNameError(true);
    if (!nickName) setNickNameError(true);
    if (!email) setEmailError(true);

    if (password && userName && email && nickName) return true;
    else return false;
  };

  return (
    <div>
      <MyUpper />

      <div className="welcome">
        <h2>
          Godtiner님, <br /> 가입을 환영해요!
        </h2>
      </div>

      <div className="userNameDiv">
        <input
          placeholder="유저명"
          className="userName"
          value={userName}
          onChange={onChangeUserName}
        />
        {userNameError && (
          <div className="invalid-input">유저명을 입력해주세요</div>
        )}
      </div>

      <div className="nickNameDiv">
        <input
          placeholder="닉네임"
          className="nickName"
          value={nickName}
          onChange={onChangeNickName}
        />
        {nickNameError && (
          <div className="invalid-input">닉네임을 입력해주세요</div>
        )}
      </div>

      <div className="idDiv">
        <input
          placeholder="이메일"
          className="loginId"
          value={email}
          onChange={onChangeEmail}
        />
        {emailError && (
          <div className="invalid-input">유효한 이메일을 입력해주세요</div>
        )}
      </div>
      <div className="pwDiv">
        <input
          type="password"
          placeholder="비밀번호"
          className="loginPw"
          value={password}
          onChange={onChangePassword}
        />
        {passwordError && (
          <div className="invalid-input">
            비밀번호는 1개 이상의 숫자와 문자를 포함하여 8자 이상이어야 합니다.
          </div>
        )}
      </div>
      <div className="buttonDiv">
        <button type="button" className="loginButton" onClick={onSubmit}>
          회원가입
        </button>
      </div>
      <Popup
        open={popup.open}
        setPopup={setPopup}
        message={popup.message}
        title={popup.title}
        callback={popup.callback}
      />
    </div>
  );
};

export default EmailSignup;
