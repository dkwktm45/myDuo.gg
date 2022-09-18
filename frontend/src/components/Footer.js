import styled from "styled-components";

const Container = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 40px;
`;
const Wrapper = styled.div`
  height: 60px;
  font-family: "Roboto";
  color: ${(props) => props.theme.lolTextColor};
  display: flex;
  justify-content: center;
  p {
    margin: 0 5px;
  }
`;
const Footer = () => {
  return (
    <Container>
      <Wrapper>
        <p>Copyright 2022.MyDuo</p>
        <p> All rights reserved.</p>
      </Wrapper>
    </Container>
  );
};

export default Footer;
