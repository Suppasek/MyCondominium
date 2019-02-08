import React, { Component } from "react";
import AppBar from "../components/AppBar";
import NavigationTab from "../components/NavigationTab";

class Account extends Component {
  render() {
    return (
      <AppBar name="Account">
        <NavigationTab />
      </AppBar>
    );
  }
}

export default Account;
