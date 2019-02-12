import Paper from "@material-ui/core/Paper";
import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";

const styles = theme => ({
  paper: {
    padding: theme.spacing.unit * 3,
    overflow: "auto"
  },
  button: {
    marginTop: 16,
    float: "right"
  }
});

class Juristic extends Component {
  render() {
    const classes = this.props.classes;
    return (
      <Paper elevation={1} className={classes.paper}>
        <div>
          <Typography variant="subtitle1">
            Add an Email/Password user
          </Typography>
          <TextField
            id="email"
            label="Email"
            fullWidth={true}
            margin="normal"
          />
          <TextField id="name" label="Name" fullWidth={true} margin="normal" />
          <TextField
            id="password"
            label="Password"
            fullWidth={true}
            margin="normal"
          />
          <Button
            variant="contained"
            color="primary"
            className={classes.button}
          >
            Add
          </Button>
        </div>
      </Paper>
    );
  }
}

export default withStyles(styles)(Juristic);
