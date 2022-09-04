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

function MainHeader({ setPopupCreate }) {
  const lineTypes = ["ALL", "TOP", "JUNGLE", "MID", "BOT", "SUPPORT"];
  const [lineFilter, setLineFilter] = useRecoilState(LineFilterState);

  const setTierFilter = useRecoilState(TierFilterState)[1];

  const handleLineFilter = (e) => {
    setLineFilter(e.target.value);
  };

  const handleTierFilter = (e) => {
    setTierFilter(e.target.value);
  };

  const openCreateBoard = () => {
    setPopupCreate(true);
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
            <option value="ALL">전체</option>
            <option value="IRON">아이언</option>
            <option value="BRONZE">브론즈</option>
            <option value="SILVER">실버</option>
            <option value="GOLD">골드</option>
            <option value="PLATINUM">플래티넘</option>
            <option value="DIAMOND">다이아몬드</option>
            <option value="MASTER">마스터</option>
            <option value="GRANDMASTER">그랜드 마스터</option>
            <option value="CHALLENGER">챌린저</option>
          </Select>
        </Tier>
      </UpperContents>
      <UpperContents>
        <UpLoadButton onClick={openCreateBoard}>등록하기</UpLoadButton>
      </UpperContents>
    </Upper>
  );
}

export default MainHeader;
