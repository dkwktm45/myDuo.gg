import axios from "axios";
const BASE_URL = "http://localhost:8080";

export const accountService = async (type, data) => {
  const response = await axios.post(BASE_URL + "/account/" + type, data, {
    headers: {
      "Content-Type": "application/json",
    },
  });
  return response;
};
