//상세루틴 편집하는 컴포넌트
import React from "react";
import { useEffect, useRef, useState } from "react";
//import mui
import Switch from "@mui/material/Switch";
import { useNavigate } from "react-router-dom";

import MyRoutineDummyData from "../DummyData/MyRoutineDummyData.json";

import "./RoutineEditor.css";

const RoutineEditor = ({ isEdit, originData }) => {
  //세부루틴 이름
  const [content, setContent] = useState("");
  //알림설정
  const [onOff, setOnOff] = useState(false);
  //시작시간
  const [startTime, setStartTime] = useState("");
  //끝나는 시간
  const [endTime, setEndTime] = useState("");

  const contentRef = useRef();
  const navigate = useNavigate();

  //더미데이터가 2개까지 있으므로
  const dataId = useRef(3);

  useEffect(() => {
    if (isEdit) {
      setStartTime(originData.startTime);
      setEndTime(originData.endTime);
      setContent(originData.content);
    }
  }, [isEdit, originData]);

  const handleSubmit = () => {
    let newRoutine = [];

    if (content.length < 1) {
      contentRef.current.focus();
      return;
    }
    if (!isEdit) {
      let newData = {
        id: dataId.current,
        startTime,
        endTime,
        content,
      };
      newRoutine = [...MyRoutineDummyData.MyRoutine, newData];
      MyRoutineDummyData.MyRoutine = newRoutine;

      dataId.current += 1;
    } else {
      let new_data = {
        id: originData.id,
        startTime,
        endTime,
        content,
      };
      const getIndex = MyRoutineDummyData.MyRoutine.indexOf(originData);
      MyRoutineDummyData.MyRoutine[getIndex] = new_data;
    }
    navigate("/home", { replace: true });
  };

  return (
    <div className="RoutineEditor">
      <section>
        <div className="RoutinName">
          <input
            className="NameBox"
            ref={contentRef}
            value={content}
            onChange={(e) => setContent(e.target.value)}
          />
        </div>
        <div className="Notification">
          <span>알림 설정</span>
          <Switch
            checked={onOff}
            onChange={(e) => {
              setOnOff(e.target.checked);
            }}
          />
        </div>

        <div className="timeDay">
          <span>시간 설정 </span>
          <input
            type="time"
            value={startTime}
            onChange={(e) => setStartTime(e.target.value)}
          />
          <input
            type="time"
            value={endTime}
            onChange={(e) => setEndTime(e.target.value)}
          />
        </div>
        <div>
          <span>요일 반복</span>
        </div>
      </section>
      <button onClick={handleSubmit}>{isEdit ? "수정하기" : "추가하기"}</button>
    </div>
  );
};
export default RoutineEditor;
