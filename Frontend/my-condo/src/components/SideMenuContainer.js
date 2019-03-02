import React from "react";
import { switchPosition } from "../actions";
import { connect } from "react-redux";
import SideMenu from "./SideMenu";

const SideMenuContainer = props => (
  <SideMenu selected={props.item} switchEvent={props.onSwitchItem} />
);

const mapDispatchToProps = dispatch => ({
  onSwitchItem: (event, index) => dispatch(switchPosition("MENU", index))
});

const mapStateToProps = state => ({
  item: state.menu
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SideMenuContainer);
