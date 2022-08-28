import styled from "styled-components";
import NavBar from "components/NavBar";
import io from "socket.io-client";

const Container = styled.div`
  width: 100%;
  height: calc(100vh - 5vh);
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
  border: 1px solid ${(props) => props.theme.lolTextColor};
  color: white;
`;

function Chat() {
  const socket = io("http://localhost:3000");
  console.log(socket);
  return (
    <>
      <NavBar />
      <Container>
        <Wrapper>
          <div>채팅</div>
        </Wrapper>
        <Wrapper>
          <div>채팅</div>
        </Wrapper>
      </Container>
    </>
  );
}
export default Chat;
