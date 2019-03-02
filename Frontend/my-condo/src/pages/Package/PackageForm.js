import React from "react";
import TextField from "@material-ui/core/TextField";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import { connect } from "react-redux";
import { postPackage } from "../../actions";
import CircularProgress from "@material-ui/core/CircularProgress";
import FormPaper from "../../components/FormPaper";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import { getPackage, switchStatus, switchPosition } from "../../actions";
import withMobileDialog from "@material-ui/core/withMobileDialog";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";

const styles = theme => ({
  button: {
    marginBottom: 16,
    float: "right",
    marginRight: 16
  },
  flex: {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    flexGrow: 0
  }
});

const PackageForm = props => (
  <Dialog
    open={props.packageDialog}
    aria-labelledby="form-dialog-title"
    onClose={() => {
      props.onDialogClose();
      props.getPackage(props.roomIndex + 1);
    }}
    fullWidth
  >
    <DialogTitle id="form-dialog-title">Add a Package</DialogTitle>
    <form
      onSubmit={event => {
        event.preventDefault();
        props.onAddPackage(event, props.roomIndex);
      }}
    >
      <DialogContent className={props.classes.flex}>
        <FormControl className={props.classes.formControl}>
          <InputLabel>Room</InputLabel>
          <Select
            name="room"
            value={props.roomIndex}
            onChange={event => {
              props.onSwitchItem(event.target.value);
              props.getPackage(event.target.value + 1);
            }}
          >
            {Array.from({ length: 20 }, (v, k) => (
              <MenuItem value={k} key={k}>
                {k + 1}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <TextField
          name="receiver"
          label="Receiver Name"
          className={props.classes.textField}
          margin="normal"
        />
        <TextField
          name="package"
          label="Package No."
          className={props.classes.textField}
          margin="normal"
        />
      </DialogContent>
      <DialogActions>
        <Button
          color="primary"
          className={props.classes.button}
          type="submit"
          disabled={props.fetchResult.loading}
          variant="outlined"
        >
          {props.fetchResult.loading && <CircularProgress size={25} />}
          {props.fetchResult.loading || <div>Add</div>}
        </Button>
      </DialogActions>
    </form>
  </Dialog>
);

const mapDispatchToProps = dispatch => ({
  onAddPackage(event, room) {
    dispatch(
      postPackage(
        room + 1,
        event.target.receiver.value,
        event.target.package.value
      )
    );
  },
  onDialogClose() {
    dispatch(switchStatus("PACKAGE_DIALOG", false));
  },
  onSwitchItem: position => {
    dispatch(switchPosition("PACKAGE_ROOM", position));
  },
  getPackage(room) {
    dispatch(getPackage(room));
  }
});

const mapStateToProps = state => ({
  fetchResult: state.postPackageResult,
  packageDialog: state.packageDialogStatus,
  roomIndex: state.packageRoom
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(withMobileDialog()(PackageForm)));
