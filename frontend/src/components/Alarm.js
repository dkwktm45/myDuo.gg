import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faXmark } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";

const Overlay = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2;
`;

const Container = styled.div`
  position: fixed;
  bottom: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px;
  z-index: 3;
`;

const WrapperClosed = styled.div`
  width: 60px;
  height: 60px;
  font-size: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: ${(props) => props.theme.lolTextColor};
  border-radius: 50%;
  cursor: pointer;
`;

const WrapperOpened = styled.div`
  width: 350px;
  height: 600px;
  border: none;
  border-radius: 30px;
  background-color: ${(props) => props.theme.lolTextColor};
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Header = styled.div`
  width: 95%;
  height: 30px;
  display: flex;
  justify-content: space-between;
  margin: 20px 0;
  label {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 5px;
    margin: 0 20px;
    cursor: pointer;
    font-size: 20px;
    &:first-child {
      margin-top: 10px;
    }
  }
`;

const Body = styled.div`
  width: 80%;
  height: 80%;
  background-color: ${(props) => props.theme.lolBgColorLight};
  color: ${(props) => props.theme.lolTextColor};
`;

function Alarm() {
  const [isOpen, setIsOpen] = useState(false);
  const handleAlarmWindow = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      {isOpen ? <Overlay onClick={handleAlarmWindow} /> : ""}
      <Container>
        {!isOpen ? (
          <WrapperClosed onClick={handleAlarmWindow}>
            <FontAwesomeIcon icon={faBell} />
          </WrapperClosed>
        ) : (
          <WrapperOpened>
            <Header>
              <label>알림</label>
              <label onClick={handleAlarmWindow}>
                <FontAwesomeIcon icon={faXmark} />
              </label>
            </Header>
            <Body>sdfsakl</Body>
          </WrapperOpened>
        )}
      </Container>
    </>
  );
}

export default Alarm;
