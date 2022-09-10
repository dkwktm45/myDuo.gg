import axios from "axios";
const BASE_URL = "http://localhost:8080";

export const accountService = async (type, data) => {
  if (type !== "logout") {
    const response = await axios.post(BASE_URL + "/account/" + type, data, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response;
  } else {
    console.log(data);
    const response = await axios.get(BASE_URL + "/account/logout", {
      headers: {
        Authorization: data,
      },
    });
    return response;
  }
};

export const boardCreateOpenService = async (token) => {
  console.log("게시물생성", token);
  const response = await axios.get("http://127.0.0.1:8080/board/create", {
    headers: {
      Authorization: token,
    },
  });
  return response;
};

export const appendLolAccount = async () => {
  //url : /lol/add
  // header : authorization
  // body : summonerName
  return;
};

export const removeLolAccount = async () => {
  // url : /lol/remove
  // header : authorization
  // body : encryptedPUUID
  return;
};
