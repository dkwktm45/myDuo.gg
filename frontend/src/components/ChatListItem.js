import styled from "styled-components";
import Stomp from "webstomp-client";
import sockjs from "sockjs-client";
import { LoginState } from "atoms";
import { useRecoilValue } from "recoil";
import { useState } from "react";
import axios from "axios";

const DuoChatList = styled.div`
  width: 95%;
  height: 60px;
  border: 1px solid ${(props) => props.theme.lolTextColor};
  margin: 5px 0;
  cursor: pointer;
  display: grid;
  grid-template-columns: 50px 120px auto 40px;
  transition: all 0.2s ease-in-out;
  div {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    &:first-child {
      img {
        width: 30px;
        height: 30px;
      }
    }
    &:nth-child(2) {
      color: ${(props) => props.theme.lolTextColor};
      justify-content: left;
    }
    &:nth-child(3) {
      font-size: 15px;
      flex-direction: column;
      align-items: flex-start;
      label {
        cursor: pointer;
        margin-left: 5px;
        &:nth-child(2) {
          margin-left: 10px;
          margin-top: 3px;
          font-size: 13px;
          color: #aaa;
        }
      }
    }
    &:nth-child(4) {
      span {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: tomato;
        width: 25px;
        height: 25px;
        border-radius: 50%;
        font-size: 13px;
      }
    }
  }
  &:hover {
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
  &.selected {
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
`;

function ChatListItem({ ...props }) {
  const account = useRecoilValue(LoginState);
  //var [ws, setWs] = useState(null);
  var ws = props.ws;
  var setWs = props.setWs;

  const handleChatRoom = async () => {
    if (ws === null) {
      var chatSocket = new sockjs("http://localhost:8080/ws-stomp", null, {
        headers: {
          Authorization: account.token,
        },
      });
      ws = Stomp.over(chatSocket);
      setWs(ws);
    }
    if (props.chatRoom === "") {
      props.setChatRoom(props.data.roomId);
      connectChat(ws, props.data.roomId);
    } else {
      if (props.chatRoom !== props.data.roomId) {
        disconnectChat(ws, props.chatRoom);
        handleChatRoom();
      } else {
        disconnectChat(ws, props.chatRoom);
        props.setChatRoom("");
      }
    }
  };

  const disconnectChat = (ws, roomId) => {
    ws.disconnect(
      () => {
        console.log("connect 끊음");
        setWs(null);
      },
      { roomId: roomId }
    );
  };

  const connectChat = (ws, roomId) => {
    //ws.debug = function (str) {};
    ws.connect(
      { Authorization: account.token },
      function (frame) {
        ws.subscribe(
          "/sub/chat/room/" + roomId,
          function (message) {
            var recv = JSON.parse(message.body);
            console.log("연결 성공", recv);
            props.setChatRoom(roomId);
          },
          {
            Authorization: account.token,
          }
        );
      },
      function (error) {
        alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
        ws.disconnect(
          () => {
            console.log("connect 끊음");
          },
          { roomId: roomId }
        );
        //location.href = "/chat-list";
        console.log("실패");
      }
    );
  };

  if (props.type === "duo-applicant") {
    return (
      <DuoChatList
        key={props.index}
        onClick={handleChatRoom}
        className={props.data.roomId === props.chatRoom ? "selected" : ""}
      >
        <div>
          <img src={`../img/emblems/Emblem_Silver.png`} alt="lolLogo" />
        </div>
        <div>{props.data.userName}</div>
        <div>
          <label>안녕하세요</label>
          <label>오후 3:13</label>
        </div>
        <div>
          <span>3</span>
        </div>
      </DuoChatList>
    );
  } else if (props.type === "duo-apply") {
    return (
      <DuoChatList
        key={props.index}
        onClick={handleChatRoom}
        className={props.data === props.chatRoom ? "selected" : ""}
      >
        <div>
          <img src={`../img/emblems/Emblem_Silver.png`} alt="lolLogo" />
        </div>
        <div>{props.data.boardName}</div>
        <div>
          <label>안녕하세요</label>
          <label>오후 3:13</label>
        </div>
        <div>
          <span>3</span>
        </div>
      </DuoChatList>
    );
  }
}

export default ChatListItem;
