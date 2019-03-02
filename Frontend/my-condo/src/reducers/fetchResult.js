export default function createFetchResultWithNamedType(statusName) {
  return function(result = { status: "idle", loading: false }, action) {
    switch (action.type) {
      case `FETCH_SUCCESS_${statusName}`:
        return {
          status: "success",
          response: action.response.data,
          loading: false
        };
      case `FETCH_FAILURE_${statusName}`:
        return { status: "fail", response: "fail", loading: false };
      case `FETCHING_${statusName}`:
        return { status: "idle", loading: true };
      default:
        return result;
    }
  };
}
