import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { LoginState } from "atoms";
import { useForm } from "react-hook-form";

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

const ErrorMsg = styled.label`
  font-size: 14px;
  color: ${(props) => props.theme.lolAccentColor1};
`;

function Register() {
  const setIsLoggedIn = useRecoilState(LoginState)[1];
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm();

  const onValid = async (data) => {
    if (data.password !== data.passwordCheck) {
      setError("passwordCheck", { message: "비밀번호를 다시 확인해주세요." });
    }
    setIsLoggedIn(true);
    navigate("/");

    //성공하면 해당 user 아이디 패스워드값 셋팅
    //JWT
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
              <Form onSubmit={handleSubmit(onValid)}>
                <Input
                  placeholder="이메일 입력"
                  {...register("email", {
                    required: "이메일 입력은 필수입니다.",
                    pattern: {
                      value: /^[A-Za-z0-9._%+-]+@gmail.com$/,
                      message: "구글 이메일 형식만 가능합니다.",
                    },
                  })}
                />
                <ErrorMsg>{errors?.email?.message}</ErrorMsg>
                <Input
                  type="text"
                  placeholder="닉네임 입력"
                  {...register("nickname", {
                    required: "닉네임을 입력해주세요",
                    minLength: {
                      value: 2,
                      message: "닉네임은 최소 2자리 이상 입력해주세요.",
                    },
                  })}
                />
                <ErrorMsg>{errors?.nickname?.message}</ErrorMsg>
                <Input
                  type="password"
                  placeholder="비밀번호 입력"
                  {...register("password", {
                    required: "비밀번호를 입력해주세요.",
                    minLength: {
                      value: 8,
                      message: "비밀번호는 최소 8자리 이상 입력해주세요.",
                    },
                  })}
                />
                <ErrorMsg>{errors?.password?.message}</ErrorMsg>
                <Input
                  type="password"
                  placeholder="비밀번호 확인"
                  {...register("passwordCheck", {
                    required: "비밀번호를 입력해주세요.",
                  })}
                />
                <ErrorMsg>{errors?.passwordCheck?.message}</ErrorMsg>
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