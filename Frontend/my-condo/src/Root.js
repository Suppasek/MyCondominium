import React, { Component } from "react";
import SideMenu from "./components/SideMenu";
import CssBaseline from "@material-ui/core/CssBaseline";
import Account from "./pages/Account";
import { Route } from "react-router-dom";
import Package from "./pages/Package";

const accountPage = () => <Account />;
const packagePage = () => <Package />;

class Root extends Component {
  render() {
    return (
      <div className="App">
        <CssBaseline />
        <SideMenu />
        <Route exact path="/" component={accountPage} />
        <Route exact path="/package" component={packagePage} />
      </div>
    );
  }
}

export default Root;
