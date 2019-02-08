export default (state = 0, action) => {
  switch (action.type) {
    case "TABCHANGE":
      return action.tab;
    default:
      return state;
  }
};
