import React from "react";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  paper: {
    padding: theme.spacing.unit * 3,
    overflow: "auto",
    display: "flex",
    flexDirection: "column"
  }
});

const FormPaper = props => (
  <Paper elevation={1} className={props.classes.paper}>
    <Typography variant="subtitle1" align="left" className={props.typography}>
      {props.formName}
    </Typography>
    <form onSubmit={props.onSubmit}>{props.children}</form>
  </Paper>
);

export default withStyles(styles)(FormPaper);
