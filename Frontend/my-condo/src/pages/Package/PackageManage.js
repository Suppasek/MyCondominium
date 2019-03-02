import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import { connect } from "react-redux";
import { getPackage, switchPosition, switchStatus } from "../../actions";
import CircularProgress from "@material-ui/core/CircularProgress";
import FormPaper from "../../components/FormPaper";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";

const styles = theme => ({
  button: {
    float: "right"
  }
});
class PackageManage extends Component {
  componentDidMount() {
    this.props.getPackage(1);
  }
  render() {
    const props = this.props;
    return (
      <FormPaper>
        <FormControl className={props.classes.formControl}>
          <InputLabel>Room</InputLabel>
          <Select
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
        <Button
          variant="contained"
          color="primary"
          className={props.classes.button}
          onClick={props.onOpenDialog}
        >
          Add Package
        </Button>
        {props.fetchResult.status === "success" && (
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>PackageNo</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Status</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {props.fetchResult.response.map(response => (
                <TableRow key={response.packageNo}>
                  <TableCell>{response.packageNo}</TableCell>
                  <TableCell>{response.name}</TableCell>
                  <TableCell>{response.status}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </FormPaper>
    );
  }
}

const mapDispatchToProps = dispatch => ({
  getPackage(room) {
    dispatch(getPackage(room));
  },
  onSwitchItem: position => {
    dispatch(switchPosition("PACKAGE_ROOM", position));
  },
  onOpenDialog: () => {
    dispatch(switchStatus("PACKAGE_DIALOG", true));
  }
});

const mapStateToProps = state => ({
  fetchResult: state.getPackageResult,
  roomIndex: state.packageRoom
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(PackageManage));
