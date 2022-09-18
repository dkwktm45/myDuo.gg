import styled from "styled-components";
import NavBar from "components/NavBar";
import MainHeader from "components/MainHeader";
import Board from "components/Board";
import BoardHeader from "components/BoardHeader";
import Alarm from "components/Alarm";
import { useState } from "react";
import { useEffect } from "react";
import {
  LineFilterState,
  LoginState,
  TierFilterState,
  BoardStatus,
} from "atoms";
import { useRecoilValue, useRecoilState } from "recoil";
import BoardDetail from "components/BoardDetail";
import BoardCreate from "components/BoardCreate";
import { loadBoards } from "services/apiServices";
import { useQuery } from "react-query";
import axios from "axios";
import Footer from "components/Footer";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 1200px;
  margin-top: 100px;
`;

const Main = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  background-color: ${(props) => props.theme.lolBgColorLight};
`;

const Boards = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
`;

const LoadingMsg = styled.label`
  font-size: 18px;
  color: ${(props) => props.theme.lolTextColor};
  padding: 20px 0;
`;

const MoreButton = styled.div`
  font-family: "Roboto";
  margin: 30px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${(props) => props.theme.lolTextColor};
  background-color: transparent;
  cursor: pointer;
`;

function Home() {
  const [popupBoard, setPopupBoard] = useState("");
  const [popupUser, setPopupUser] = useState("");
  const [popupCreate, setPopupCreate] = useState(false);
  const account = useRecoilValue(LoginState);
  const [boardStatus, setBoardStatus] = useRecoilState(BoardStatus);
  const lineFilter = useRecoilValue(LineFilterState);
  const tierFilter = useRecoilValue(TierFilterState);
  const [refresh, setRefresh] = useState(true);
  const [moreData, setMoreData] = useState([]);
  const { isLoading, data } = useQuery(
    ["boards", { token: account.token }, refresh],
    loadBoards
  );

  /* 첫 보드 페이지 로딩 */
  useEffect(() => {
    setRefresh(true);
    if (data !== undefined) {
      setBoardStatus({
        new: data[0].registrationTime,
        old: data[data.length - 1].registrationTime,
      });
    }
  }, [refresh, data, setBoardStatus]);

  const moreBoards = () => {
    axios
      .get("http://localhost:8080/board/scroll/", {
        headers: {
          Authorization: account.token,
        },
        params: {
          page: 0,
          registrationTime: boardStatus.old,
          size: 20,
          sort: "registrationTime,desc",
        },
      })
      .then((response) => {
        const newData = response.data.content;
        setMoreData([...moreData, ...newData]);
        setBoardStatus({
          old: newData[newData.length - 1].registrationTime,
        });
      });
  };

  return (
    <>
      <NavBar />
      <Container>
        <Wrapper>
          <MainHeader setPopupCreate={setPopupCreate} />
          <Main>
            <BoardHeader />
            {isLoading ? (
              <LoadingMsg>Loading...</LoadingMsg>
            ) : (
              <Boards>
                {data.map((data, i) => {
                  if (
                    (lineFilter === "ALL" ||
                      data.myPositions.includes("ALL") ||
                      data.myPositions.includes(lineFilter)) &&
                    (tierFilter === "ALL" || data.boardUserTier === tierFilter)
                  ) {
                    return (
                      <Board
                        key={data.boardUuid}
                        postId={data.boardUuid}
                        data={data}
                        setPopupBoard={setPopupBoard}
                        setPopupUser={setPopupUser}
                      />
                    );
                  } else {
                    return "";
                  }
                })}

                {moreData.length !== 0
                  ? moreData.map((v, j) => {
                      return (
                        <Board
                          key={v.boardUuid}
                          postId={v.boardUuid}
                          data={v}
                          setPopupBoard={setPopupBoard}
                        />
                      );
                    })
                  : ""}
              </Boards>
            )}
            <MoreButton type="button" onClick={moreBoards}>
              더보기
            </MoreButton>
          </Main>
        </Wrapper>
        <Footer />
      </Container>
      <Alarm />
      {popupBoard !== "" ? (
        <BoardDetail
          data={data.filter((data) => data.boardUuid === popupBoard)[0]}
          setPopupBoard={setPopupBoard}
          popupUser={popupUser}
        />
      ) : (
        ""
      )}
      {popupCreate === true ? (
        <BoardCreate setPopupCreate={setPopupCreate} setRefresh={setRefresh} />
      ) : (
        ""
      )}
    </>
  );
}

export default Home;
