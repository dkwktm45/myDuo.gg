import React, { useState } from "react";
import styled, { css } from "styled-components";

const Postitem = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid black;
  padding: 10px 0;


  .PostitemBox {
  
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items:center;
  }
  .PostitemBox > div {
    width: 100%;
  }
  .BntBox{
    display: flex;
    flex-direction: row;
    margin: 0 1px;
  }
  .BntBox > input {
    border: 1px solid white;
    width:100%
    padding: 0;
    background-color: ${(props) => props.theme.lolTextColor};
    cursor: pointer;
    border-radius: 5px;
    

  }
  .positionBox {
    display: flex;
    flex-direction: row;
    justify-content:center;
  }
  .positionBox > div >img {
    width: 40px;
  height: 40px;
    border: 1px solid black;
    
  }
`;
const ToggleBtn = styled.button`
  width: 70px;
  height: 30px;
  border-radius: 30px;
  border: none;
  cursor: pointer;
  background-color: ${(props) => (!props.toggle ? "#ccc" : "rgb(51,30,190)")};
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.5s ease-in-out;
`;
const Circle = styled.div`
  background-color: white;
  width: 25px;
  height: 25px;
  border-radius: 50px;
  position: absolute;
  left: 5%;
  transition: all 0.5s ease-in-out;

  ${(props) =>
    props.toggle &&
    css`
      transform: translate(30px, 0);
      transition: all 0.5s ease-in-out;
    `}
`;

function MyPosts() {
  const [toggle, setToggle] = useState(false);
  const clickedToggle = () => {
    setToggle((prev) => !prev);
  };
  return (
    <Postitem>
      <div className="PostitemBox">
        <div className="Postitem">
          <ToggleBtn onClick={clickedToggle} toggle={toggle}>
            <Circle toggle={toggle} />
          </ToggleBtn>
        </div>
        <div className="Postitem">듀오찾는 유저</div>
        <div className="positionBox">
          <div className="Jposition_first">
            <img src="img\positions\Position_Gold-Mid.png" />
          </div>
          <div className="Jposition_second">
            <img src="img\positions\Position_Gold-Jungle.png" />
          </div>
        </div>
        <div className="positionBox">
          <div className="Sposition_first">
            <img src="img\positions\Position_Gold-Support.png" />
          </div>
          <div className="Sposition_second">
            <img src="img\positions\Position_Gold-Mid.png" />
          </div>
        </div>
        <div className="Postitem_Memo">메모</div>
        <div className="Postitem_Date">등록일시</div>
        <div className="BntBox">
          <input type="button" value="수정" />
          <input type="button" value="삭제" />
        </div>
      </div>
    </Postitem>
  );
}
export default MyPosts;
