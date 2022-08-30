import styled from "styled-components";
import NavBar from "components/NavBar";
import MainHeader from "components/MainHeader";
import Post from "components/Post";
import PostHeader from "components/PostHeader";
import Alarm from "components/Alarm";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleUser,
  faXmark,
  faMicrophone,
  faHeart,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";
import { LineFilterState, TierFilterState } from "atoms";
import { useRecoilValue } from "recoil";

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

const Overlay = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 5;
`;

const PostDetail = styled.div`
  font-family: "Roboto", sans-serif;
  position: absolute;
  width: 30vw;
  height: 600px;
  top: 0vh;
  bottom: 0vh;
  left: 0vw;
  right: 0vw;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  overflow: hidden;
  border: 2px solid ${(props) => props.theme.lolTextColor};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  z-index: 9;
`;

const PostDetailHeader = styled.div`
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
  border-bottom: 1px solid ${(props) => props.theme.lolBgColor};
  div {
    display: flex;
    align-items: center;
    span {
      margin: 20px;
      color: ${(props) => props.theme.lolTextColor};
      &:first-child {
        font-size: 30px;
      }
    }
  }

  > span {
    margin: 20px;
    color: ${(props) => props.theme.lolTextColor};
    font-size: 20px;
    cursor: pointer;
  }
`;

const PostDetailUserInfo = styled.div`
  width: 100%;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  div {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    margin: 0 10px;
    color: ${(props) => props.theme.lolTextColor};
  }
  div:first-child {
    width: 40%;
    span {
      margin: 0 5px;
    }
    span img {
      width: 50px;
      height: 50px;
      padding: 3px;
      background-color: ${(props) => props.theme.lolBgColorLight};
      border-radius: 50%;
    }
  }
  div:nth-child(2) {
    width: 20%;
    line-height: 20px;
  }

  div:nth-child(3) {
    font-size: 20px;
  }
`;

const PostDetailLineInfo = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 180px;
  background-color: yellow;
`;

const PostDetailLatestGame = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 100px;
  background-color: tomato;
`;

const PostDetailMemo = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 100px;
  background-color: yellowgreen;
`;

const PostDetailButtons = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 50px;
  background-color: yellow;
`;

const LoadingMsg = styled.label`
  font-size: 18px;
  color: ${(props) => props.theme.lolTextColor};
  padding: 20px 0;
`;

function Home() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [popup, setPopup] = useState(0);

  useEffect(() => {
    (async () => {
      const response = await fetch("http://localhost:8000/posts");
      const json = await response.json();
      setPosts(json);
      setLoading(false);
    })();
  }, []);

  const dataJSON = {
    post: {
      1: {
        status: 1,
        nickName: "방황하는 프로도",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "11초 전",
      },
      2: {
        status: 1,
        nickName: "청소하는 튜브",
        tier: "Gold",
        myline: ["All", "Jungle"],
        yourline: ["All", "Top"],
        memo: "듀오하실 탑 구합니다",
        date: "20초 전",
      },
      3: {
        status: 1,
        nickName: "기침하는 해삼",
        tier: "Bronze",
        myline: ["All", "Top"],
        yourline: ["All", "Mid"],
        memo: "듀오하실분",
        date: "2분 전",
      },
      4: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Bot"],
        memo: "탑 구해요 탑 구해요 ",
        date: "2분 전",
      },
      5: {
        status: 0,
        nickName: "노트북",
        tier: "Grandmaster",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      6: {
        status: 0,
        nickName: "노트북",
        tier: "Platinum",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      7: {
        status: 0,
        nickName: "노트북",
        tier: "Master",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      8: {
        status: 0,
        nickName: "노트북",
        tier: "Iron",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      9: {
        status: 0,
        nickName: "노트북",
        tier: "Challenger",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      10: {
        status: 0,
        nickName: "노트북",
        tier: "Diamond",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      11: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      12: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      13: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      14: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      15: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "Mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
      16: {
        status: 0,
        nickName: "노트북",
        tier: "Silver",
        myline: ["All", "mid"],
        yourline: ["All", "Top"],
        memo: "탑 구해요",
        date: "2분 전",
      },
    },
  };

  const onOverlayClicked = () => {
    setPopup(0);
  };

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
                {posts.map((v) => {
                  if (lineFilter !== "All") {
                    if (v.myline[1] !== lineFilter) {
                      return "";
                    }
                  }
                  if (tierFilter !== "All") {
                    if (v.tier !== tierFilter) {
                      return "";
                    }
                  }
                  return (
                    <Post
                      key={v.id}
                      postId={v.id}
                      data={v}
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
      {popup !== 0 ? (
        <>
          <Overlay onClick={onOverlayClicked}></Overlay>
          <PostDetail>
            <PostDetailHeader>
              <div>
                <span>
                  <FontAwesomeIcon icon={faCircleUser} />
                </span>
                <span>{dataJSON.post[popup].nickName}</span>
              </div>
              <span onClick={onOverlayClicked}>
                <FontAwesomeIcon icon={faXmark} />
              </span>
            </PostDetailHeader>
            <PostDetailUserInfo>
              <div>
                <span>
                  <img
                    src={`../img/emblems/Emblem_${dataJSON.post[popup].tier}.png`}
                    alt="lolLogo"
                  />
                </span>
                <span>롤 아이디</span>
              </div>
              <div>
                162 138패
                <br />
                승률 54.0%
              </div>
              <div>
                <div>
                  <FontAwesomeIcon icon={faMicrophone} />
                </div>

                <div>
                  <FontAwesomeIcon icon={faHeart} />
                  18
                </div>
              </div>
            </PostDetailUserInfo>
            <PostDetailLineInfo>라인 및 챔피언별 승률</PostDetailLineInfo>
            <PostDetailLatestGame>최근 경기 결과</PostDetailLatestGame>
            <PostDetailMemo>게시물 메모</PostDetailMemo>
            <PostDetailButtons>버튼</PostDetailButtons>
          </PostDetail>
        </>
      ) : (
        ""
      )}
    </>
  );
}

export default Home;
