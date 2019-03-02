import React from "react";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import { switchPosition } from "../actions";
import { connect } from "react-redux";

const NavigationTab = props => (
  <Tabs
    value={props.tab}
    onChange={props.onSwitchTab}
    indicatorColor="secondary"
  >
    <Tab label="Juristic Person" />
    <Tab label="Water Meter Recorder" />
    <Tab label="Resident" />
  </Tabs>
);

const mapDispatchToProps = dispatch => ({
  onSwitchTab(event, value) {
    dispatch(switchPosition("TAB", value));
  }
});

const mapStateToProps = state => ({
  tab: state.tab
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NavigationTab);
