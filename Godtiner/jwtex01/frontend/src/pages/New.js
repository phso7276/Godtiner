// 개인 루틴을 추가하는 페이지
import React from "react";
import MyUpper from "../Components/MyUpper";
import RoutineEditor from "../Components/RoutineEditor";
import "./New.css";
const New = () => {
  return (
    <div>
      <MyUpper text={"루틴 추가하기"} />
      <RoutineEditor isEdit={false} />
    </div>
  );
};

export default New;
