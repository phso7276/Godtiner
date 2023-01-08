import { useNavigate } from "react-router-dom";
import React from "react";

const MoveTab = () => {
  const navigate = useNavigate();

  return (
    <footer>
      <div>
        <button className="Home_btn" onClick={() => navigate("/home")}>
          🗓️
        </button>
      </div>
      <div>
        <button
          className="Recommend_btn"
          onClick={() => navigate("/recommend")}
        >
          👍
        </button>
      </div>
      <div>
        <button className="Feed_btn" onClick={() => navigate("/feed")}>
          🔍
        </button>
      </div>
      <div>
        <button className="Mission_btn" onClick={() => navigate("/mission")}>
          🏆
        </button>
      </div>
      <div>
        <button className="Mypage_btn" onClick={() => navigate("/mypage")}>
          😀
        </button>
      </div>
    </footer>
  );
};

export default MoveTab;
