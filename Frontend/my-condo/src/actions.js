import axios from "axios";

const api = "https://us-central1-waterusagechecker.cloudfunctions.net/condo/";

export const switchPosition = (type, position) => ({
  type: `POSITION_CHANGE_${type}`,
  position
});
export const switchStatus = (type, status) => ({
  type: `STATUS_CHANGE_${type}`,
  status
});
export const fetchStart = type => ({
  type: `FETCHING_${type}`
});
export const fetchSuccess = (type, response) => ({
  type: `FETCH_SUCCESS_${type}`,
  response
});
export const fetchFailure = (type, response) => ({
  type: `FETCH_FAILURE_${type}`,
  response
});

export const getPackage = room => {
  return dispatch => {
    dispatch(fetchStart("GET_PACKAGE", true));
    axios
      .get(`${api}room/${room}/package`)
      .then(response => {
        dispatch(fetchSuccess("GET_PACKAGE", response));
      })
      .catch(error => {
        dispatch(fetchSuccess("GET_PACKAGE", error));
      });
  };
};

export const postPackage = (room, name, packageNo) => {
  return dispatch => {
    dispatch(fetchStart("POST_PACKAGE", true));
    axios
      .post(`${api}room/${room}/package`, {
        name,
        packageNo
      })
      .then(response => {
        dispatch(fetchSuccess("POST_PACKAGE", response));
        dispatch(switchStatus("PACKAGE_SNACKBAR", true));
      })
      .catch(error => {
        dispatch(fetchFailure("POST_PACKAGE", error));
        dispatch(switchStatus("PACKAGE_SNACKBAR", true));
      });
  };
};

export const postAnnounce = (tag, message) => {
  return dispatch => {
    dispatch(fetchStart("POST_ANNOUNCE", true));
    axios
      .post(`${api}announce`, {
        tag,
        message
      })
      .then(response => {
        dispatch(fetchSuccess("POST_ANNOUNCE", response));
        dispatch(switchStatus("ANNOUNCE_SNACKBAR", true));
      })
      .catch(error => {
        dispatch(fetchFailure("POST_ANNOUNCE", error));
        dispatch(switchStatus("ANNOUNCE_SNACKBAR", true));
      });
  };
};
