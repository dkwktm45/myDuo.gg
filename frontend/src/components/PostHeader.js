import styled from "styled-components";

const PostTitleWrapper = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: ${(props) => props.theme.lolBgColorNormal};
  padding: 0;
`;

const PostTitle = styled.span`
  font-size: 14px;
  font-family: "Roboto", sans-serif;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px;
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

function PostHeader() {
  return (
    <PostTitleWrapper>
      <PostTitle>마감 여부</PostTitle>
      <PostTitle>사용자 이름</PostTitle>
      <PostTitle>티어</PostTitle>
      <PostTitle>지원 포지션</PostTitle>
      <PostTitle>선호 포지션</PostTitle>
      <PostTitle>메모</PostTitle>
      <PostTitle>등록 일시</PostTitle>
    </PostTitleWrapper>
  );
}

export default PostHeader;
