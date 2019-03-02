import React, { Component } from "react";
import "./App.css";
import { createMuiTheme } from "@material-ui/core/styles";
import blue from "@material-ui/core/colors/blue";
import green from "@material-ui/core/colors/green";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import Root from "./Root";

const theme = createMuiTheme({
  palette: {
    primary: blue,
    secondary: green
  }
});

class App extends Component {
  componentDidMount() {
    document.title = "MyCondo";
  }
  render() {
    return (
      <MuiThemeProvider theme={theme}>
        <Root />
      </MuiThemeProvider>
    );
  }
}

export default App;
