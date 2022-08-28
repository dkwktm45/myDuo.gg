export const loadPosts = async () => {
  const BASE_URL = "http://localhost:8000";

  const response = await fetch(`${BASE_URL}/posts`);

  return response.json();
  //throw new Error("서버와 통신이 원활하지 않습니다.");
};
