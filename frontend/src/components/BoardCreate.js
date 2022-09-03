import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";

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

const Container = styled.div`
  font-family: "Roboto", sans-serif;
  position: absolute;
  width: 480px;
  height: 620px;
  top: 0vh;
  bottom: 0vh;
  left: 0vw;
  right: 0vw;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  border: 2px solid ${(props) => props.theme.lolTextColor};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  color: ${(props) => props.theme.lolTextColor};
  z-index: 9;
`;

const ContainerHeader = styled.div`
  width: 90%;
  height: 70px;
  border-bottom: 1px solid ${(props) => props.theme.lolTextColor};
  display: flex;
  justify-content: space-between;
  align-items: center;
  span {
    font-size: 18px;
    &:first-child {
      margin-left: 10px;
      margin-top: 5px;
    }
    &:nth-child(2) {
      margin-bottom: 10px;
      font-size: 20px;
      cursor: pointer;
    }
  }
`;

function BoardCreate({ setPopupCreate }) {
  const overlayClose = () => {
    setPopupCreate(false);
  };

  return (
    <>
      <Overlay onClick={overlayClose} />
      <Container>
        <ContainerHeader>
          <span>게시물 등록하기</span>
          <span onClick={overlayClose}>
            <FontAwesomeIcon icon={faXmark} />
          </span>
        </ContainerHeader>
      </Container>
    </>
  );
}

export default BoardCreate;
