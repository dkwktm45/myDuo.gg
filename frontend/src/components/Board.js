import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid ${(props) => props.theme.lolBgColorDark};
  cursor: pointer;
  &:hover {
    background-color: ${(props) => props.theme.lolBgColorNormal};
  }
`;

const PostContent = styled.div`
  width: 100px;
  height: 30px;
  font-size: 14px;
  font-family: "Roboto", sans-serif;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  &:nth-child(1) {
    width: 5%;
  }
  &:nth-child(2) {
    width: 15%;
  }
  &:nth-child(3) {
    width: 10%;
  }
  &:nth-child(4),
  &:nth-child(5) {
    width: 7%;
  }
  &:nth-child(6) {
    width: 17%;
  }
  &:nth-child(7) {
    width: 10%;
  }
`;

const PostContentTier = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-evenly;
  img {
    width: 30px;
    height: 30px;
  }
`;

const PostContentPositions = styled.div`
  display: flex;
  justify-content: center;
  margin: 10px;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
`;

const Item = styled.label`
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  font-size: 20px;
  color: ${(props) => props.theme.lolTextColor};
  img,
  span {
    cursor: pointer;
    position: absolute;
    width: 80%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
`;

const RadioButtonLabel = styled.label`
  font-size: 20px;
  width: 40px;
  height: 40px;
  background-color: blue;
  cursor: pointer;
  color: ${(props) => props.theme.lolTextColor};
  border: 2px solid ${(props) => props.theme.lolBgColorLight};
  background-color: ${(props) => props.theme.lolBgColorNormal};
`;

const RadioButton = styled.input`
  cursor: pointer;
  display: none;
  transition: all 0.2s ease-in-out;
`;

function Board({ postId, data, setPopupBoard, setPopupUser }) {
  const positionName = ["ALL", "TOP", "JUNGLE", "MID", "BOT", "SUPPORT"];

  const onClick = () => {
    setPopupBoard(postId);
    setPopupUser(data.summonerName);

    /* 듀오 모집 진행 중 or 마감 표시 로직 구현 예정
    if (data.closingStatus === true) {
      setPopupBoard(postId);
    }
    */
  };

  return (
    <Wrapper onClick={onClick}>
      <PostContent>{data.closingStatus === 1 ? "모집 중" : "마감"}</PostContent>
      <PostContent>{data.summonerName}</PostContent>
      <PostContent>
        <PostContentTier>
          <img src={`../img/emblems/Emblem_${data.tier}.png`} alt="lolLogo" />
          {data.tier}
          {" " + data.rank}
        </PostContentTier>
      </PostContent>
      <PostContent>
        <PostContentPositions>
          {data.myPositions.map((v, i) => (
            <Item key={i}>
              <RadioButton
                type="radio"
                name="selectedLine"
                id={v}
                value={v}
                line={v}
                isHeader={true}
              />
              <RadioButtonLabel htmlFor={v} />
              {v === 0 ? (
                <span>
                  <FontAwesomeIcon icon={faStarOfLife} />
                </span>
              ) : (
                <img
                  src={`../img/positions/Position_Gold-${positionName[v]}.png`}
                  alt=""
                />
              )}
            </Item>
          ))}
        </PostContentPositions>
      </PostContent>
      <PostContent>
        <PostContentPositions>
          {data.otherPositions.map((v, i) => (
            <Item key={i}>
              <RadioButton
                type="radio"
                name="selectedLine"
                id={v}
                value={v}
                line={v}
                isHeader={true}
              />
              <RadioButtonLabel htmlFor={v} />
              {v === 0 ? (
                <span>
                  <FontAwesomeIcon icon={faStarOfLife} />
                </span>
              ) : (
                <img
                  src={`../img/positions/Position_Gold-${positionName[v]}.png`}
                  alt=""
                />
              )}
            </Item>
          ))}
        </PostContentPositions>
      </PostContent>
      <PostContent>{data.content}</PostContent>
      <PostContent>{calcElapsed(data.registrationTime)} 전</PostContent>
    </Wrapper>
  );
}

function calcElapsed(time) {
  const elapsedSec = (Date.now() - time) / 1000;
  if (elapsedSec >= 60) {
    const elapsedMin = elapsedSec / 60;
    if (elapsedMin >= 60) {
      const elapsedHour = elapsedMin / 60;
      if (elapsedHour >= 24) {
        const elapsedDay = elapsedHour / 24;
        return Math.floor(elapsedDay) + "일";
      } else {
        return Math.floor(elapsedHour) + "시간";
      }
    } else {
      return Math.floor(elapsedMin) + "분";
    }
  } else {
    return Math.floor(elapsedSec) + "초";
  }
}

export default Board;
