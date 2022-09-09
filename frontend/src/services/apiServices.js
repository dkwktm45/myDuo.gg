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
