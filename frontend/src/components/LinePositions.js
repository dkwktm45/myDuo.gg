import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";

const Container = styled.div`
  display: flex;
`;

const Item = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  font-size: 20px;
  color: ${(props) => props.theme.lolTextColor};
`;

const CheckBoxLabel = styled.label`
  font-size: 20px;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${(props) => props.theme.lolTextColor};
  border: 2px solid ${(props) => props.theme.lolBgColorLight};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  img {
    width: 100%;
  }
`;

const CheckBox = styled.input`
  display: none;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  &:checked + ${CheckBoxLabel} {
    border: 2px solid ${(props) => props.theme.lolTextColor};
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
  &:checked + ${CheckBoxLabel}, &:hover + ${CheckBoxLabel} {
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
`;

function LinePositions({ useFor }) {
  const lines = ["ALL", "TOP", "JUNGLE", "MID", "BOT", "SUPPORT"];
  const [selectedMyLine, setSelectedMyLine] = useState([0, 0, 0, 0, 0]);
  const handleSelection = (e) => {
    console.log(e);
    console.log(e.target.value, e.target.name);
  };
  return (
    <Container>
      {lines.map((line, i) => {
        return (
          <Item key={useFor + line}>
            <CheckBox
              type="checkbox"
              id={useFor + line}
              name={useFor}
              value={i}
              onChange={handleSelection}
            />
            <CheckBoxLabel htmlFor={useFor + line}>
              {line === "ALL" ? (
                <FontAwesomeIcon icon={faStarOfLife} />
              ) : (
                <img
                  src={`../img/positions/Position_Gold-${line}.png`}
                  alt=""
                />
              )}
            </CheckBoxLabel>
          </Item>
        );
      })}
    </Container>
  );
}

export default LinePositions;
