import React from "react";
import Account from "./pages/Account/index";
import { Route } from "react-router-dom";
import Package from "./pages/Package/index";
import Announce from "./pages/Announce/index";
import SideMenuContainer from "./components/SideMenuContainer";
import Grid from "@material-ui/core/Grid";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  spacer: { marginTop: "10%", marginBottom: "10%" }
});

const accountPage = () => <Account />;
const packagePage = () => <Package />;
const announcePage = () => <Announce />;

const Root = props => (
  <Grid container direction="row">
    <Grid item xs={2}>
      <SideMenuContainer />
    </Grid>
    <Grid item xs={6} className={props.classes.spacer}>
      <Route exact path="/" component={accountPage} />
      <Route exact path="/package" component={packagePage} />
      <Route exact path="/announce" component={announcePage} />
    </Grid>
  </Grid>
);

export default withStyles(styles)(Root);
