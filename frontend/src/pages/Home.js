import styled from "styled-components";
import NavBar from "components/NavBar";
import MainHeader from "components/MainHeader";
import Board from "components/Board";
import BoardHeader from "components/BoardHeader";
import Alarm from "components/Alarm";
import { useState } from "react";
import { useEffect } from "react";
import { LineFilterState, TierFilterState } from "atoms";
import { useRecoilValue } from "recoil";
import DetailBoard from "components/DetailBoard";

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

function Home() {
  const [boards, setBoards] = useState([]);
  const [loading, setLoading] = useState(true);
  const [popup, setPopup] = useState("");

  useEffect(() => {
    (async () => {
      const response = await fetch("http://localhost:8000/boards");
      const json = await response.json();
      //json.forEach((v) => console.log(v, lineFilter));
      setBoards(json);
      setLoading(false);
    })();
  }, []);

  const lineFilter = useRecoilValue(LineFilterState);
  const tierFilter = useRecoilValue(TierFilterState);

  return (
    <>
      <NavBar />
      <Container>
        <Wrapper>
          <MainHeader />
          <Main>
            <BoardHeader />
            {loading ? (
              <LoadingMsg>Loading...</LoadingMsg>
            ) : (
              <Boards>
                {boards.map((data) => {
                  if (
                    (lineFilter === "ALL" ||
                      data.myPositions.includes("ALL") ||
                      data.myPositions.includes(lineFilter)) &&
                    (tierFilter === "ALL" || data.boardUserTier === tierFilter)
                  ) {
                    return (
                      <Board
                        key={data.boardId}
                        postId={data.boardId}
                        data={data}
                        setPopup={setPopup}
                      />
                    );
                  } else {
                    return "";
                  }
                })}
              </Boards>
            )}
          </Main>
        </Wrapper>
      </Container>
      <Alarm />
      {popup !== "" ? (
        <DetailBoard
          data={boards.filter((data) => data.boardId === popup)[0]}
          setPopup={setPopup}
        />
      ) : (
        ""
      )}
    </>
  );
}

export default Home;
