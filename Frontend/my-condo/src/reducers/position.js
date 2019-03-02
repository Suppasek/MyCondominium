export default function createPositionWithNamedType(positionName) {
  return function(position = 0, action) {
    switch (action.type) {
      case `POSITION_CHANGE_${positionName}`:
        return action.position;
      default:
        return position;
    }
  };
}
