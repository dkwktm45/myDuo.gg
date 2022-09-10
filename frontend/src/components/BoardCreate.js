import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";
import LinePositions from "components/LinePositions";
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { boardCreateOpenService } from "services/apiServices";
import { useRecoilValue } from "recoil";
import { LoginState } from "atoms";
import axios from "axios";

const Overlay = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 5;
`;

const Container = styled.form`
  font-family: "Roboto", sans-serif;
  position: absolute;
  width: 440px;
  height: 560px;
  top: 0vh;
  bottom: 0vh;
  left: 0vw;
  right: 0vw;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  border: 2px solid ${(props) => props.theme.lolTextColor};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  color: ${(props) => props.theme.lolTextColor};
  z-index: 9;
`;

const GridContainer = styled.div`
  display: grid;
  width: 80%;
  height: 180px;
  grid-template-rows: 1fr 1fr;
  grid-template-areas:
    "header right "
    "main right ";
  margin-top: 20px;
`;

const BoardCreateHeader = styled.div`
  width: 90%;
  height: 70px;
  border-bottom: 1px solid ${(props) => props.theme.lolTextColor};
  display: flex;
  justify-content: space-between;
  align-items: center;
  span {
    font-size: 18px;
    &:first-child {
      margin-left: 10px;
      margin-top: 5px;
    }
    &:nth-child(2) {
      margin-bottom: 10px;
      font-size: 20px;
      cursor: pointer;
    }
  }
`;

const BoardCreateContents = styled.div`
  width: 80%;
  height: 80px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
`;

const BoardCreateId = styled(BoardCreateContents)`
  height: 80px;
  select {
    width: 70%;
    height: 35px;
    color: ${(props) => props.theme.lolTextColor};
    border: 1px solid ${(props) => props.theme.lolTextColor};
    background-color: ${(props) => props.theme.lolBgColorLight};
    text-align: center;
  }
  label {
    width: 70%;
    height: 35px;
    color: ${(props) => props.theme.lolTextColor};
    border: 1px solid ${(props) => props.theme.lolTextColor};
    background-color: ${(props) => props.theme.lolBgColorLight};
    text-align: center;
    line-height: 35px;
    cursor: pointer;
  }
`;

const BoardCreateMyPosition = styled.div`
  display: flex;
  height: 80%;
  flex-direction: column;
  justify-content: space-around;
  grid-area: header;
`;

const BoardCreateOtherPosition = styled(BoardCreateMyPosition)`
  grid-area: main;
  input {
    display: none;
  }
`;

const BoardCreateMic = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  grid-area: right;
  & > span {
    margin-top: 15px;
  }
  & > label {
    &:nth-child(3) {
      margin-top: 10px;
      color: ${(props) => props.theme.lolAccentColor1};

      &.checked {
        color: ${(props) => props.theme.lolAccentColor};
      }
    }
  }
`;

const ToggleButton = styled.span`
  & > input {
    display: none;
    &,
    &:after,
    &:before,
    & *,
    & *:after,
    & *:before,
    & + label {
      box-sizing: border-box;
      &::selection {
        background: none;
      }
    }
    &:checked + label {
      background: ${(props) => props.theme.lolBgColorLight};
      &:active {
        box-shadow: none;
        &:after {
          margin-left: -0.8em;
        }
      }
    }
    &:checked + label:after {
      left: 50%;
    }
  }
  & > label {
    outline: 0;
    display: block;
    width: 4em;
    height: 2em;
    position: relative;
    cursor: pointer;
    user-select: none;
    background: ${(props) => props.theme.lolBgColorNormal};
    border-radius: 2em;
    padding: 2px;
    transition: all 0.4s ease;
    border: 2px solid ${(props) => props.theme.lolTextColor};
    &:after,
    &:before {
      position: relative;
      display: block;
      content: "";
      width: 50%;
      height: 100%;
    }
    &:after {
      left: 0;
      border-radius: 2em;
      background: ${(props) => props.theme.lolTextColor};
      transition: left 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275),
        padding 0.3s ease, margin 0.3s ease;
      box-shadow: 0 0 0 1px rgba(0, 0, 0, 0.1), 0 4px 0 rgba(0, 0, 0, 0.08);
    }
    &:before {
      display: none;
    }
    &:hover:after {
      will-change: padding;
    }
    &:active {
      box-shadow: inset 0 0 0 2em ${(props) => props.theme.lolBgColorLight};
      &:after {
        padding-right: 0.8em;
      }
    }
  }
`;

const BoardCreateMemo = styled(BoardCreateContents)`
  height: 100px;
  margin: 0;
  textarea {
    font-family: "Roboto";
    font-size: 16px;
    width: 100%;
    height: 60px;
    padding: 5px;
    color: ${(props) => props.theme.lolTextColor};
    background-color: ${(props) => props.theme.lolBgColorLight};
    border: 1px solid ${(props) => props.theme.lolTextColor};
    resize: none;
    &:focus {
      outline: none;
    }
  }
`;

