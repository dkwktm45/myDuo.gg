import { ThemeProvider } from "styled-components";
import GlobalStyle from "styles/GlobalStyle";
import Router from "Router";
import theme from "styles/theme";
import "styles/App.css";
import { BrowserRouter } from "react-router-dom";
import { HelmetProvider } from "react-helmet-async";
import HelmetComponent from "components/HelmetComponent";

function App() {
  return (
    <>
      <HelmetProvider>
        <ThemeProvider theme={theme}>
          <HelmetComponent />
          <GlobalStyle />
          <BrowserRouter basename={process.env.PUBLIC_URL}>
            <Router />
          </BrowserRouter>
        </ThemeProvider>
      </HelmetProvider>
    </>
  );
}

export default App;
