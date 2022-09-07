import styled from "styled-components";

const DuoChatList = styled.div`
  width: 95%;
  height: 60px;
  border: 1px solid ${(props) => props.theme.lolTextColor};
  margin: 5px 0;
  cursor: pointer;
  display: grid;
  grid-template-columns: 50px 120px auto 40px;
  transition: all 0.2s ease-in-out;
  div {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    &:first-child {
      img {
        width: 30px;
        height: 30px;
      }
    }
    &:nth-child(2) {
      color: ${(props) => props.theme.lolTextColor};
      justify-content: left;
    }
    &:nth-child(3) {
      font-size: 15px;
      flex-direction: column;
      align-items: flex-start;
      label {
        cursor: pointer;
        margin-left: 5px;
        &:nth-child(2) {
          margin-left: 10px;
          margin-top: 3px;
          font-size: 13px;
          color: #aaa;
        }
      }
    }
    &:nth-child(4) {
      span {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: tomato;
        width: 25px;
        height: 25px;
        border-radius: 50%;
        font-size: 13px;
      }
    }
  }
  &:hover {
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
  &.selected {
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
`;

function ChatListItem({ ...props }) {
  const handleChatRoom = () => {
    if (props.data === props.chatRoom) {
      props.setChatRoom("");
    } else {
      props.setChatRoom(props.data);
    }
  };
  return (
    <DuoChatList
      key={props.index}
      onClick={handleChatRoom}
      className={props.data === props.chatRoom ? "selected" : ""}
    >
      <div>
        <img src={`../img/emblems/Emblem_Silver.png`} alt="lolLogo" />
      </div>
      <div>{props.data}</div>
      <div>
        <label>안녕하세요</label>
        <label>오후 3:13</label>
      </div>
      <div>
        <span>3</span>
      </div>
    </DuoChatList>
  );
}

export default ChatListItem;
