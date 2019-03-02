import React from "react";
import MdAppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";

const styles = theme => ({
  appBar: {
    width: "85%"
  }
});

const AppBar = props => (
  <MdAppBar position="fixed" className={props.classes.appBar}>
    <Toolbar className={props.appBarSpacer}>
      <Typography variant="h6" color="inherit" noWrap>
        {props.name}
      </Typography>
    </Toolbar>
    {props.children}
  </MdAppBar>
);

export default withStyles(styles)(AppBar);
