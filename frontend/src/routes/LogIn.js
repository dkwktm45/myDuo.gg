import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faGoogle,
  faGithub,
  faFacebookF,
} from "@fortawesome/free-brands-svg-icons";
import { Link, useNavigate } from "react-router-dom";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
`;

const OverViewContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const OverView = styled.div`
  color: ${(props) => props.theme.lolTextColor};
  background-color: rgba(0, 0, 0, 0);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-width: 360px;
  margin: 0 10px;
  width: 25vw;
  &:first-child {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
  }

  &:nth-child(2) {
    border: 4px solid ${(props) => props.theme.lolTextColor};
  }
  span {
    width: 100%;
  }
  span:first-child {
    font-size: 25px;
    text-align: right;
  }

  span:nth-child(2) {
    font-size: 120px;
  }
`;

const OverViewItem = styled.div`
  width: 75%;
  padding: 40px 0;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 5px;
`;

const Input = styled.input`
  width: 100%;
  height: 40px;
  margin-bottom: 10px;
  padding: 5px;
  font-size: 16px;
  letter-spacing: 1px;
  color: ${(props) => props.theme.lolTextColor};
  box-sizing: border-box;
  transition: all 0.3s ease-in-out;
  background-color: rgba(0, 0, 0, 0);
  border: none;
  outline: none;
  margin: 10px 0;
  border-bottom: 2px solid ${(props) => props.theme.lolTextColor};
  &:hover {
    border-bottom: 5px solid ${(props) => props.theme.lolTextColor};
  }
  &:focus {
    border-bottom: 5px solid ${(props) => props.theme.lolTextColor};
  }
  &::placeholder {
    color: ${(props) => props.theme.lolTextColor};
    letter-spacing: 0px;
  }
`;

const Submit = styled.input`
  font-family: "Hanna";
  color: ${(props) => props.theme.lolBgColor};
  width: 100%;
  height: 40px;
  border: none;
  border-radius: none;
  font-size: 20px;
  justify-content: center;
  background-color: ${(props) => props.theme.lolTextColor};
  margin: 20px 0;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
  &:hover {
    color: white;
  }
`;

const Text = styled.div`
  text-align: center;
  font-size: 16px;
`;

const SocialIcons = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
`;

const SocialIcon = styled.button`
  width: 40px;
  height: 40px;
  font-size: 24px;
  text-align: center;
  background-color: ${(props) => props.theme.lolBgColor};
  color: ${(props) => props.theme.lolTextColor};
  border: 1px solid ${(props) => props.theme.lolTextColor};
  border-radius: 5px;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
  margin: 0 5px;

  &:nth-child(1):hover {
    background-color: white;
    color: #4d80ed;
    border: 1px solid white;
  }
  &:nth-child(2):hover {
    background-color: white;
    color: black;
    border: 1px solid white;
  }
  &:nth-child(3):hover {
    background-color: #526ea4;
    color: white;
    border: 1px solid #526ea4;
  }
`;
const Label = styled.label`
  font-size: 16px;
  color: ${(props) => props.theme.lolAccentColor};
  margin-left: 10px;
  cursor: pointer;
  &:hover {
    text-decoration: underline;
  }
`;

function LogIn({ setIsLoggedIn }) {
  const navigate = useNavigate();
  const onSubmit = (event) => {
    event.preventDefault();
    try {
      console.log("로그인");
      setIsLoggedIn(true);
      navigate("/");
    } catch (error) {
      console.log(error);
    }
  };

  const onClickSocial = (event) => {
    const {
      target: { name },
    } = event;
    console.log(name);
  };

  return (
    <>
      <Container>
        <OverViewContainer>
          <OverView>
            <span>내가 찾은 최고의 게임 듀오</span>
            <span>MyDuo</span>
          </OverView>
          <OverView>
            <OverViewItem>
              <Form onSubmit={onSubmit}>
                <Input type="email" placeholder="이메일 입력" required />
                <Input type="password" placeholder="비밀번호 입력" required />
                <Submit type="submit" value="로그인" />
              </Form>
              <Text>또는</Text>
              <SocialIcons>
                <SocialIcon name="google" onClick={onClickSocial}>
                  <FontAwesomeIcon icon={faGoogle} />
                </SocialIcon>
                <SocialIcon name="github" onClick={onClickSocial}>
                  <FontAwesomeIcon icon={faGithub} />
                </SocialIcon>
                <SocialIcon name="facebook" onClick={onClickSocial}>
                  <FontAwesomeIcon icon={faFacebookF} />
                </SocialIcon>
              </SocialIcons>
              <Text>
                아직 계정이 없으신가요?
                <Label>
                  <Link to="/register">회원 가입</Link>
                </Label>
              </Text>
            </OverViewItem>
          </OverView>
        </OverViewContainer>
      </Container>
    </>
  );
}

export default LogIn;
