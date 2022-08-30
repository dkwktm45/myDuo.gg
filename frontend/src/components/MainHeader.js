import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { useRecoilState } from "recoil";
import { LineFilterState, TierFilterState } from "atoms";

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
  width: 120px;
  height: 40px;
  font-size: 16px;
  line-height: 40px;
  text-align: center;
  background-color: ${(props) => props.theme.lolTextColor};
  color: ${(props) => props.theme.lolBgColor};
  margin: 0 20px;
`;

const Select = styled.select`
  background-color: ${(props) => props.theme.lolTextColor};
  width: 100%;
  height: 100%;
  font-size: 16px;
  font-weight: 600;
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

const Item = styled.label`
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  font-size: 20px;
  color: ${(props) => props.theme.lolTextColor};
  img,
  span {
    cursor: pointer;
    position: absolute;
    width: 80%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
`;

const RadioButtonLabel = styled.label`
  font-size: 20px;
  width: 40px;
  height: 40px;
  background-color: blue;
  cursor: pointer;
  color: ${(props) => props.theme.lolTextColor};
  border: 2px solid ${(props) => props.theme.lolBgColorLight};
  background-color: ${(props) => props.theme.lolBgColorNormal};
`;

const RadioButton = styled.input`
  cursor: pointer;
  display: none;
  transition: all 0.2s ease-in-out;
  &:checked + ${RadioButtonLabel}, &:hover + ${RadioButtonLabel} {
    background-color: ${(props) => props.theme.lolBgColorLight};
  }
  &:hover {
    span {
      display: block;
    }
  }
`;

function MainHeader() {
  const lineTypes = ["All", "Top", "Jungle", "Mid", "Bot", "Support"];
  const [lineFilter, setLineFilter] = useRecoilState(LineFilterState);

  const [tierFilter, setTierFilter] = useRecoilState(TierFilterState);

  const handleLineFilter = (e) => {
    setLineFilter(e.target.value);
  };

  const handleTierFilter = (e) => {
    setTierFilter(e.target.value);
  };

  return (
    <Upper>
      <UpperContents>
        <Positions>
          {lineTypes.map((v, i) => (
            <Item key={i}>
              <RadioButton
                type="radio"
                name="selectedLine"
                id={v}
                value={v}
                line={v}
                isHeader={true}
                onChange={handleLineFilter}
                checked={lineFilter === v}
              />
              <RadioButtonLabel htmlFor={v} />
              {i === 0 ? (
                <span>
                  <FontAwesomeIcon icon={faStarOfLife} />
                </span>
              ) : (
                <img src={`../img/positions/Position_Gold-${v}.png`} alt="" />
              )}
            </Item>
          ))}
        </Positions>
        <Tier>
          <Select onChange={handleTierFilter}>
            <option value="All">전체</option>
            <option value="Iron">아이언</option>
            <option value="Bronze">브론즈</option>
            <option value="Silver">실버</option>
            <option value="Gold">골드</option>
            <option value="Platinum">플래티넘</option>
            <option value="Diamond">다이아몬드</option>
            <option value="Master">마스터</option>
            <option value="GrandMaster">그랜드 마스터</option>
            <option value="Challenger">챌린저</option>
          </Select>
        </Tier>
      </UpperContents>
      <UpperContents>
        <UpLoadButton>등록하기</UpLoadButton>
      </UpperContents>
    </Upper>
  );
}

export default MainHeader;
