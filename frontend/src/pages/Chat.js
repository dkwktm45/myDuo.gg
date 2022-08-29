import styled from "styled-components";
import NavBar from "components/NavBar";
import { useState } from "react";
//import io from "socket.io-client";

const Container = styled.div`
  width: 100%;
  height: calc(100vh - 5vh - 100px);
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 100px;
`;

const Wrapper = styled.div`
  width: 30vw;
  height: 80vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 5vw;
  color: white;
  &:nth-child(2) {
    border: 1px solid ${(props) => props.theme.lolTextColor};
  }
`;

const TabMenus = styled.div`
  width: 100%;
  height: 30px;
  display: flex;
  align-items: center;
`;

const TabMenu = styled.span`
  width: 100px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  border: 1px solid ${(props) => props.theme.lolTextColor};
  background-color: ${(props) =>
    props.on === "true" ? props.theme.lolBgColorLight : "transeparent"};
`;

const TabContents = styled.div`
  border: 1px solid ${(props) => props.theme.lolTextColor};
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: ${(props) => props.theme.lolBgColorNormal};
  span {
    width: 80%;
    margin: 10px 0;
  }
`;

const DuoChatWrapper = styled.div`
  width: 80%;
  height: 42%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${(props) => props.theme.lolBgColorLight};
`;

const DuoChatRoom = styled.div`
  width: 90%;
  height: 50px;
  background-color: ${(props) => props.theme.lolBgColorNormal};
  margin: 1px 0;
`;

const FriendChatWrapper = styled(DuoChatWrapper)`
  height: 90%;
`;

const FriendChatRoom = styled(DuoChatRoom)``;

function Chat() {
  const [chatRoom, setChatRoom] = useState("");

  const [isDuoChat, setssDuoChat] = useState(true);
  const handleTabMenu = (e) => {
    setssDuoChat(!isDuoChat);
  };

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
                <DuoChatWrapper>
                  <DuoChatRoom />
                  <DuoChatRoom />
                </DuoChatWrapper>
                <span>내가 지원한 듀오</span>
                <DuoChatWrapper></DuoChatWrapper>
              </>
            ) : (
              <>
                <FriendChatWrapper>
                  <FriendChatRoom />
                  <FriendChatRoom />
                </FriendChatWrapper>
              </>
            )}
          </TabContents>
        </Wrapper>
        <Wrapper>{chatRoom === "" ? "" : <div>채팅</div>}</Wrapper>
      </Container>
    </>
  );
}
export default Chat;
