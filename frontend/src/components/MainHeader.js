import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretDown } from "@fortawesome/free-solid-svg-icons";
import LinePosition from "components/LinePosition";

const Upper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);
`;

const UpperContents = styled.div`
  display: flex;
  align-items: center;
  &:nth-child(2) {
    justify-content: right;
  }
`;

const Positions = styled.div`
  display: flex;
  justify-content: center;
  margin: 10px;
`;

const Tier = styled.div`
  width: 100px;
  height: 40px;
  font-size: 16px;
  line-height: 40px;
  text-align: center;
  background-color: ${(props) => props.theme.lolTextColor};
  color: ${(props) => props.theme.lolBgColor};
  margin: 0 20px;
  span {
    margin: 0 5px;
  }
`;

const UpLoadButton = styled.button`
  font-family: "Hanna";
  width: 130px;
  height: 39px;
  font-size: 16px;
  border: none;
  margin: 0 20px;
  background-color: ${(props) => props.theme.lolTextColor};
  color: ${(props) => props.theme.lolBgColor};
  cursor: pointer;
`;

function MainHeader() {
  return (
    <Upper>
      <UpperContents>
        <Positions>
          <LinePosition key={"All"} line={"All"} isHeader={true}></LinePosition>
          <LinePosition key={"Top"} line={"Top"} isHeader={true}></LinePosition>
          <LinePosition
            key={"Jungle"}
            line={"Jungle"}
            isHeader={true}
          ></LinePosition>
          <LinePosition key={"Mid"} line={"Mid"} isHeader={true}></LinePosition>
          <LinePosition key={"Bot"} line={"Bot"} isHeader={true}></LinePosition>
          <LinePosition
            key={"Support"}
            line={"Support"}
            isHeader={true}
          ></LinePosition>
        </Positions>
        <Tier>
          <span>티어 전체</span>
          <span>
            <FontAwesomeIcon icon={faCaretDown} />
          </span>
        </Tier>
      </UpperContents>
      <UpperContents>
        <UpLoadButton>등록하기</UpLoadButton>
      </UpperContents>
    </Upper>
  );
}

export default MainHeader;
