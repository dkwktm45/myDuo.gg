import React, { Component } from "react";
import { Helmet } from "react-helmet-async";

class HelmetComponent extends Component {
  render() {
    return (
      <Helmet>
        <title>MyDuo</title>
        <link rel="icon" href="../img/logo/favicon.ico" />
      </Helmet>
    );
  }
}

export default HelmetComponent; //별도 컴포넌트로 생성
