import React, { Component } from "react";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import axios from "axios";

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

const addPackage = event => {
  event.preventDefault();
  axios
    .post(
      "https://us-central1-waterusagechecker.cloudfunctions.net/condo/room/" +
        event.target.room.value +
        "/package",
      {
        name: event.target.receiver.value,
        packageNo: event.target.package.value
      }
    )
    .then(response => console.log(response))
    .catch(error => console.log(error));
};

class PackageForm extends Component {
  render() {
    const classes = this.props.classes;
    return (
      <form onSubmit={addPackage}>
        <Paper elevation={1} className={classes.paper}>
          <div>
            <Typography variant="subtitle1">Add a Package</Typography>
            <TextField
              name="receiver"
              label="Receiver Name"
              fullWidth={true}
              margin="normal"
            />
            <TextField
              name="room"
              label="House No."
              fullWidth={true}
              margin="normal"
            />
            <TextField
              name="package"
              label="Package No."
              fullWidth={true}
              margin="normal"
            />
            <Button
              variant="contained"
              color="primary"
              className={classes.button}
              type="submit"
            >
              Add
            </Button>
          </div>
        </Paper>
      </form>
    );
  }
}

export default withStyles(styles)(PackageForm);
