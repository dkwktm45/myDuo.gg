import NavBar from "components/NavBar";
import styled from "styled-components";
import { useResetRecoilState } from "recoil";
import { LoginState } from "atoms";
import { useNavigate } from "react-router-dom";
import Alarm from "components/Alarm";
import { useState } from "react";
import { useForm } from "react-hook-form";

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
`;

const Header = styled.div`
  border: 1px solid aqua;
  width: 80%;
  height: 150px;
  margin: 10px 0;
  display: flex;
  flex-direction: row;
  justify-content: space-beetween;
`;

const ProfilePicture = styled.div`
  border: 2px solid tomato;
  width: 150px;
  height: 150px;
  color: white;
`;

const UserName = styled.div`
  border: 2px solid green;
  width: 200px;
  height: 150px;
  background-color: ${(props) => props.theme.lolTextColor};
`;
const Form = styled.form`
  display: flex;
  flex-direction: row;
`;
const Introduce = styled.textarea`
  border: 2px solid green;
  width: 450px;
  height: 150px;
`;
const SumitButton = styled.button`
  border: none;
  width: 50px;
  height: 20px;
  color: white;
  background-color: ${(props) => props.theme.lolTextColor};
  margin: 30px 0;
  cursor: pointer;
  border-radius: 5px;
`;
const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  border: 2px solid green;
  width: 80%;
  height: 100%;
  margin: 0;
  padding: 0;
`;

const InfoHeader = styled.div`
  border: 2px solid aqua;
  width: 100%;
  height: 100%;
  color: aqua;
  display: flex;
`;

const InfoUser = styled.div`
  border: 2px solid tomato;
  width: 80%;
  height: 100%;
  color: tomato;
`;
const ScoreRefresh = styled.button`
  width: 10%;
  color: #3333ff;
`;

const IdChange = styled.button`
  width: 10%;
  color: ECC5FB;
`;

function Profile() {
  const navigate = useNavigate();
  const onClickLogOut = () => {
    logOut();
    navigate("/login");
  };

  const logOut = useResetRecoilState(LoginState);

  const [introduce, setIntro] = useState("");
  const handleIntro = (e) => {
    setIntro(e.target.value);
  };

  return (
    <>
      <Container>
        <NavBar />
        <Wrapper>
          <Header>
            <ProfilePicture>Profile Picture</ProfilePicture>
            <UserName>계정 닉네임</UserName>
            <Form>
              <Introduce
                type="text"
                value={introduce}
                onChange={handleIntro}
                placeholder="자기소개"
              />
              <SumitButton>수정</SumitButton>
            </Form>
          </Header>
          <InfoContainer>
            <InfoHeader>
              <InfoUser>유저닉네임</InfoUser>
              <ScoreRefresh>전적갱신</ScoreRefresh>
              <IdChange>ID변경</IdChange>
            </InfoHeader>
          </InfoContainer>
          <TestButton onClick={onClickLogOut}>로그아웃</TestButton>
        </Wrapper>
      </Container>
      <Alarm />
    </>
  );
}

export default Profile;
