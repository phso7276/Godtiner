import React, { useState } from "react";

import MyUpper from "../Components/MyUpper";
import MoveTab from "../Components/MoveTab";
import { useNavigate } from "react-router-dom";

import UserDummyData from "../DummyData/UserDummyData.json";
import "./ProfileEdit.css";

const ProfileEdit = () => {
  const navigate = useNavigate();
  const [nickName, setNickName] = useState("");
  //가입 시에 받은 nickname 넣도록! 일단 암거나
  const [profileContent, setProfileContent] = useState("");

  const onChangenickName = (e) => {
    setNickName(e.target.value);
  };

  const onChangeProfileContent = (e) => {
    setProfileContent(e.target.value);
  };

  //백이랑 통신 API 짜기 (axios)
  //일단 더미 데이터로 보내는 거
  const onSubmit = (e) => {
    UserDummyData.User.UserName = nickName;
    UserDummyData.User.UserProfileContent = profileContent;
  };

  return (
    <div>
      <MyUpper />
      <div className="ProfileEdit">
        <div className="Edit_div_pe">
          <text>닉네임</text>
          <input onChange={onChangenickName}></input>
        </div>
        <div className="Edit_div_pe">
          <text>자기소개</text>
          <input onChange={onChangeProfileContent}></input>
        </div>
        <div className="save_div_pe">
          <button className="save_pe" onClick={onSubmit}>
            저장하기
          </button>
        </div>
      </div>
      <MoveTab />
    </div>
  );
};

export default ProfileEdit;
