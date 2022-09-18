import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleUser,
  faXmark,
  faMicrophone,
  faMicrophoneSlash,
  faHeart,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";
import { useState } from "react";
import axios from "axios";
import { LoginState } from "atoms";
import { useRecoilValue } from "recoil";

const Overlay = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 5;
`;

const PostDetail = styled.div`
  font-family: "Roboto", sans-serif;
  position: fixed;
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
  overflow: hidden;
  border: 2px solid ${(props) => props.theme.lolTextColor};
  background-color: ${(props) => props.theme.lolBgColorNormal};
  z-index: 9;
  margin-top: 100px;
`;

const PostDetailHeader = styled.div`
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 8px;
  box-sizing: border-box;
  border-bottom: 1px solid ${(props) => props.theme.lolBgColor};
  div {
    display: flex;
    align-items: center;
    span {
      margin: 15px;
      color: ${(props) => props.theme.lolTextColor};
      &:first-child {
        font-size: 30px;
      }
    }
  }

  > span {
    margin: 20px;
    color: ${(props) => props.theme.lolTextColor};
    font-size: 20px;
    cursor: pointer;
  }
`;

const PostDetailUserInfo = styled.div`
  width: 95%;
  height: 90px;
  display: grid;
  grid-template-columns: 1fr 2fr 2fr 1fr 1fr;
  color: ${(props) => props.theme.lolTextColor};
  div {
    &:nth-child(1) {
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 50px;
        height: 50px;
        padding: 3px;
        background-color: ${(props) => props.theme.lolBgColorLight};
        border-radius: 50%;
      }
    }
    &:nth-child(2) {
      display: flex;
      flex-direction: column;
      justify-content: center;
      margin-left: 5px;
      label {
        font-size: 18px;
        font-weight: 600;
        margin-top: 12px;
        margin-bottom: 10px;
      }
      div {
        display: flex;
        justify-content: left;
        flex-direction: row;
        label {
          margin: 0;
          font-size: 14px;
          font-weight: 600;
        }
        label:nth-child(2) {
          margin-left: 10px;
          font-weight: 400;
          font-size: 12px;
        }
      }
    }
    &:nth-child(3) {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin-top: 5px;
      font-size: 14px;
      label:first-child {
        font-weight: 600;
        font-size: 16px;
        margin-bottom: 8px;
      }
    }
    &:nth-child(4),
    &:nth-child(5) {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      font-size: 20px;
      margin-top: 5px;
      span:nth-child(2) {
        font-size: 14px;
        margin-top: 8px;
      }
    }
  }
`;

const PostDetailLineInfo = styled.div`
  display: grid;
  align-items: center;
  width: 100%;
  height: 180px;
  padding: 0 5px;
  color: ${(props) => props.theme.lolTextColor};
  grid-template-columns: 2fr 7fr;
  grid-template-rows: 30px 1fr;
  div {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
  }
  & > div:nth-child(1),
  & > div:nth-child(2) {
    font-weight: 600;
    font-size: 15px;
  }
  & > div:nth-child(3) {
    /*
    * 자주 가는 라인 2종류
     */
    flex-direction: column;
    div {
      width: 70px;
      height: 70px;
      border: 1px solid ${(props) => props.theme.lolTextColor};
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-around;
      border-radius: 5px;
      img {
        width: 30px;
      }
      label {
        font-size: 14px;
      }
    }
    div:first-child {
      margin-bottom: 5px;
    }
  }
  & > div:nth-child(4) {
    /*
    * 챔피언별 승률 정보
     */
    position: relative;
    width: 95%;
    height: 145px;
    border: 1px solid ${(props) => props.theme.lolTextColor};
    background-color: ${(props) => props.theme.lolBgColorLight};
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    justify-content: left;
    overflow: auto;
    /*
    * 스크롤바 디자인
    */
    &::-webkit-scrollbar {
      width: 10px;
    }
    &::-webkit-scrollbar-thumb {
      background-color: ${(props) => props.theme.lolTextColor};
      border-radius: 5px;
    }
    &::-webkit-scrollbar-track {
      background-color: ${(props) => props.theme.lolBgColorLight};
      border-radius: 5px;
      box-shadow: inset 0px 0px 5px ${(props) => props.theme.lolBgColorNormal};
    }

    & > label {
      position: relative;
      width: 100%;
      background-color: ${(props) => props.theme.lolBgColorNormal};
      margin: 3px 0;
      display: grid;
      grid-template-columns: 1fr 3fr 3fr 3fr;
      div {
        display: flex;
        flex-direction: column;
        & > span {
          font-size: 14px;
          font-weight: 300;
          &:first-child {
            font-size: 16px;
            font-weight: 600;
            margin: 5px 0;
          }
        }
      }
      & > div:first-child {
        img {
          width: 50px;
          height: 50px;
          border-radius: 50%;
          margin: 5px 10px;
        }
      }
    }
  }
