import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";

const Position = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  font-size: 20px;
  color: ${(props) => props.theme.lolTextColor};
  border: 2px solid ${(props) => props.theme.lolBgColorLight};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  transition: all 0.2s ease-in-out;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  cursor: pointer;
  img {
    width: 80%;
  }
  &:hover {
    background-color: ${(props) => props.theme.lolBgColorLight};
    span {
      display: block;
    }
  }
`;

const PositionsInfo = styled.span`
  position: absolute;
  font-size: 16px;
  text-align: center;
  line-height: 16px;
  padding: 10px;
  width: 50px;
  top: 40px;
  left: 10px;
  display: none;
  background-color: ${(props) => props.theme.lolBgColorLight};
  border: 1px solid ${(props) => props.theme.lolBgColorNormal};
`;

function LinePosition({ line, isHeader }) {
  const positions = {
    All: "전체",
    Top: "탑",
    Jungle: "정글",
    Mid: "미드",
    Bot: "원딜",
    Support: "서폿",
  };

  if (isHeader) {
    return (
      <Position>
        {line === "All" ? (
          <>
            <FontAwesomeIcon icon={faStarOfLife} />
            <PositionsInfo>{positions[line]}</PositionsInfo>
          </>
        ) : (
          <>
            <img src={`../img/positions/Position_Gold-${line}.png`} alt="" />
            <PositionsInfo>{positions[line]}</PositionsInfo>
          </>
        )}
      </Position>
    );
  } else {
    return (
      <Position>
        {line === "All" ? (
          <FontAwesomeIcon icon={faStarOfLife} />
        ) : (
          <img src={`../img/positions/Position_Gold-${line}.png`} alt="" />
        )}
      </Position>
    );
  }
}

export default LinePosition;
