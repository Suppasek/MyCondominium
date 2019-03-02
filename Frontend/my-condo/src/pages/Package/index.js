import React from "react";
import AppBar from "../../components/AppBar";
import PackageForm from "./PackageForm";
import SnackBar from "../../components/SnackBar";
import { connect } from "react-redux";
import { switchStatus } from "../../actions";
import PackageManage from "./PackageManage";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  container: {
    display: "flex",
    flexDirection: "column"
  },
  content: {
    flexGrow: 1
  }
});
const Package = props => (
  <div className={props.container}>
    <div>
      <AppBar name="Package" />
    </div>
    <div className={props.content}>
      <PackageForm />
      <PackageManage />
      <SnackBar
        onSwitchStatus={props.onSwitchStatus}
        status={props.fetchResult.status}
        switch={props.packageSnackBar}
        message={props.fetchResult.status}
      />
    </div>
  </div>
);

const mapDispatchToProps = dispatch => ({
  onSwitchStatus(status) {
    dispatch(switchStatus("PACKAGE_SNACKBAR", status));
  }
});

const mapStateToProps = state => ({
  packageSnackBar: state.packageSnackBar,
  fetchResult: state.postPackageResult
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(Package));
