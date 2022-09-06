import NavBar from "components/NavBar";
import styled from "styled-components";
import { useResetRecoilState } from "recoil";
import { LoginState } from "atoms";
import { useNavigate } from "react-router-dom";
import Alarm from "components/Alarm";

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
  border:1px solid aqua;
  width: 80%;
  height: 150px;
  margin: 10px 0;
  display:flex;
  flex-direction:row;
  justify-content:space-beetween;
`;

const SumitButton = styled.button`
border: none;
width: 50px;
height: 20px;
color: white;
background-color: ${(props) => props.theme.lolTextColor};
margin: 30px 0;
cursor: pointer;
border-radius:5px
`;

const ProfilePicture = styled.div`
border:2px solid tomato;
width :150px;
height : 150px;

`;

const UserName = styled.div`
border:2px solid green;
width :150px;
height : 150px;
background-color: ${(props) => props.theme.lolTextColor};
`;

const IntroduceYourself = styled.form`
border : 2px solid green;
width :150px;
height : 150px;

`;

function Introduce() {
  const [introduce,setIntro] = useState("");
  const handleIntro = (e) => {
    setIntro(e.target.value);
  };
  const handleSudmlt = (e) => {
    e.preventDefault()
  };
  return(
    <form onSubmit = { handleSudmlt}>
      <label>
        자기소개
        <input 
        type="text" 
        value= {introduce} 
        onChange={handleIntro}/>
      </label>
    </form>
  )
}

function Profile() {
  const navigate = useNavigate();
  const onClickLogOut = () => {
    logOut();
    navigate("/login");
  };

  const logOut = useResetRecoilState(LoginState);

  return (
    <>
      
      <Container>
      <NavBar />
        <Wrapper>
          <Header>
            <ProfilePicture>Profile Picture</ProfilePicture>
            <UserName>계정 닉네임</UserName>
            <IntroduceYourself>자기 소개</IntroduceYourself>
            <SumitButton>수정</SumitButton>
          </Header>
          <TestButton onClick={onClickLogOut}>로그아웃</TestButton>
        </Wrapper>
      </Container>
      <Alarm />
    </>
  );
}

export default Profile;
