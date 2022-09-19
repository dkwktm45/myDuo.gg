import NavBar from "components/NavBar";
import styled from "styled-components";
import { useResetRecoilState, useRecoilValue } from "recoil";
import { LoginState } from "atoms";
import { useNavigate } from "react-router-dom";
import Alarm from "components/Alarm";
import { logoutService } from "services/apiServices";
import Footer from "components/Footer";

const Container = styled.div`
  width: 100vw;
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
  const account = useRecoilValue(LoginState);
  const logOut = useResetRecoilState(LoginState);
  const onClickLogOut = async () => {
    logoutService(account.token)
      .then(function (response) {
        logOut();
        window.localStorage.removeItem("myNick");
        navigate("/login");
      })
      .catch(function (error) {
        console.log("Error Msg", error);
      });
    logOut();
  };

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
        <Footer />
      </Container>
      <Alarm />
    </>
  );
}

export default Profile;