`;

const PostDetailLatestGame = styled.div`
  display: grid;
  grid-template-rows: 1fr 1fr;
  width: 100%;
  height: 80px;
  color: ${(props) => props.theme.lolTextColor};
  margin-top: 10px;
  div {
    display: flex;
    align-items: center;
    font-size: 16px;
    &:first-child {
      margin-left: 12px;
      font-weight: 600;
    }
    &:nth-child(2) {
      justify-content: center;
      span {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 40px;
        height: 40px;
        border: 1px solid ${(props) => props.theme.lolTextColor};
        background-color: ${(props) => props.theme.lolBgColorLight};
        color: ${(props) => props.theme.lolAccentColor};
        &.lose {
          color: ${(props) => props.theme.lolAccentColor1};
        }
      }
    }
  }
`;

const PostDetailMemo = styled.div`
  display: grid;
  grid-template-rows: 1fr 3fr;
  width: 100%;
  height: 100px;
  color: ${(props) => props.theme.lolTextColor};
  font-size: 16px;
  margin-top: 15px;
  div {
    width: 100%;
    &:first-child {
      font-weight: 600;
      margin-left: 12px;
    }

    &:nth-child(2) {
      display: flex;
      justify-content: center;
      p {
        border: 1px solid ${(props) => props.theme.lolTextColor};
        background-color: ${(props) => props.theme.lolBgColorLight};
        width: 90%;
        height: 75px;
        max-width: calc(100%);
        box-sizing: border-box;
        padding: 10px;
        line-height: 18px;
        word-break: break-all;
        overflow: auto;
        &::-webkit-scrollbar {
          width: 10px;
        }
        &::-webkit-scrollbar-thumb {
          background-color: ${(props) => props.theme.lolTextColor};
          border-radius: 5px;
        }
        &::-webkit-scrollbar-track {
          background-color: ${(props) => props.theme.lolBgColorLight};
          border-radius: 5px;
          box-shadow: inset 0px 0px 5px
            ${(props) => props.theme.lolBgColorNormal};
        }
      }
    }
  }
`;

const PostDetailButtons = styled.div`
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  width: 100%;
  height: 50px;
  margin-top: 15px;
  button {
    background-color: yellow;
    font-size: 16px;
    font-weight: 600;
    width: 110px;
    height: 35px;
    margin-right: 20px;
    border: none;
    background-color: ${(props) => props.theme.lolTextColor};
    cursor: pointer;
  }
