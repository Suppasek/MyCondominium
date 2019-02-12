import React, { Component } from "react";
import AppBar from "../components/AppBar";
import NavigationTab from "../components/NavigationTab";
import Juristic from "./AccountTab/Juristic";
import { withStyles } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";

const styles = theme => ({
  paper: {
    flexGrow: 1
  },
  toolbar: theme.mixins.toolbar
});

class Account extends Component {
  render() {
    const classes = this.props.classes;
    return (
      <div>
        <CssBaseline />
        <AppBar name="Account">
          <NavigationTab />
        </AppBar>
        <div className={classes.paper}>
          <Juristic />
        </div>
      </div>
    );
  }
}

export default withStyles(styles)(Account);
