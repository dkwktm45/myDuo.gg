import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faGoogle,
  faGithub,
  faFacebookF,
} from "@fortawesome/free-brands-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { LoginState } from "atoms";
import { useForm } from "react-hook-form";
import { accountService } from "services/apiServices";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
`;

const OverViewContainer = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
`;

const OverView = styled.div`
  width: 25vw;
  min-width: 360px;
  max-width: 380px;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: ${(props) => props.theme.lolTextColor};
  background-color: transparent;
  margin: 0 5vw;
  &:nth-child(2) {
    border: 4px solid ${(props) => props.theme.lolTextColor};
  }
  span {
    width: 100%;
    font-size: 120px;
    &:first-child {
      font-size: 25px;
      text-align: right;
    }
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
  //자동 완성 시 흰색 배경 변경 방지
  &:-webkit-autofill {
    transition: background-color 5000s ease-in-out 0s;
    -webkit-transition: background-color 9999s ease-out;
    -webkit-text-fill-color: ${(props) => props.theme.lolTextColor} !important;
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

const ErrorMsg = styled.label`
  font-size: 14px;
  color: ${(props) => props.theme.lolAccentColor1};
`;

function LogIn() {
  const setLogInState = useRecoilState(LoginState)[1];
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm();

  const onValid = async (data) => {
    const json = JSON.stringify({
      email: data.email,
      password: data.password,
    });

    accountService("login", json)
      .then(function (response) {
        setLogInState({
          token: response.data.grantType + response.data.accessToken,
          refreshToken: response.data.refreshToken,
        });
        navigate("/");
      })
      .catch(function (error) {
        if (error.response.status === 400) {
          setError("email", {
            message: "이메일을 다시 확인해주세요",
          });
        } else if (error.response.status === 500) {
          setError("password", {
            message: "비밀번호를 다시 확인해주세요",
          });
        }
      });
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
              <Form onSubmit={handleSubmit(onValid)}>
                <Input
                  placeholder="이메일 입력"
                  {...register("email", {
                    required: "이메일 입력은 필수입니다.",
                    pattern: {
                      value:
                        /^[A-Za-z0-9._%+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
                      message: "이메일 형식만 가능합니다.",
                    },
                    minLength: {
                      value: 1,
                      message: "이메일 형식에 맞지 않습니다",
                    },
                    maxLength: {
                      value: 20,
                      message: "이메일은 20자 이하만 가능합니다",
                    },
                  })}
                />
                <ErrorMsg className="text-danger">
                  {errors?.email?.message}
                </ErrorMsg>
                <Input
                  type="password"
                  placeholder="비밀번호 입력"
                  {...register("password", {
                    required: "비밀번호를 입력해주세요.",
                    minLength: {
                      value: 1,
                      message: "비밀번호는 최소 1자리 이상 입력해주세요",
                    },
                    maxLength: {
                      value: 200,
                      message: "비밀번호가 너무 길어요",
                    },
                  })}
                />
                <ErrorMsg className="text-danger">
                  {errors?.password?.message}
                </ErrorMsg>
                <Submit type="submit" value="로그인" />
              </Form>
              <Text>또는</Text>
              <SocialIcons>
                <SocialIcon name="google">
                  <FontAwesomeIcon icon={faGoogle} />
                </SocialIcon>
                <SocialIcon name="github">
                  <FontAwesomeIcon icon={faGithub} />
                </SocialIcon>
                <SocialIcon name="facebook">
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
