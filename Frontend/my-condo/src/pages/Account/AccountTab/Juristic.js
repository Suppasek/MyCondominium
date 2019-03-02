import React from "react";
import TextField from "@material-ui/core/TextField";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import FormPaper from "../../../components/FormPaper";

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

const Juristic = props => (
  <FormPaper formName="Add an Email/Password user">
    <div>
      <TextField id="email" label="Email" fullWidth={true} margin="normal" />
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
        className={props.classes.button}
      >
        Add
      </Button>
    </div>
  </FormPaper>
);

export default withStyles(styles)(Juristic);
