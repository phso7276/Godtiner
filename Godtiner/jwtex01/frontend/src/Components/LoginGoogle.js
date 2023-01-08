import React from "react";
import { GoogleLogin } from "react-google-login";
import Axios from "axios";

const config = {
  headers: {
    "Content-Type": "application/json; charset=utf-8",
  },
};

const responseGoogle = async (response) => {
  console.log(1, response);
  let jwtToken = await Axios.post(
    "http://localhost:8080/oauth/jwt/google",
    JSON.stringify(response),
    config
  );
  if (jwtToken.status === 200) {
    console.log(2, jwtToken.data);
    localStorage.setItem("jwtToken", jwtToken.data);
  }
};

const LoginGoogle = () => {
  return (
    <GoogleLogin
      clientId="1031066264934-g58khhhkkf5hbqtbh5e4l3n2ovbrir0r.apps.googleusercontent.com"
      buttonText="Login"
      onSuccess={responseGoogle}
      onFailure={responseGoogle}
      cookiePolicy={"single_host_origin"}
    />
  );
};

export default LoginGoogle;
