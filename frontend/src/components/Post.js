import styled from "styled-components";
import LinePosition from "components/LinePosition";

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

function Post({ postId, data, setPopup }) {
  const onClick = () => {
    setPopup(postId);
  };
  return (
    <Wrapper onClick={onClick}>
      <PostContent>{data.status === 1 ? "모집 중" : "마감"}</PostContent>
      <PostContent>{data.nickName}</PostContent>
      <PostContent>
        <PostContentTier>
          <img src={`../img/emblems/Emblem_${data.tier}.png`} alt="lolLogo" />
          {data.tier}
        </PostContentTier>
      </PostContent>
      <PostContent>
        <PostContentPositions>
          {data.myline.map((v) => (
            <LinePosition key={v} line={v} isHeader={false}></LinePosition>
          ))}
        </PostContentPositions>
      </PostContent>
      <PostContent>
        <PostContentPositions>
          {data.yourline.map((v) => (
            <LinePosition key={v} line={v} isHeader={false}></LinePosition>
          ))}
        </PostContentPositions>
      </PostContent>
      <PostContent>{data.memo}</PostContent>
      <PostContent>{data.date}</PostContent>
    </Wrapper>
  );
}

export default Post;
