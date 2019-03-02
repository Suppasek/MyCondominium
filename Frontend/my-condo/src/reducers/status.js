export default function createStatusWithNamedType(statusName) {
  return function(status = false, action) {
    switch (action.type) {
      case `STATUS_CHANGE_${statusName}`:
        return action.status;
      default:
        return status;
    }
  };
}
