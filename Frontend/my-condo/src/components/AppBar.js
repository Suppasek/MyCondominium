import React, { Component } from "react";
import MdAppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";

const styles = {
  appBar: {
    width: "85%",
    marginLeft: 240
  }
};

class AppBar extends Component {
  render() {
    const classes = this.props.classes;
    return (
      <MdAppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" color="inherit" noWrap>
            {this.props.name}
          </Typography>
        </Toolbar>
        {this.props.children}
      </MdAppBar>
    );
  }
}

export default withStyles(styles)(AppBar);
