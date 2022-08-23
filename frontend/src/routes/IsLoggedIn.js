import { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

function Home({ isLoggedIn }) {
  const location = useLocation();
  const navigate = useNavigate();
  useEffect(() => {
    if (location.pathname === "/" && isLoggedIn) {
      navigate("/lol/home");
    } else {
      navigate("/login");
    }
  }, [isLoggedIn, navigate, location]);

  return <div>loading ... </div>;
}
export default Home;
