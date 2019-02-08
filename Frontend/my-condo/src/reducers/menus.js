export default (state = 0, action) => {
  switch (action.type) {
    case "MENUCHANGE":
      return action.item;
    default:
      return state;
  }
};
