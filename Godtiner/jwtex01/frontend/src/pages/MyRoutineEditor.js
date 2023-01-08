// 내 루틴에서 세부 루틴을 눌렀을 때 수정하는 페이지

import React, { useContext, useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";
import RoutineEditor from "../Components/RoutineEditor";

import MyRoutineDummyData from "../DummyData/MyRoutineDummyData.json";

const MyRoutineEditor = () => {
  const [originData, setOriginData] = useState();
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (MyRoutineDummyData.MyRoutine.length >= 1) {
      const targetRoutine = MyRoutineDummyData.MyRoutine.find(
        (it) => parseInt(it.id) === parseInt(id)
      );
      if (targetRoutine) {
        setOriginData(targetRoutine);
      } else {
        navigate("/", { replace: true });
      }
    }
  }, [id, MyRoutineDummyData]);

  return (
    <div>
      {originData && <RoutineEditor isEdit={true} originData={originData} />}
    </div>
  );
};

export default MyRoutineEditor;
