import React, { useEffect, useState } from "react";
import styled, { css } from "styled-components";

const DropBoxContainet = styled.div`
  width: 250px;
  height: 70px;
  margin: 0;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 10px 0;

  .Container {
    width: 100%;
    font-size: 30px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    border: 1px solid black;
  }
  .Container > ul {
    background-color: White;
  }
  .IdBox {
    padding: 5px;
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    border: 1px solid black;
    font-size: 20px;

    &:hover {
      color: #455057;
      background-color: ${(props) => props.theme.lolTextColor};
    }
  }
  .secondID > button {
    border: 1px solid #455057;
    background-color: ${(props) => props.theme.lolTextColor};
    border-radius: 5px;
    cursor: pointer;
  }
`;

function MyPosts() {
  return (
    <DropBoxContainet>
      <div className="Container">
        <ul>
          <li className="firstID , IdBox">
            <p>화가 난 프로도</p>
          </li>
          <li className="secondID , IdBox">
            <p>롤아이디 부계정2</p>
            <button>선택</button>
          </li>
        </ul>
        <div></div>
      </div>
      <button>+</button>
    </DropBoxContainet>
  );
}
export default MyPosts;
