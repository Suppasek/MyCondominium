import React from "react";
import TextField from "@material-ui/core/TextField";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import { connect } from "react-redux";
import FormPaper from "../../components/FormPaper";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import { switchPosition, postAnnounce } from "../../actions";

const styles = theme => ({
  button: {
    marginTop: 16,
    float: "right"
  },
  formControl: {
    marginTop: theme.spacing.unit * 2,
    minWidth: 60
  }
});

const AnnounceForm = props => (
  <FormPaper formName="Announcement">
    <form
      onSubmit={event => {
        event.preventDefault();
        props.postAnnounce(event.target.tag.value, event.target.message.value);
        console.log(`${event.target.tag.value}, ${event.target.message.value}`);
      }}
    >
      <FormControl className={props.classes.formControl}>
        <InputLabel>TAG</InputLabel>
        <Select
          name="tag"
          value={props.item}
          onChange={event => props.onSwitchItem(event.target.value)}
        >
          <MenuItem value={"ประกาศ"}>ประกาศ</MenuItem>
          <MenuItem value={"ข่าวสาร"}>ข่าวสาร</MenuItem>
        </Select>
      </FormControl>
      <div>
        <TextField
          name="message"
          label="announce"
          fullWidth
          margin="normal"
          multiline
          rows="2"
        />
        <Button
          variant="contained"
          color="primary"
          className={props.classes.button}
          type="submit"
        >
          Publish
        </Button>
      </div>
    </form>
  </FormPaper>
);

const mapDispatchToProps = dispatch => ({
  onSwitchItem: position => {
    dispatch(switchPosition("ANNOUNCE_TAG", position));
  },
  postAnnounce: (tag, message) => {
    dispatch(postAnnounce(tag, message));
  }
});

const mapStateToProps = state => ({
  item: state.announceTag
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles)(AnnounceForm));
