import styled from "styled-components";
import NavBar from "components/NavBar";
import MainHeader from "components/MainHeader";
import Post from "components/Post";
import PostHeader from "components/PostHeader";
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

const Posts = styled.div`
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
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [popup, setPopup] = useState("");

  useEffect(() => {
    (async () => {
      const response = await fetch("http://localhost:8000/boards");
      const json = await response.json();
      //json.forEach((v) => console.log(v, lineFilter));
      setPosts(json);
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
            <PostHeader />
            {loading ? (
              <LoadingMsg>Loading...</LoadingMsg>
            ) : (
              <Posts>
                {posts.map((data) => {
                  if (lineFilter !== "ALL") {
                    if (data.myPositions[1] !== lineFilter) {
                      return "";
                    }
                  }
                  if (tierFilter !== "ALL") {
                    if (data.boardUserTier !== tierFilter) {
                      return "";
                    }
                  }
                  return (
                    <Post
                      key={data.boardId}
                      postId={data.boardId}
                      data={data}
                      setPopup={setPopup}
                    />
                  );
                })}
              </Posts>
            )}
          </Main>
        </Wrapper>
      </Container>
      <Alarm />
      {popup !== "" ? <DetailBoard postId={popup} setPopup={setPopup} /> : ""}
    </>
  );
}

export default Home;
