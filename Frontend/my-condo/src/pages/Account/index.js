import React from "react";
import AppBar from "../../components/AppBar";
import NavigationTab from "../../components/NavigationTab";
import Juristic from "./AccountTab/Juristic";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  toolbar: theme.mixins.toolbar
});

const Account = props => (
  <div>
    <AppBar name="Account">
      <NavigationTab />
    </AppBar>
    <Juristic />
  </div>
);

export default withStyles(styles)(Account);
