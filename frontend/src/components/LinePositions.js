import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";

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
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
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
  cursor: pointer;
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

function LinePositions({ ...props }) {
  const lines = ["ALL", "TOP", "JUNGLE", "MID", "BOT", "SUPPORT"];
  const handleSelection = (e) => {
    const value = parseInt(e.target.value);
    if (e.target.name === "createMy") {
      if (props.myLineCheck.includes(value)) {
        props.myLineCheck.splice(props.myLineCheck.indexOf(value), 1);
        props.setMyLineCheck(props.myLineCheck);
      } else if (props.myLineCheck.length < 2) {
        props.myLineCheck.push(value);
        props.setMyLineCheck(props.myLineCheck);
      } else {
        document.querySelector(
          "#" + e.target.name + lines[props.myLineCheck[0]]
        ).checked = false;
        props.myLineCheck.shift();
        props.myLineCheck.push(value);
        props.setMyLineCheck(props.myLineCheck);
      }
    } else if (e.target.name === "createOther") {
      if (props.otherLineCheck.includes(value)) {
        props.otherLineCheck.splice(props.otherLineCheck.indexOf(value), 1);
        props.setOtherLineCheck(props.otherLineCheck);
      } else if (props.otherLineCheck.length < 2) {
        props.otherLineCheck.push(value);
        props.setOtherLineCheck(props.otherLineCheck);
      } else {
        document.querySelector(
          "#" + e.target.name + lines[props.otherLineCheck[0]]
        ).checked = false;
        props.otherLineCheck.shift();
        props.otherLineCheck.push(value);
        props.setOtherLineCheck(props.otherLineCheck);
      }
    }
  };
  return (
    <Container>
      {lines.map((line, i) => {
        return (
          <Item key={props.useFor + line}>
            <CheckBox
              type="checkbox"
              id={props.useFor + line}
              name={props.useFor}
              value={i}
              onChange={handleSelection}
            />
            <CheckBoxLabel htmlFor={props.useFor + line}>
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
