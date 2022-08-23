import styled from "styled-components";
import { Link } from "react-router-dom";

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
  &:nth-child(1),
  &:nth-child(2) {
    margin-bottom: 20px;
  }
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
  &:first-child {
    font-size: 24px;
    margin-bottom: 10px;
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
function Register() {
  const onSubmit = (event) => {
    event.preventDefault();
    console.log("회원 가입");
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
              <Text>회원 가입</Text>
              <Form onSubmit={onSubmit}>
                <Input type="email" placeholder="이메일 입력" required />
                <Input type="text" placeholder="닉네임 입력" required />
                <Input type="password" placeholder="비밀번호 입력" required />
                <Input type="password" placeholder="비밀번호 확인" required />
                <Submit type="submit" value="회원 가입" />
              </Form>
              <Text>
                아직 계정이 없으신가요?
                <Label>
                  <Link to="/login">로그인</Link>
                </Label>
              </Text>
            </OverViewItem>
          </OverView>
        </OverViewContainer>
      </Container>
    </>
  );
}

export default Register;
