import NavBar from "components/NavBar";
import styled from "styled-components";
import { useResetRecoilState } from "recoil";
import { LoginState } from "atoms";
import { useNavigate } from "react-router-dom";

const Wrapper = styled.div`
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
      <Wrapper>
        <TestButton onClick={onClickLogOut}>로그아웃</TestButton>
      </Wrapper>
    </>
  );
}
export default Profile;
