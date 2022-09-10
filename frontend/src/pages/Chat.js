import styled from "styled-components";
import NavBar from "components/NavBar";
import { useState } from "react";
import Alarm from "components/Alarm";
import ChatListItem from "components/ChatListItem";
import ChatRoom from "components/ChatRoom";
import { useEffect } from "react";
import { LoginState } from "atoms";
import { useRecoilValue } from "recoil";
import axios from "axios";

const Container = styled.div`
  width: 100%;
  height: calc(100vh - 5vh - 100px);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 100px;
  font-family: "Roboto";
`;

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

const TabMenus = styled.div`
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
`;

const TabMenu = styled.span`
  width: 100px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  cursor: pointer;
  background-color: ${(props) =>
    props.on === "true"
      ? props.theme.lolBgColorLight
      : props.theme.lolBgColorNormal};
  color: ${(props) => (props.on === "true" ? props.theme.lolTextColor : "")};
`;

const TabContents = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: ${(props) => props.theme.lolBgColorLight};
  span {
    width: 80%;
    margin: 10px 0;
  }
`;

const ChatList = styled.div`
  width: 80%;
  height: 42%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${(props) => props.theme.lolBgColorNormal};
  overflow: auto;
  overflow-x: hidden;
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

  //내 게시물 지원자와의 채팅 리스트
  &.duo-applicant {
  }
  //내가 지원한 듀오
  &.duo-apply {
  }
  //친구 채팅 리스트
  &.friend {
    height: 90%;
  }
  &.disabled {
  }
`;

function Chat() {
  const [chatRoom, setChatRoom] = useState("");
  const account = useRecoilValue(LoginState);

  const [isDuoChat, setssDuoChat] = useState(true);
  const [myChatList, setMyChatList] = useState([]);
  const [otherChatList, setOtherChatList] = useState([]);
  const handleTabMenu = (e) => {
    setssDuoChat(!isDuoChat);
  };

  useEffect(() => {
    (async () => {
      await axios
        .post("http://localhost:8080/participants/my-rooms", null, {
          headers: {
            Authorization: account.token,
          },
        })
        .then(function (response) {
          setMyChatList(response.data);
        });

      await axios
        .post("http://localhost:8080/participants/other-rooms", null, {
          headers: {
            Authorization: account.token,
          },
        })
        .then(function (response) {
          setOtherChatList(response.data);
        });
    })();
  }, [account.token]);

  return (
    <>
      <NavBar />
      <Container>
        <Wrapper>
          <TabMenus>
            <TabMenu
              onClick={handleTabMenu}
              on={isDuoChat === true ? "true" : "false"}
            >
              듀오 모집
            </TabMenu>
            <TabMenu
              onClick={handleTabMenu}
              on={isDuoChat === false ? "true" : "false"}
            >
              친구
            </TabMenu>
          </TabMenus>
          <TabContents>
            {isDuoChat ? (
              <>
                <span>내가 모집하는 듀오</span>
                <ChatList className="duo-applicant">
                  {myChatList.map((item, index) => {
                    return (
                      <ChatListItem
                        type={"duo-applicant"}
                        key={index}
                        data={item}
                        chatRoom={chatRoom}
                        setChatRoom={setChatRoom}
                      />
                    );
                  })}
                </ChatList>
                <span>내가 지원한 듀오</span>
                <ChatList className="duo-apply">
                  {otherChatList.map((item, index) => {
                    return (
                      <ChatListItem
                        type={"duo-apply"}
                        key={index}
                        data={item}
                        chatRoom={chatRoom}
                        setChatRoom={setChatRoom}
                      />
                    );
                  })}
                </ChatList>
              </>
            ) : (
              <>
                <ChatList className="friend">
                  <ChatListItem
                    type={"friend"}
                    key={1}
                    data={1}
                    chatRoom={chatRoom}
                    setChatRoom={setChatRoom}
                  />
                </ChatList>
              </>
            )}
          </TabContents>
        </Wrapper>
        <Wrapper>
          {chatRoom === "" ? (
            ""
          ) : (
            <ChatRoom chatRoom={chatRoom} setChatRoom={setChatRoom} />
          )}
        </Wrapper>
      </Container>
      <Alarm />
    </>
  );
}
export default Chat;
