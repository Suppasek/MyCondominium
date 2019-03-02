import React from "react";
import AppBar from "../../components/AppBar";
import { withStyles } from "@material-ui/core/styles";
import AnnounceForm from "./AnnounceForm";
import SnackBar from "../../components/SnackBar";
import { connect } from "react-redux";
import { switchStatus } from "../../actions";

const styles = theme => ({});

const Announce = props => (
  <div>
    <AppBar name="Announce" />
    <AnnounceForm />
    <SnackBar
      onSwitchStatus={props.onSwitchStatus}
      status={props.fetchResult.status}
      switch={props.announceSnackBar}
      message={props.fetchResult.status}
    />
  </div>
);

const mapDispatchToProps = dispatch => ({
  onSwitchStatus(status) {
    dispatch(switchStatus("ANNOUNCE_SNACKBAR", status));
  }
});

const mapStateToProps = state => ({
  announceSnackBar: state.announceSnackBar,
  fetchResult: state.postAnnounceResult
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(Announce));