`;

function DetailBoard({ ...props }) {
  const lane = ["TOP", "JUNGLE", "MID", "BOT", "SUPPORT"];
  const championNameToKorean = {
    Garen: "가렌",
    Galio: "갈리오",
    Gangplank: "갱플랭크",
    Gragas: "그라가스",
    Graves: "그레이브즈",
    Gnar: "나르",
    Nami: "나미",
    Nasus: "나서스",
    Nautilus: "노틸러스",
    Nocturne: "녹턴",
    Nunu: "누누",
    Nidalee: "니달리",
    Neeko: "니코",
    Darius: "다리우스",
    Diana: "다이애나",
    Draven: "드레이븐",
    Ryze: "라이즈",
    Rakan: "라칸",
    Rammus: "람머스",
    Lux: "럭스",
    Rumble: "럼블",
    Renekton: "레넥톤",
    Leona: "레오나",
    "Rek'Sai": "렉사이",
    Rengar: "렝가",
    Lucian: "루시안",
    Lulu: "룰루",
    LeBlanc: "르블랑",
    "Lee Sin": "리신",
    Riven: "리븐",
    Lissandra: "리산드라",
    "Master Yi": "마스터 이",
    Maokai: "마오카이",
    Malzahar: "말자하",
    Malphite: "말파이트",
    Mordekaiser: "모데카이저",
    Morgana: "모르가나",
    "Dr. Mundo": "문도박사",
    "Miss Fortune": "미스포츈",
    Bard: "바드",
    Varus: "바루스",
    Vi: "바이",
    Veigar: "베이가",
    Vayne: "베인",
    VelKoz: "벨코즈",
    Voilbear: "볼리베어",
    Braum: "브라움",
    Brand: "브랜드",
    Vladimir: "블라디미르",
    Blitzcrank: "블리츠크랭크",
    Viktor: "빅토르",
    Poppy: "뽀삐",
    Sion: "사이온",
    Sylas: "사일러스",
    Shaco: "샤코",
    Senna: "세나",
    Sejuani: "세주아니",
    Sett: "세트",
    Sona: "소나",
    Soraka: "소라카",
    Shen: "쉔",
    Shyvana: "쉬바나",
    Swain: "스웨인",
    Skarner: "스카너",
    Sivir: "시비르",
    "Xin Zhao": "신짜오",
    Syndra: "신드라",
    Singed: "신지드",
    Thresh: "쓰레쉬",
    Ahri: "아리",
    Amumu: "아무무",
    "Aurelion Sol": "아우렐리온 솔",
    Ivern: "아이번",
    Azir: "아지르",
    Akali: "아칼리",
    Aatrox: "아트록스",
    Aphelios: "아펠리오스",
    Alistar: "알리스타",
    Annie: "애니",
    Anivia: "애니비아",
    Ashe: "애쉬",
    Yasuo: "야스오",
    Ekko: "에코",
    Elise: "엘리스",
    Wukong: "오공",
    Ornn: "오른",
    Orianna: "오리아나",
    Olaf: "올라프",
    Yorick: "요릭",
    Udyr: "우디르",
    Urgot: "우르곳",
    Warwick: "워윅",
    Yummi: "유미",
    Irelia: "이렐리아",
    Evelynn: "이블린",
    Ezreal: "이즈리얼",
    Illaoi: "일라오이",
    JarvanIV: "자르반 4세",
    Xayah: "자야",
    Zyra: "자이라",
    Zac: "자크",
    Janna: "잔나",
    Jax: "잭스",
    Zed: "제드",
    Xerath: "제라스",
    Jayce: "제이스",
    Zoe: "조이",
    Ziggs: "직스",
    Jhin: "진",
    Zilean: "질리언",
    Jinx: "징크스",
    "Cho'Gath": "초가스",
    Karma: "카르마",
    Camile: "카밀",
    Kassadin: "카사딘",
    Karthus: "카서스",
    Cassiopeia: "카시오페아",
    Kaisa: "카이사",
    Khazix: "카직스",
    Katarina: "카타리나",
    Kalista: "칼리스타",
    Kennen: "케넨",
    Caitlyn: "케이틀린",
    Kayn: "케인",
    Kayle: "케일",
    "Kog'Maw": "코그모",
    Corki: "코르키",
    Quinn: "퀸",
    Kled: "클레드",
    Qiyana: "키아나",
    Kindred: "킨드레드",
    Taric: "타릭",
    Talon: "탈론",
    Taliyah: "탈리야",
    "Tahm Kench": "탐켄치",
    Trundle: "트런들",
    Tristana: "트리스타나",
    Tryndamere: "트린다미어",
    "Twisted Fate": "트위스티드 페이트",
    Twitch: "트위치",
    Teemo: "티모",
    Pyke: "파이크",
    Pantheon: "판테온",
    FiddleSticks: "피들스틱",
    Fiora: "피오라",
    Fizz: "피즈",
    Heimerdinger: "하이머딩거",
    Hecarim: "헤카림",
  };
  const account = useRecoilValue(LoginState);
  const [boardUserData, setBoardUserData] = useState();
  const [loading, setLoading] = useState(true);
  const [isMine, setIsMine] = useState(false);
  const [, setRefresh] = useState(0);
  const overlayClose = () => {
    setLoading(true);
    props.setPopupBoard("");
  };
  useEffect(() => {
    axios
      .get("http://127.0.0.1:8080/board/detail/" + props.data.boardUuid, {
        headers: {
          Authorization: account.token,
        },
      })
      .then(function (response) {
        setBoardUserData(response.data);
        setLoading(false);
        setRefresh(1);
      });
  }, [account, props.data.boardUuid]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/board/create", {
        headers: {
          Authorization: account.token,
        },
      })
      .then(function (response) {
        setIsMine(response.data.map((v) => v.name).includes(props.popupUser));
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [account, props.popupUser]);

  const refreshBoard = () => {
    setRefresh(0);
    console.log("전적 갱신", props.data);
  };

  const participate = () => {
    axios
      .post("http://127.0.0.1:8080/participants/room", null, {
        headers: {
          Authorization: account.token,
          "Access-Control-Allow-Origin": "http://localhost:3000",
        },
        params: {
          boardUuid: props.data.boardUuid,
        },
      })
      .then((response) => {
        console.log(response);
      });
  };

  return (
    <>
      <Overlay onClick={overlayClose}></Overlay>
      <PostDetail>
        {loading ? (
          "loading"
        ) : (
          <>
            <PostDetailHeader>
              <div>
                <span>
                  <FontAwesomeIcon icon={faCircleUser} />
                </span>
                <span>{boardUserData.loLAccountInfoDto.name}</span>
              </div>
              <span onClick={overlayClose}>
                <FontAwesomeIcon icon={faXmark} />
              </span>
            </PostDetailHeader>
            <PostDetailUserInfo>
              <div>
                <img
                  src={`../img/emblems/Emblem_${boardUserData.loLAccountInfoDto.tier}.png`}
                  alt="lolLogo"
                />
              </div>
              <div>
                <label>{boardUserData.loLAccountInfoDto.name}</label>
                <div>
                  <label>{`${boardUserData.loLAccountInfoDto.tier} ${boardUserData.loLAccountInfoDto.rank}`}</label>
                  <label>{`${boardUserData.loLAccountInfoDto.leaguePoints}LP`}</label>
                </div>
              </div>
              <div>
                <label>{`${boardUserData.loLAccountInfoDto.wins}승 ${boardUserData.loLAccountInfoDto.losses}패`}</label>
                <label>{`승률 ${boardUserData.loLAccountInfoDto.winningRate.toFixed(
                  1
                )}%`}</label>
              </div>
              <div>
                <span>
                  {boardUserData.micEnabled ? (
                    <FontAwesomeIcon icon={faMicrophone} />
                  ) : (
                    <FontAwesomeIcon icon={faMicrophoneSlash} />
                  )}
                </span>
                <span>마이크</span>
              </div>
              <div>
                <span>
                  <FontAwesomeIcon icon={faHeart} />
                </span>
                <span>{boardUserData.userHeart}</span>
              </div>
            </PostDetailUserInfo>
            <PostDetailLineInfo>
              <div>자주 가는 라인</div>
              <div>챔피언 별 승률</div>
              <div>
                <div>
                  <img
                    src={`../img/positions/Position_Gold-${
                      lane[boardUserData.loLAccountInfoDto.laneInfo[0]]
                    }.png`}
                    alt=""
                  />
                  <label>
                    {lane[boardUserData.loLAccountInfoDto.laneInfo[0]]}
                  </label>
                </div>
                <div>
                  <img
                    src={`../img/positions/Position_Gold-${
                      lane[boardUserData.loLAccountInfoDto.laneInfo[1]]
                    }.png`}
                    alt=""
                  />
                  <label>
                    {lane[boardUserData.loLAccountInfoDto.laneInfo[1]]}
                  </label>
                </div>
              </div>
              <div>
                {boardUserData.loLAccountInfoDto.championInfo.map((champ) => {
                  return (
                    <label key={champ.championName}>
                      <div>
                        <img
                          src={`../img/champions/${champ.championName}_0.jpg`}
                          alt=""
                        />
                      </div>
                      <div>
                        <span>
                          {championNameToKorean[champ.championName] !==
                          undefined
                            ? championNameToKorean[champ.championName]
                            : champ.championName}
                        </span>
                        <span>{`CS ${champ.csPerGame.toFixed(1)}`}</span>
                      </div>
                      <div>
                        <span>{`${champ.grade.toFixed(2)} 평점`}</span>
                        <span>
                          {`${champ.kills.toFixed(1)} / ${champ.deaths.toFixed(
                            1
                          )} / ${champ.assists.toFixed(1)}`}
                        </span>
                      </div>
                      <div>
                        <span>{`${champ.winningRate.toFixed(1)}%`}</span>
                        <span>{champ.totalPlayedCount}게임</span>
                      </div>
                    </label>
                  );
                })}
              </div>
            </PostDetailLineInfo>
            <PostDetailLatestGame>
              <div>최근 경기 결과</div>
              <div>
                <div>
                  {boardUserData.loLAccountInfoDto.resultRecentGameDto.result
                    .split("")
                    .map((result, i) => (
                      <span key={i} className={result === "L" ? "lose" : ""}>
                        {result}
                      </span>
                    ))}
                </div>
              </div>
            </PostDetailLatestGame>
            <PostDetailMemo>
              <div>메모</div>
              <div>
                <p>{boardUserData.boardContent}</p>
              </div>
            </PostDetailMemo>
            <PostDetailButtons>
              {isMine ? (
                <button disabled>참가 신청</button>
              ) : (
                <button onClick={participate}>참가 신청</button>
              )}

              <button onClick={refreshBoard}>전적 갱신하기</button>
            </PostDetailButtons>
          </>
        )}
      </PostDetail>
    </>
  );
}

export default DetailBoard;
