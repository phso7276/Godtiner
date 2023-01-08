import React,{ useState } from "react";
import InputBase from "@mui/material/InputBase";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import Notifications from "@mui/icons-material/Notifications";

const MySearchAlarm = () => {
  const [search, setSearch] = useState("");
  console.log(search);
  return (
    <div className="MySearchAlarm">
      <div className="SearchBar">
        <InputBase
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
          }}
          sx={{ ml: 3, flex: 1 }}
        />
        <div>
          <IconButton
            onClick={() => {
              alert("hi");
            }}
          >
            <SearchIcon />
          </IconButton>
        </div>
      </div>
      <div className="Notifications">
        <IconButton
          onClick={() => {
            alert("message");
          }}
        >
          <Notifications />
        </IconButton>
      </div>
    </div>
  );
};

export default MySearchAlarm;
