import { ThemeProvider } from "styled-components";
import GlobalStyle from "styles/GlobalStyle";
import Router from "Router";
import theme from "styles/theme";
import "styles/App.css";
import { useState } from "react";
import { BrowserRouter } from "react-router-dom";
import { HelmetProvider } from "react-helmet-async";
import HelmetComponent from "components/HelmetComponent";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  return (
    <>
      <HelmetProvider>
        <ThemeProvider theme={theme}>
          <HelmetComponent />
          <GlobalStyle />
          <BrowserRouter basename={process.env.PUBLIC_URL}>
            <Router isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
          </BrowserRouter>
        </ThemeProvider>
      </HelmetProvider>
    </>
  );
}

export default App;
