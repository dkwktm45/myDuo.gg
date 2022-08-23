import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell } from "@fortawesome/free-solid-svg-icons";

const Container = styled.div`
  position: fixed;
  bottom: 0;
  right: 0;
  width: 100px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Wrapper = styled.div`
  width: 70px;
  height: 70px;
  border: 2px solid ${(props) => props.theme.lolBgColorLight};
  font-size: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${(props) => props.theme.lolTextColor};
  border-radius: 50%;
  cursor: pointer;
`;

function Alarm() {
  return (
    <Container>
      <Wrapper>
        <FontAwesomeIcon icon={faBell} />
      </Wrapper>
    </Container>
  );
}

export default Alarm;
