import React, { useContext, useState } from "react";
import { RoutineStateContext, ShareStateContext } from "../App";

const ShareRoutine = () => {
  const [name, setName] = useState("");
  const [content, setContent] = useState("");
  const [checkedList, setCheckedList] = useState([]);

  // const onCheckedElement = (checked, id, startTime, endTime, content) => {
  //   let newArr = [id, startTime, endTime, content];
  //   if (checked) {
  //     setCheckedList([...checkedList, newArr]);
  //   } else {
  //     setCheckedList(checkedList.filter((it) => it[0] !== id));
  //   }
  // };

  // const onCheckedAll = (checked) => {
  //   if (checked) {
  //     const listArr = [];
  //     routineList.forEach((it) => listArr.push(it));
  //     setCheckedList(listArr);
  //   } else {
  //     setCheckedList([]);
  //   }
  // };

  // console.log(checkedList);

  return (
    <div>
      <div>
        <h3>루틴이름</h3>
        <input value={name} onChange={(e) => setName(e.target.value)} />
      </div>
      <div>
        <h3>루틴 소개글</h3>
        <textarea
          placeholder="이 루틴은 어떤 루틴인가요?"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
      </div>

      <div>
        <h3>공개 루틴 상세 설정</h3>
        {/* <input
          type="checkbox"
          onChange={(e) => {
            onCheckedAll(e.target.checked);
          }}
        /> */}
        {/* {routineList.map((it) => (
          <div>
            <div>
              {it.startTime}-{it.endTime}
            </div>
            <div>{it.content}</div>

            <input
              type="checkbox"
              key={it.id}
              onChange={(e) =>
                onCheckedElement(
                  e.target.checked,
                  it.id,
                  it.startTime,
                  it.endTime,
                  it.content
                )
              }
            />
          </div>
        ))} */}
      </div>
      <button>공유하기</button>
    </div>
  );
};

export default ShareRoutine;
