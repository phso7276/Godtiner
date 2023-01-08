// 피드페이지
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Axios from "axios";

import feedDummyData from "../DummyData/feedDummyData.json";
import "./Feed.css";
import MoveTab from "../Components/MoveTab";

const Feed = () => {
  const navigate = useNavigate();
  const [AllRoutines, setAllRoutines] = useState();

  useEffect(() => {
    Axios.get("http://localhost:8080/feed/all")
      .then((Response) => {
        console.log(Response.data);
        setAllRoutines(Response.data);
      })
      .catch((Error) => {
        console.log(Error);
      });
  }, []);

  return (
    <div>
      <h2 style={{ fontSize: "40px" }}>🔍최신 루틴</h2>
      <div className="Routine_list">
        {AllRoutines.content.map((it) => (
          <div
            className="RoutineItem"
            key={it.id}
            onClick={() => navigate(`/routine/${it.id}`)}
          >
            <img className="feedImg" src={it.thumbnailUrl}></img>
            <br />
            <text className="feedTitle">{it.title}</text>
            <div className="feedTag">
              #{it.tag[0]} #{it.tag[1]}
            </div>
            <div>
              <div className="feedback">
                ❤{it.likecnt} 📥{it.pickcnt} 👀{it.hits}
              </div>
            </div>
          </div>
        ))}
      </div>
      <MoveTab />
    </div>
  );
};

export default Feed;
