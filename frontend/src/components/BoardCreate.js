import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import LinePositions from "components/LinePositions";
import { useState } from "react";

const Overlay = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 5;
`;

const Container = styled.div`
  font-family: "Roboto", sans-serif;
  position: absolute;
  width: 360px;
  height: 600px;
  top: 0vh;
  bottom: 0vh;
  left: 0vw;
  right: 0vw;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  border: 2px solid ${(props) => props.theme.lolTextColor};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  color: ${(props) => props.theme.lolTextColor};
  z-index: 9;
`;

const BoardCreateHeader = styled.div`
  width: 90%;
  height: 70px;
  border-bottom: 1px solid ${(props) => props.theme.lolTextColor};
  display: flex;
  justify-content: space-between;
  align-items: center;
  span {
    font-size: 18px;
    &:first-child {
      margin-left: 10px;
      margin-top: 5px;
    }
    &:nth-child(2) {
      margin-bottom: 10px;
      font-size: 20px;
      cursor: pointer;
    }
  }
`;

const BoardCreateContents = styled.div`
  width: 80%;
  height: 80px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

const BoardCreateId = styled(BoardCreateContents)`
  height: 80px;
  select {
    width: 70%;
    height: 35px;
    color: ${(props) => props.theme.lolTextColor};
    border: 1px solid ${(props) => props.theme.lolTextColor};
    background-color: ${(props) => props.theme.lolBgColorLight};
    text-align: center;
  }
`;

const BoardCreateMyPosition = styled(BoardCreateContents)`
  height: 80px;
`;

const BoardCreateOtherPosition = styled(BoardCreateContents)`
  height: 80px;
  margin-top: 10px;
`;

const BoardCreateMic = styled(BoardCreateContents)`
  height: 50px;
`;

const BoardCreateMemo = styled(BoardCreateContents)`
  height: 100px;
  textarea {
    width: 100%;
    height: 60px;
    color: ${(props) => props.theme.lolTextColor};
    background-color: transparent;
    border: 1px solid ${(props) => props.theme.lolTextColor};
    resize: none;
    &:focus {
      outline: none;
    }
  }
`;

const BoardCreateButtons = styled(BoardCreateContents)`
  flex-direction: row-reverse;
  justify-content: right;
  button {
    font-size: 16px;
    font-weight: 600;
    height: 35px;
    border: none;
    background-color: ${(props) => props.theme.lolTextColor};
    cursor: pointer;
  }
`;

function BoardCreate({ setPopupCreate }) {
  const [memo, setMemo] = useState("");
  const overlayClose = () => {
    setPopupCreate(false);
  };

  const onChange = (e) => {
    setMemo(e.target.value);
  };

  return (
    <>
      <Overlay onClick={overlayClose} />
      <Container>
        <BoardCreateHeader>
          <span>게시물 등록하기</span>
          <span onClick={overlayClose}>
            <FontAwesomeIcon icon={faXmark} />
          </span>
        </BoardCreateHeader>
        <BoardCreateId>
          <span>롤 아이디 선택하기</span>
          <select>
            <option>1번 아이디</option>
            <option>2번 아이디</option>
            <option>3번 아이디</option>
          </select>
        </BoardCreateId>
        <BoardCreateMyPosition>
          <span>내 포지션</span>
          <LinePositions useFor="createMy" />
        </BoardCreateMyPosition>
        <BoardCreateOtherPosition>
          <span>다른 사람 포지션</span>
          <LinePositions useFor="createOther" />
        </BoardCreateOtherPosition>
        <BoardCreateMic>
          <label>마이크 사용 여부</label>
          <span>Mic</span>
        </BoardCreateMic>
        <BoardCreateMemo>
          <label>메모</label>
          <textarea value={memo} onChange={onChange}>
            hello
          </textarea>
        </BoardCreateMemo>
        <BoardCreateButtons>
          <button>듀오 모집하기</button>
        </BoardCreateButtons>
      </Container>
    </>
  );
}

export default BoardCreate;
