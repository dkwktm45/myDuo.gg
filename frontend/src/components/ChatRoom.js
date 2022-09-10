import styled from "styled-components";
import React, { useRef, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRightFromBracket } from "@fortawesome/free-solid-svg-icons";

const Wrapper = styled.div`
  width: 30vw;
  min-width: 500px;
  height: 80vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 3vw;
  color: white;
`;

const ChatWrapper = styled(Wrapper)`
  display: grid;
  grid-template-rows: 80px 1fr 90px;
  background-color: ${(props) => props.theme.lolBgColorLight};
`;

const ChatHeader = styled.div`
  height: 100%;
  margin: 10px 0;
  display: grid;
  grid-template-columns: 60px 1fr 1fr 120px 40px;
  color: ${(props) => props.theme.lolTextColor};
  div {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    &:nth-child(1) {
      img {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        margin-left: 10px;
      }
    }
    &:nth-child(2) {
      align-items: flex-start;
      span {
        font-size: 14px;
        margin-left: 10px;
        &:first-child {
          font-size: 16px;
          margin-bottom: 5px;
        }
        &:nth-child(2) {
          color: whitesmoke;
        }
      }
    }
    &:nth-child(3) {
      background-color: yellowgreen;
    }
    &:nth-child(4) {
      div {
        &:nth-child(2) {
          display: flex;
          flex-direction: row;
          align-items: center;
          button {
            width: 45px;
            height: 25px;
            padding: 5px;
            color: ${(props) => props.theme.lolAccentColor};
            background-color: ${(props) => props.theme.lolBgColorNormal};
            border: none;
            margin: 3px;
            &:nth-child(2) {
              color: ${(props) => props.theme.lolAccentColor1};
              background-color: ${(props) => props.theme.lolBgColorNormal};
            }
          }
        }
      }
    }
    &:nth-child(5) {
      font-size: 18px;
      color: whitesmoke;
      cursor: pointer;
    }
  }
`;

const ChatBody = styled.div`
  height: 100%;
  background-color: ${(props) => props.theme.lolBgColorNormal};
  overflow: auto;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
  overflow: auto;
  overflow-x: hidden;
  align-items: center;
  /*
  * 스크롤바 디자인
  */
  &::-webkit-scrollbar {
    width: 10px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: ${(props) => props.theme.lolTextColor};
    border-radius: 5px;
  }
  &::-webkit-scrollbar-track {
    background-color: ${(props) => props.theme.lolBgColorLight};
    border-radius: 5px;
    box-shadow: inset 0px 0px 5px ${(props) => props.theme.lolBgColorNormal};
  }
  div {
    width: 95%;
    display: flex;
    margin: 5px 0;
    &.my {
      justify-content: flex-end;
      span {
        display: flex;
        flex-direction: column;
        padding: 0 10px;
        label {
          &:first-child {
            max-width: 300px;
            line-height: 24px;
            padding: 14px;
            border-radius: 12px;
            background-color: ${(props) => props.theme.lolAccentColor};
          }
          &:nth-child(2) {
            font-size: 12px;
            margin-top: 10px;
            margin-left: 10px;
            text-align: right;
            -ms-user-select: none;
            -moz-user-select: -moz-none;
            -webkit-user-select: none;
            -khtml-user-select: none;
            user-select: none;
          }
        }
      }
    }
    &.other {
      justify-content: flex-start;
      span {
        display: flex;
        flex-direction: column;
        padding: 0 10px;
        label {
          &:first-child {
            max-width: 300px;
            line-height: 24px;
            padding: 14px;
            border-radius: 12px;
            background-color: ${(props) => props.theme.lolBgColorLight};
          }
          &:nth-child(2) {
            width: 100%;
            font-size: 12px;
            margin-top: 10px;
            margin-left: 10px;
            text-align: left;
            -ms-user-select: none;
            -moz-user-select: -moz-none;
            -webkit-user-select: none;
            -khtml-user-select: none;
            user-select: none;
          }
        }
      }
    }
    &.info {
      justify-content: center;
      margin: 10px 0;
      font-size: 14px;
      color: #ccc;
    }
    &:last-child {
      margin-bottom: 10px;
    }
  }
`;

const ChatController = styled.div`
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  input {
    color: whitesmoke;
    width: 70%;
    height: 55px;
    margin-right: 20px;
    padding: 5px 10px;
    font-size: 18px;
    background-color: ${(props) => props.theme.lolBgColorNormal};
    border: none;
    outline: none;
  }
  button {
    width: 15%;
    height: 50px;
    border: none;
    font-family: "Roboto";
    font-size: 16px;
    background-color: ${(props) => props.theme.lolTextColor};
    cursor: pointer;
  }
`;
function ChatRoom({ ...props }) {
  const scrollRef = useRef();

  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  });

  return (
    <ChatWrapper>
      <ChatHeader>
        <div>
          <img src={`../img/emblems/Emblem_Silver.png`} alt="lolLogo" />
        </div>
        <div>
          <span>{props.chatRoom}</span>
          <span>유저의 롤 아이디</span>
        </div>
        <div></div>
        <div>
          <div>듀오 요청</div>
          <div>
            <button>수락</button>
            <button>거절</button>
          </div>
        </div>
        <div>
          <FontAwesomeIcon icon={faRightFromBracket} />
        </div>
      </ChatHeader>
      <ChatBody ref={scrollRef}>
        <div className="info">채팅방이 생성되었습니다.</div>
        <div className="my">
          <span>
            <label>
              안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요 안녕하세요
              안녕하세요 안녕하세요 안녕하세요 안녕하세요
            </label>
            <label>오후 3:00</label>
          </span>
        </div>
        <div className="other">
          <span>
            <label>
              반갑습니다 반갑습니다 반갑습니다 반갑습니다 반갑습니다 반갑습니다
              반갑습니다 반갑습니다 반갑습니다 반갑습니다
            </label>
            <label>오후 3:01</label>
          </span>
        </div>
        <div className="my">
          <span>
            <label>듀오 해요</label>
            <label>오후 3:10</label>
          </span>
        </div>
        <div className="other">
          <span>
            <label>넵</label>
            <label>오후 3:01</label>
          </span>
        </div>
        <div className="other">
          <span>
            <label>Hi Hi Hi Hi Hi Hi</label>
            <label>오후 3:01</label>
          </span>
        </div>
        <div className="other">
          <span>
            <label>Hi Hi Hi Hi Hi Hi</label>
            <label>오후 3:01</label>
          </span>
        </div>
        <div className="other">
          <span>
            <label>Hi Hi Hi Hi Hi Hi</label>
            <label>오후 3:01</label>
          </span>
        </div>
        <div className="other">
          <span>
            <label>Hi Hi Hi Hi Hi Hi</label>
            <label>오후 3:01</label>
          </span>
        </div>
      </ChatBody>
      <ChatController>
        <input type="text" />
        <button>전송</button>
      </ChatController>
    </ChatWrapper>
  );
}

export default ChatRoom;
