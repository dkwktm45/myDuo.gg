import NavBar from "components/NavBar";
import styled from "styled-components";
import { useResetRecoilState } from "recoil";
import { LoginState } from "atoms";
import { useNavigate } from "react-router-dom";
import Alarm from "components/Alarm";

const Container = styled.div`
  width: 50vw;
  height: calc(100vh - 120px);
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 120px;
`;

const TestButton = styled.button`
  border: none;
  width: 200px;
  height: 50px;
  color: white;
  background-color: ${(props) => props.theme.lolTextColor};
  margin: 30px 0;
  cursor: pointer;
`;

const Wrapper = styled.div`
  width: 1030px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${(props) => props.theme.lolBgColorLight};
`;

const Header = styled.div`
  width: 80%;
  height: 50px;
  margin: 10px 0;
`;

function Profile() {
  const navigate = useNavigate();
  const onClickLogOut = () => {
    logOut();
    navigate("/login");
  };

  const logOut = useResetRecoilState(LoginState);

  return (
    <>
      <NavBar />
      <Container>
        <Wrapper>
          <Header>
            <span>Profile Picture</span>
            <span>계정 닉네임</span>
            <span>자기 소개</span>
          </Header>
          <TestButton onClick={onClickLogOut}>로그아웃</TestButton>
        </Wrapper>
      </Container>
      <Alarm />
    </>
  );
}

export default Profile;
