import React, { Component } from "react";
import Drawer from "@material-ui/core/Drawer";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import PersonIcon from "@material-ui/icons/AccountCircle";
import BoxIcon from "@material-ui/icons/MoveToInbox";
import { ListItemIcon } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import { NavLink } from "react-router-dom";
import { switchMenu } from "../actions";
import { connect } from "react-redux";

const styles = theme => ({
  drawer: {
    flexShrink: 0
  },
  paper: {
    width: "15%",
    position: "absolute",
    flexShrink: 0
  },
  toolbar: theme.mixins.toolbar,
  navLink: { textDecoration: "none" }
});

class SideMenu extends Component {
  render() {
    const classes = this.props.classes;

    return (
      <Drawer
        variant="permanent"
        open={true}
        className={classes.Drawer}
        classes={{ paper: classes.paper }}
        anchor="left"
      >
        {/*left some space */}
        <div className={classes.toolbar} />

        <Divider />
        <List component="nav">
          <NavLink exact to="/" className={classes.navLink}>
            <ListItem
              button
              selected={this.props.item === 0}
              onClick={event => this.props.onSwitchItem(event, 0)}
            >
              <ListItemIcon>
                <PersonIcon />
              </ListItemIcon>
              <ListItemText primary="Account" />
            </ListItem>
          </NavLink>
          <NavLink exact to="/package" className={classes.navLink}>
            <ListItem
              button
              selected={this.props.item === 1}
              onClick={event => this.props.onSwitchItem(event, 1)}
            >
              <ListItemIcon>
                <BoxIcon />
              </ListItemIcon>
              <ListItemText primary="Package" />
            </ListItem>
            <Divider />
          </NavLink>
        </List>
      </Drawer>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  onSwitchItem(event, index) {
    dispatch(switchMenu(index));
  }
});

const mapStateToProps = state => ({
  item: state.menus
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(SideMenu));
