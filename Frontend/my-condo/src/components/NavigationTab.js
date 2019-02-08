import React, { Component } from "react";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import { switchTab } from "../actions";
import { connect } from "react-redux";

class NavigationTab extends Component {
  render() {
    return (
      <Tabs
        value={this.props.tab}
        onChange={this.props.onSwitchTab}
        indicatorColor="secondary"
      >
        <Tab label="Juristic Person" />
        <Tab label="Water Meter Recorder" />
        <Tab label="Resident" />
      </Tabs>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  onSwitchTab(event, value) {
    dispatch(switchTab(value));
  }
});

const mapStateToProps = state => ({
  tab: state.tabs
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NavigationTab);
