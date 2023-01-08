import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import MySearchAlarm from "../Components/MySearchAlarm";
import "./Home.css";
import Calendar from "../Components/Calendar";
import MoveTab from "../Components/MoveTab";
import RoutineItem from "../Components/RoutineItem";
import {
  IoIosAddCircle,
  IoIosShare,
  IoMdReorder,
  IoIosCalendar,
  IoMdArrowBack,
} from "react-icons/io";
import MyRoutineDummyData from "../DummyData/MyRoutineDummyData.json";

const Home = () => {
  const navigate = useNavigate();
  const [name, setName] = useState("내 루틴");

  return (
    <div className="Home">
      <MySearchAlarm />
      <Calendar />
      <IoMdReorder size="50" />
      <IoIosShare
        size="50"
        color=""
        onClick={() => navigate("/shareroutine")}
      />
      <div>
        {/* 일부러 위쪽으로 올림. 확인하기 위해서 */}
        <IoIosAddCircle
          size="80"
          color="blue"
          position="fixed"
          right="50px"
          botton="300px"
          onClick={() => navigate("/new")}
        />
      </div>
      <div className="routinename">
        <input
          className="routine_name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
      </div>
      <div className="routinSection">
        {MyRoutineDummyData.MyRoutine.map((it) => (
          <RoutineItem key={it.id} {...it} />
        ))}
      </div>
      {/* moveTap의 크기가 너무 커서 자꾸 마지막 루틴이 안보임. 크기조절 해야할 듯 */}
      {/* <MoveTab /> */}
    </div>
  );
};
export default Home;
