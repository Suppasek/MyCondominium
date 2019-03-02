import React from "react";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import { ListItemIcon } from "@material-ui/core";

const SideMenuItem = props => (
  <ListItem
    button
    selected={props.selected === props.index}
    onClick={event => props.onSwitch(event, props.index)}
  >
    <ListItemIcon>{props.children}</ListItemIcon>
    <ListItemText primary={props.label} />
  </ListItem>
);

export default SideMenuItem;
