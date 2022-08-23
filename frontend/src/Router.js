import { Routes, Route } from "react-router-dom";
import LogIn from "routes/LogIn";
import Register from "routes/Register";
import IsLoogedIn from "routes/IsLoggedIn";
import LolHome from "routes/LolHome";
import Chat from "routes/Chat";
import Profile from "routes/Profile";

function Router({ isLoggedIn, setIsLoggedIn }) {
  return (
    <Routes>
      <Route path="/" element={<IsLoogedIn isLoggedIn={isLoggedIn} />} />
      <Route path="/login" element={<LogIn setIsLoggedIn={setIsLoggedIn} />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/lol/home"
        element={<LolHome setIsLoggedIn={setIsLoggedIn} />}
      />
      <Route path="/chat" element={<Chat />} />
      <Route path="/profile" element={<Profile />} />
    </Routes>
  );
}
export default Router;
