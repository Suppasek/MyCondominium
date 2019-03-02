import { combineReducers } from "redux";
import createPositionWithNamedType from "./position";
import createStatusWithNamedType from "./status";
import createFetchResultWithNamedType from "./fetchResult";

export default combineReducers({
  tab: createPositionWithNamedType("TAB"),
  menu: createPositionWithNamedType("MENU"),
  packageSnackBar: createStatusWithNamedType("PACKAGE_SNACKBAR"),
  postPackageResult: createFetchResultWithNamedType("POST_PACKAGE"),
  announceTag: createPositionWithNamedType("ANNOUNCE_TAG"),
  getPackageResult: createFetchResultWithNamedType("GET_PACKAGE"),
  packageRoom: createPositionWithNamedType("PACKAGE_ROOM"),
  packageDialogStatus: createStatusWithNamedType("PACKAGE_DIALOG"),
  postAnnounceResult: createFetchResultWithNamedType("POST_ANNOUNCE"),
  announceSnackBar: createStatusWithNamedType("ANNOUNCE_SNACKBAR")
});
