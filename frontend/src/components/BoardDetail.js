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
    * ?????? ?????? ?????? 2??????
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
    * ???????????? ?????? ??????
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
    * ???????????? ?????????
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
    Garen: "??????",
    Galio: "?????????",
    Gangplank: "????????????",
    Gragas: "????????????",
    Graves: "???????????????",
    Gnar: "??????",
    Nami: "??????",
    Nasus: "?????????",
    Nautilus: "????????????",
    Nocturne: "??????",
    Nunu: "??????",
    Nidalee: "?????????",
    Neeko: "??????",
    Darius: "????????????",
    Diana: "????????????",
    Draven: "????????????",
    Ryze: "?????????",
    Rakan: "??????",
    Rammus: "?????????",
    Lux: "??????",
    Rumble: "??????",
    Renekton: "?????????",
    Leona: "?????????",
    "Rek'Sai": "?????????",
    Rengar: "??????",
    Lucian: "?????????",
    Lulu: "??????",
    LeBlanc: "?????????",
    "Lee Sin": "??????",
    Riven: "??????",
    Lissandra: "????????????",
    "Master Yi": "????????? ???",
    Maokai: "????????????",
    Malzahar: "?????????",
    Malphite: "????????????",
    Mordekaiser: "???????????????",
    Morgana: "????????????",
    "Dr. Mundo": "????????????",
    "Miss Fortune": "????????????",
    Bard: "??????",
    Varus: "?????????",
    Vi: "??????",
    Veigar: "?????????",
    Vayne: "??????",
    VelKoz: "?????????",
    Voilbear: "????????????",
    Braum: "?????????",
    Brand: "?????????",
    Vladimir: "???????????????",
    Blitzcrank: "??????????????????",
    Viktor: "?????????",
    Poppy: "??????",
    Sion: "?????????",
    Sylas: "????????????",
    Shaco: "??????",
    Senna: "??????",
    Sejuani: "????????????",
    Sett: "??????",
    Sona: "??????",
    Soraka: "?????????",
    Shen: "???",
    Shyvana: "?????????",
    Swain: "?????????",
    Skarner: "?????????",
    Sivir: "?????????",
    "Xin Zhao": "?????????",
    Syndra: "?????????",
    Singed: "?????????",
    Thresh: "?????????",
    Ahri: "??????",
    Amumu: "?????????",
    "Aurelion Sol": "??????????????? ???",
    Ivern: "?????????",
    Azir: "?????????",
    Akali: "?????????",
    Aatrox: "????????????",
    Aphelios: "???????????????",
    Alistar: "????????????",
    Annie: "??????",
    Anivia: "????????????",
    Ashe: "??????",
    Yasuo: "?????????",
    Ekko: "??????",
    Elise: "?????????",
    Wukong: "??????",
    Ornn: "??????",
    Orianna: "????????????",
    Olaf: "?????????",
    Yorick: "??????",
    Udyr: "?????????",
    Urgot: "?????????",
    Warwick: "??????",
    Yummi: "??????",
    Irelia: "????????????",
    Evelynn: "?????????",
    Ezreal: "????????????",
    Illaoi: "????????????",
    JarvanIV: "????????? 4???",
    Xayah: "??????",
    Zyra: "?????????",
    Zac: "??????",
    Janna: "??????",
    Jax: "??????",
    Zed: "??????",
    Xerath: "?????????",
    Jayce: "?????????",
    Zoe: "??????",
    Ziggs: "??????",
    Jhin: "???",
    Zilean: "?????????",
    Jinx: "?????????",
    "Cho'Gath": "?????????",
    Karma: "?????????",
    Camile: "??????",
    Kassadin: "?????????",
    Karthus: "?????????",
    Cassiopeia: "???????????????",
    Kaisa: "?????????",
    Khazix: "?????????",
    Katarina: "????????????",
    Kalista: "????????????",
    Kennen: "??????",
    Caitlyn: "????????????",
    Kayn: "??????",
    Kayle: "??????",
    "Kog'Maw": "?????????",
    Corki: "?????????",
    Quinn: "???",
    Kled: "?????????",
    Qiyana: "?????????",
    Kindred: "????????????",
    Taric: "??????",
    Talon: "??????",
    Taliyah: "?????????",
    "Tahm Kench": "?????????",
    Trundle: "?????????",
    Tristana: "???????????????",
    Tryndamere: "???????????????",
    "Twisted Fate": "??????????????? ?????????",
    Twitch: "?????????",
    Teemo: "??????",
    Pyke: "?????????",
    Pantheon: "?????????",
    FiddleSticks: "????????????",
    Fiora: "?????????",
    Fizz: "??????",
    Heimerdinger: "???????????????",
    Hecarim: "?????????",
  };
  const account = useRecoilValue(LoginState);
  const [boardUserData, setBoardUserData] = useState();
  const [loading, setLoading] = useState(true);
  const [isMine, setIsMine] = useState(false);
  const [, setRefresh] = useState(0);

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
        if (window.localStorage.getItem("myNick") === response.data.userName) {
          setIsMine(true);
        }
      });
  }, [account, props.data.boardUuid]);

  const participate = () => {
    axios
      .post("http://127.0.0.1:8080/participants/room", null, {
        headers: {
          Authorization: account.token,
        },
        params: {
          boardUuid: props.data.boardUuid,
        },
      })
      .then((response) => {
        console.log("participate", response);
      });
  };

  const refreshBoard = () => {
    setRefresh(0);
    console.log("?????? ??????", props.data);
  };

  const overlayClose = () => {
    setLoading(true);
    props.setPopupBoard("");
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
                <span>{boardUserData.userName}</span>
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
                <label>{`${boardUserData.loLAccountInfoDto.wins}??? ${boardUserData.loLAccountInfoDto.losses}???`}</label>
                <label>{`?????? ${boardUserData.loLAccountInfoDto.winningRate.toFixed(
                  1
                )}%`}</label>
              </div>
              <div>
                <span>
                  {boardUserData.micEnabled ? (
                    <FontAwesomeIcon icon={faMicrophone} />
                  ) : (
                    <FontAwesomeIcon icon={faMicrophoneSlash} color="#d82e34" />
                  )}
                </span>
                <span>?????????</span>
              </div>
              <div>
                <span>
                  <FontAwesomeIcon icon={faHeart} />
                </span>
                <span>{boardUserData.userHeart}</span>
              </div>
            </PostDetailUserInfo>
            <PostDetailLineInfo>
              <div>?????? ?????? ??????</div>
              <div>????????? ??? ??????</div>
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
                        <span>{`${champ.grade.toFixed(2)} ??????`}</span>
                        <span>
                          {`${champ.kills.toFixed(1)} / ${champ.deaths.toFixed(
                            1
                          )} / ${champ.assists.toFixed(1)}`}
                        </span>
                      </div>
                      <div>
                        <span>{`${champ.winningRate.toFixed(1)}%`}</span>
                        <span>{champ.totalPlayedCount}??????</span>
                      </div>
                    </label>
                  );
                })}
              </div>
            </PostDetailLineInfo>
            <PostDetailLatestGame>
              <div>?????? ?????? ??????</div>
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
              <div>??????</div>
              <div>
                <p>{boardUserData.boardContent}</p>
              </div>
            </PostDetailMemo>
            <PostDetailButtons>
              {isMine ? (
                <button disabled>?????? ??????</button>
              ) : (
                <button onClick={participate}>?????? ??????</button>
              )}

              <button onClick={refreshBoard}>?????? ????????????</button>
            </PostDetailButtons>
          </>
        )}
      </PostDetail>
    </>
  );
}

export default DetailBoard;
