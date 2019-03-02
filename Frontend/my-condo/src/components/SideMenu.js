import React from "react";
import Drawer from "@material-ui/core/Drawer";
import List from "@material-ui/core/List";
import Divider from "@material-ui/core/Divider";
import PersonIcon from "@material-ui/icons/AccountCircle";
import BoxIcon from "@material-ui/icons/MoveToInbox";
import AnnounceIcon from "@material-ui/icons/Announcement";
import { withStyles } from "@material-ui/core/styles";
import { NavLink } from "react-router-dom";
import SideMenuItem from "./SideMenuItem";

const styles = theme => ({
  drawer: {
    flexShrink: 0
  },
  paper: {
    width: "15%",
    flexShrink: 0
  },
  toolbar: theme.mixins.toolbar,
  navLink: { textDecoration: "none" }
});

const SideMenu = props => (
  <Drawer
    variant="permanent"
    open={true}
    className={props.classes.Drawer}
    classes={{ paper: props.classes.paper }}
    anchor="left"
  >
    {/*left some space */}
    <div className={props.classes.toolbar} />

    <Divider />
    <List component="nav">
      <NavLink exact to="/" className={props.classes.navLink}>
        <SideMenuItem
          selected={props.selected}
          onSwitch={props.switchEvent}
          index={0}
          label="Account"
        >
          <PersonIcon />
        </SideMenuItem>
      </NavLink>
      <NavLink exact to="/package" className={props.classes.navLink}>
        <SideMenuItem
          selected={props.selected}
          onSwitch={props.switchEvent}
          index={1}
          label="Package"
        >
          <BoxIcon />
        </SideMenuItem>
      </NavLink>
      <NavLink exact to="/announce" className={props.classes.navLink}>
        <SideMenuItem
          selected={props.selected}
          onSwitch={props.switchEvent}
          index={2}
          label="Announce"
        >
          <AnnounceIcon />
        </SideMenuItem>
      </NavLink>
      <Divider />
    </List>
  </Drawer>
);

export default withStyles(styles)(SideMenu);
