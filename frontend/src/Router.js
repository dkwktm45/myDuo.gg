import { Routes, Route, Navigate } from "react-router-dom";
import LogIn from "pages/LogIn";
import Register from "pages/Register";
import Home from "pages/Home";
import Chat from "pages/Chat";
import Profile from "pages/Profile";
import { useRecoilValue } from "recoil";
import { LoginState } from "atoms";

function Router() {
  const isLoggedIn = useRecoilValue(LoginState);
  return (
    <Routes>
      {!isLoggedIn ? (
        <Route path="/" element={<Navigate replace to="/login" />} />
      ) : (
        <Route path="/" element={<Home />} />
      )}
      <Route path="/login" element={<LogIn />} />
      <Route path="/register" element={<Register />} />
      <Route path="/chat" element={<Chat />} />
      <Route path="/profile" element={<Profile />} />
      <Route path="/*" element={<Navigate replace to="/" />} />
    </Routes>
  );
}
export default Router;
