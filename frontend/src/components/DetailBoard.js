import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleUser,
  faXmark,
  faMicrophone,
  faHeart,
} from "@fortawesome/free-solid-svg-icons";

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

function DetailBoard({ setPopup, postId }) {
  const dataTest = "test";
  const overlayClose = () => {
    setPopup("");
  };
  return (
    <>
      <Overlay onClick={overlayClose}></Overlay>
      <PostDetail>
        <PostDetailHeader>
          <div>
            <span>
              <FontAwesomeIcon icon={faCircleUser} />
            </span>
            <span>{dataTest}</span>
          </div>
          <span onClick={overlayClose}>
            <FontAwesomeIcon icon={faXmark} />
          </span>
        </PostDetailHeader>
        <PostDetailUserInfo>
          <div>
            <span>
              <img
                src={`../img/emblems/Emblem_${dataTest}.png`}
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
  );
}

export default DetailBoard;
