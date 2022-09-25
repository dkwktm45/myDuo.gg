import styled from "styled-components";
import React, { useRef, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import { useForm } from "react-hook-form";
import { LoginState } from "atoms";
import { useRecoilValue } from "recoil";
import axios from "axios";

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
      background-color: transparent;
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

const ChatController = styled.form`
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
  const account = useRecoilValue(LoginState);
  const myNickName = window.localStorage.getItem("myNick");
  const { register, handleSubmit, reset } = useForm();
  var ws = props.ws;

  useEffect(() => {
    //채팅창 시작 시 스크롤 맨 아래에서부터 시작
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [props.chats]);

  const onValid = (data) => {
    ws.send(
      "/pub/chat/message",
      JSON.stringify({
        type: "TALK",
        roomId: props.room.roomId,
        message: data.msg,
        sender: myNickName,
      }),
      {
        Authorization: account.token,
      }
    );
    reset();
  };

  const toAMPM = (date) => {
    return new Date(date).toLocaleString("ko-KR", {
      hour: "numeric",
      minute: "numeric",
      hour12: true,
    });
  };
  const acceptDuo = () => {
    //boardUuid / participantUuid 필요
    //participants/delete

    /// board-id => 보드 아이디 구해야함

    console.log(props.room.participantUuid);
    axios
      .post("http://localhost:8080/board/one", props.room.participantUuid, {
        headers: {
          Authorization: account.token,
          "Content-Type": "text/plain", // 이거 안넣으면 안됨 !!
        },
      })
      .then((response) => {
        console.log("BoardUuid =", response.data.board.boardUuid);
        console.log("participantUuid =", props.room.participantUuid);

        const body = new Map();
        body.set("boardUuid", response.data.board.boardUuid);
        body.set("participantUuid", props.room.participantUuid);

        axios
          .delete("http://localhost:8080/participants/delete", {
            headers: {
              Authorization: account.token,
              //"Content-Type": "text/plain",
              "Content-Type": "application/json",
            },
            body,
          })
          .then((newResponse) => {
            console.log(newResponse);
          });
      });
  };

  return (
    <ChatWrapper>
      <ChatHeader>
        <div>
          <img src={`../img/emblems/Emblem_Silver.png`} alt="lolLogo" />
        </div>
        <div>
          <span>{props.room.userName}</span>
          <span>유저의 롤 아이디</span>
        </div>
        <div>
          <button>친구 신청하기</button>
        </div>
        <div>
          {myNickName === props.room.boardName ? (
            <>
              <div>듀오 요청</div>
              <div>
                <button onClick={acceptDuo}>수락</button>
                <button>거절</button>
              </div>
            </>
          ) : (
            ""
          )}
        </div>

        <div>
          <FontAwesomeIcon icon={faRightFromBracket} />
        </div>
      </ChatHeader>
      <ChatBody ref={scrollRef}>
        {props.chats.map((msg) => {
          if (msg.type === "ENTER") {
            return (
              <div className="info" key={msg.messageId}>
                <span>
                  <label>
                    {msg.sender}
                    {msg.message}
                  </label>
                </span>
              </div>
            );
          } else {
            if (myNickName === msg.sender) {
              return (
                <div className="my" key={msg.messageId}>
                  <span>
                    <label>{msg.message}</label>
                    <label>{toAMPM(new Date(msg.createdAt))}</label>
                  </span>
                </div>
              );
            } else {
              return (
                <div className="other" key={msg.messageId}>
                  <span>
                    <label>{msg.message}</label>
                    <label>{toAMPM(new Date(msg.createdAt))}</label>
                  </span>
                </div>
              );
            }
          }
        })}
      </ChatBody>
      <ChatController onSubmit={handleSubmit(onValid)}>
        <input type="text" {...register("msg")} autoComplete="off" />
        <button type="submit">전송</button>
      </ChatController>
    </ChatWrapper>
  );
}

export default ChatRoom;
