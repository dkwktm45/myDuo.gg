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
  width: 80%;
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

function Post({ postId, data, setPopup }) {
  const onClick = () => {
    setPopup(postId);
  };

  return (
    <Wrapper onClick={onClick}>
      <PostContent>
        {data.boardRecruitmentYn === 1 ? "모집 중" : "마감"}
      </PostContent>
      <PostContent>{data.boardName}</PostContent>
      <PostContent>
        <PostContentTier>
          <img
            src={`../img/emblems/Emblem_${data.boardUserTier.replace(
              /\b[a-z]/g,
              (char) => char.toUpperCase()
            )}.png`}
            alt="lolLogo"
          />
          {data.boardUserTier.replace(/\b[a-z]/g, (char) => char.toUpperCase())}
        </PostContentTier>
      </PostContent>
      <PostContent>
        <PostContentPositions>
          {data.myPositions.map((v) => (
            <Item key={v}>
              <RadioButton
                type="radio"
                name="selectedLine"
                id={v}
                value={v}
                line={v}
                isHeader={true}
              />
              <RadioButtonLabel htmlFor={v} />
              {v === "ALL" ? (
                <span>
                  <FontAwesomeIcon icon={faStarOfLife} />
                </span>
              ) : (
                <img src={`../img/positions/Position_Gold-${v}.png`} alt="" />
              )}
            </Item>
          ))}
        </PostContentPositions>
      </PostContent>
      <PostContent>
        <PostContentPositions>
          {data.otherPositions.map((v) => (
            <Item key={v}>
              <RadioButton
                type="radio"
                name="selectedLine"
                id={v}
                value={v}
                line={v}
                isHeader={true}
              />
              <RadioButtonLabel htmlFor={v} />
              {v === "ALL" ? (
                <span>
                  <FontAwesomeIcon icon={faStarOfLife} />
                </span>
              ) : (
                <img src={`../img/positions/Position_Gold-${v}.png`} alt="" />
              )}
            </Item>
          ))}
        </PostContentPositions>
      </PostContent>
      <PostContent>{data.boardContent}</PostContent>
      <PostContent>{data.boardRegDt}</PostContent>
    </Wrapper>
  );
}

export default Post;
