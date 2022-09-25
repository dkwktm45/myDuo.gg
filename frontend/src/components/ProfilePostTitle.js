import React from "react";
import styled from "styled-components";

const PostContainet = styled.div`
  width: 100%;
  height: 70px;
  margin: 0;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 10px 0;

  .title {
    width: 100%;
    font-size: 30px;
    display: flex;
    justify-content: flex-start;
  }
  .subTitle {
    padding: 5px 0;
    width: 100%;
    display: flex;
    flex-direction: row;
  }
  .subTitle > div {
    width: 100%;
    border: 1px solid black;
  }
`;

function MyPosts() {
  return (
    <PostContainet>
      <div className="title">
        <spen>내가 쓴 게시물</spen>
      </div>
      <div className="subTitle">
        <div className="subTitle_Toggle">마감여부</div>
        <div className="subTitle_ID">내 아이디</div>
        <div className="subTitle_Jposition">지원포지션</div>
        <div className="subTitle_Sposition">선호포지션</div>
        <div className="subTitle_Memo">메모</div>
        <div className="subTitle_Date">등록일시</div>
        <div className="subTitle_Date">수정/삭제</div>
      </div>
    </PostContainet>
  );
}
export default MyPosts;
