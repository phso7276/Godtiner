import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import MyUpper from "../Components/MyUpper";
import Avatar from "react-avatar-edit";
import "./Mypage.css";

import MoveTab from "../Components/MoveTab";
import { width } from "@mui/system";

import UserDummyData from "../DummyData/UserDummyData.json";

const Mypage = () => {
  const navigate = useNavigate();

  const [Image, setImage] = useState(UserDummyData.User.UserProfileImg);
  const fileInput = useRef(null);

  const onChangeImg = (e) => {
    if (e.target.files[0]) {
      setImage(e.target.files[0]);
    } else {
      //업로드 취소할 시
      setImage(UserDummyData.User.UserProfileImg);
      return;
    }

    //화면에 프로필 사진 표시
    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2) {
        setImage(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  return (
    <div className="myPageCss">
      <img
        className="profileImg"
        src={Image}
        size={100}
        onClick={() => {
          fileInput.current.click();
        }}
      />
      <input
        type="file"
        style={{ display: "none" }}
        accept="image/jpg,impge/png,image/jpeg"
        name="profile_img"
        onChange={onChangeImg}
        ref={fileInput}
      />
      <div className="userName_mp">{UserDummyData.User.UserName}</div>
      <div className="userEmail_mp">{UserDummyData.User.UserEmail}</div>
      <div className="content_mp">{UserDummyData.User.UserProfileContent}</div>
      <button
        className="profileEdit"
        onClick={() => {
          navigate("/profileedit");
        }}
      >
        프로필 편집
      </button>
      <div className="alertEdit">알림 설정</div>
      <div className="editButton">
        <button className="editButton1">계정 설정</button>

        <button className="editButton1">찜한 루틴 편집</button>

        <button className="editButton1">공유한 루틴 편집</button>
      </div>
      <div>
        <button className="logoutButton">로그아웃</button>
      </div>
      <MoveTab />
    </div>
  );
};
export default Mypage;
