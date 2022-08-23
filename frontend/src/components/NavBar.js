import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleUser,
  faHouse,
  faMessage,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";

const Container = styled.div`
  position: fixed;
  top: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 70px;
  background-color: ${(props) => props.theme.lolBgColorNormal};
  border-bottom: 2px solid ${(props) => props.theme.lolTextColor};
  z-index: 10;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
`;

const Mark = styled.span`
  font-size: 32px;
  color: ${(props) => props.theme.lolTextColor};
  margin: 0 20px;
`;
const GameType = styled.span`
  width: 40px;
  height: 40px;
  img {
    width: 40px;
  }
`;
const Icons = styled.div`
  display: flex;
  align-items: center;
  margin-right: 40px;
`;

const Icon = styled.div`
  font-size: 25px;
  font-weight: 100;
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${(props) => props.theme.lolTextColor};
  &:nth-child(3) {
    font-size: 28px;
  }
`;

function NavBar() {
  return (
    <Container>
      <Wrapper>
        <Mark>MyDuo</Mark>
        <GameType>
          <img src="../img/logo/lolLogo.png" alt="lolLogo" />
        </GameType>
      </Wrapper>
      <Wrapper>
        <Icons>
          <Icon>
            <Link to="/lol/home">
              <FontAwesomeIcon icon={faHouse} />
            </Link>
          </Icon>
          <Icon>
            <Link to="/chat">
              <FontAwesomeIcon icon={faMessage} />
            </Link>
          </Icon>
          <Icon>
            <Link to="/profile">
              <FontAwesomeIcon icon={faCircleUser} />
            </Link>
          </Icon>
        </Icons>
      </Wrapper>
    </Container>
  );
}
export default NavBar;