const BoardCreateButtons = styled(BoardCreateContents)`
  flex-direction: row-reverse;
  justify-content: right;
  button {
    font-size: 16px;
    font-weight: 600;
    height: 35px;
    border: none;
    background-color: ${(props) => props.theme.lolTextColor};
    cursor: pointer;
  }
`;

const ErrorMsg = styled.label`
  position: absolute;
  bottom: 50px;
  left: 50px;
  color: ${(props) => props.theme.lolAccentColor1};
`;

const Loading = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

function BoardCreate({ setPopupCreate }) {
  const [myLineCheck, setMyLineCheck] = useState([]);
  const [otherLineCheck, setOtherLineCheck] = useState([]);
  const [mic, setMic] = useState(false);
  const account = useRecoilValue(LoginState);
  const [isLoding, setIsloding] = useState(false);
  const [lolAccount, setLolAccount] = useState("");
  const [lolPuuid, setLolPuuid] = useState();

  useEffect(() => {
    (async () => {
      await axios
        .get("http://127.0.0.1:8080/board/create", {
          headers: {
            Authorization: account.token,
          },
        })
        .then(function (response) {
          if (Object.keys(response.data).length !== 0) {
            setLolAccount(Object.keys(response.data));
            setLolPuuid(response.data);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      setIsloding(false);
    })();
  }, [account.token]);

  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm();

  const onValid = async (data) => {
    if (myLineCheck.length !== 2 || otherLineCheck.length !== 2) {
      console.log("ERROR");
      setError("line", {
        message: "각 포지션 2가지 선택은 필수입니다.",
      });
    } else {
      data["myPositions"] = myLineCheck;
      data["otherPositions"] = otherLineCheck;
      data["lolPuuid"] = lolPuuid[data.lolId];
      console.log(data);
      await axios
        .post("http://localhost:8080/board/create", data, {
          headers: {
            Authorization: account.token,
          },
        })
        .then(function (response) {
          console.log(response);
          console.log(response.data);
        });

      overlayClose();
    }
  };

  const overlayClose = () => {
    setPopupCreate(false);
  };

  const addAccount = async () => {
    await axios
      .get("http://localhost:8080/lol/add", {
        headers: {
          Authorization: account.token,
        },
      })
      .then(function (response) {
        console.log(response.data);
      });
  };

  return (
    <>
      <Overlay onClick={overlayClose} />
      <Container onSubmit={handleSubmit(onValid)}>
        <BoardCreateHeader>
          <span>게시물 등록하기</span>
          <span onClick={overlayClose}>
            <FontAwesomeIcon icon={faXmark} />
          </span>
        </BoardCreateHeader>
        {isLoding ? (
          <Loading>loading</Loading>
        ) : (
          <>
            <BoardCreateId>
              <span>롤 아이디 선택하기</span>
              {lolAccount !== "" ? (
                <select {...register("lolId")}>
                  {lolAccount.map((v, i) => (
                    <option key={i}>{v}</option>
                  ))}
                  {lolAccount.length < 5 ? (
                    <option onClick={addAccount}>+ 롤 아이디 추가하기</option>
                  ) : (
                    ""
                  )}
                </select>
              ) : (
                <label {...register("lolId")}>loading ...</label>
              )}
            </BoardCreateId>
            <GridContainer>
              <BoardCreateMyPosition>
                <span>내 포지션</span>
                <LinePositions
                  useFor="createMy"
                  myLineCheck={myLineCheck}
                  setMyLineCheck={setMyLineCheck}
                />
              </BoardCreateMyPosition>
              <BoardCreateOtherPosition>
                <span>다른 사람 포지션</span>
                <LinePositions
                  useFor="createOther"
                  otherLineCheck={otherLineCheck}
                  setOtherLineCheck={setOtherLineCheck}
                />
                <input type="text" {...register("line")} />
              </BoardCreateOtherPosition>
              <BoardCreateMic>
                <label>마이크</label>
                <ToggleButton
                  onClick={() => setMic(document.querySelector("#tb").checked)}
                >
                  <input type="checkbox" id="tb" {...register("micEnabled")} />
                  <label htmlFor="tb"></label>
                </ToggleButton>
                <label className={mic ? "checked" : ""}>
                  {mic ? "ON" : "OFF"}
                </label>
              </BoardCreateMic>
            </GridContainer>

            <BoardCreateMemo>
              <label>메모</label>
              <textarea {...register("content")} />
            </BoardCreateMemo>
            <ErrorMsg>{errors?.line?.message}</ErrorMsg>
            <BoardCreateButtons>
              <button>듀오 모집하기</button>
            </BoardCreateButtons>
          </>
        )}
      </Container>
    </>
  );
}

export default BoardCreate;
