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

export const logoutService = async (data) => {
  const response = await axios.get(BASE_URL + "/account/logout", {
    headers: {
      Authorization: data,
    },
  });
  return response;
};

export const expiredToken = async () => {
  window.localStorage.removeItem("myNick");
  return;
};

export function loadBoards(params) {
  var res = axios
    .get("http://127.0.0.1:8080/board/list", {
      headers: {
        Authorization: params.queryKey[1].token,
        "Access-Control-Allow-Origin": "http://localhost:3000",
      },
      params: {
        page: 0,
        registrationTime: "4124440596000000",
        size: null,
        sort: "registrationTime,desc",
      },
    })
    .then((response) => response.data.content);
  return res;
}
