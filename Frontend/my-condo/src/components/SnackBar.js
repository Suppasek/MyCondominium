import React from "react";
import Snackbar from "@material-ui/core/Snackbar";
import { withStyles } from "@material-ui/core/styles";
import ErrorIcon from "@material-ui/icons/Error";
import CheckCircleIcon from "@material-ui/icons/CheckCircle";
import green from "@material-ui/core/colors/green";
import SnackbarContent from "@material-ui/core/SnackbarContent";

const styles = theme => ({
  success: {
    backgroundColor: green[600]
  },
  error: {
    backgroundColor: theme.palette.error.dark
  },
  message: {
    display: "flex",
    alignItems: "center"
  },
  iconVariant: {
    marginRight: theme.spacing.unit
  }
});

const SnackBar = props => (
  <Snackbar
    anchorOrigin={{ vertical: "bottom", horizontal: "left" }}
    open={props.switch}
    autoHideDuration={6000}
    onClose={() => props.onSwitchStatus(false)}
  >
    <SnackbarContent
      classes={
        props.status === "success"
          ? { root: props.classes.success }
          : { root: props.classes.error }
      }
      message={
        <span className={props.classes.message}>
          {props.status === "success" && (
            <CheckCircleIcon classes={{ root: props.classes.iconVariant }} />
          )}
          {props.status === "fail" && (
            <ErrorIcon classes={{ root: props.classes.iconVariant }} />
          )}
          {props.message}
        </span>
      }
    />
  </Snackbar>
);

export default withStyles(styles)(SnackBar);
