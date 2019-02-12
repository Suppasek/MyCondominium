import React, { Component } from "react";
import AppBar from "../components/AppBar";
import PackageForm from "../components/PackageForm";

export default class Package extends Component {
  render() {
    return (
      <div>
        <AppBar name="Package" />
        <PackageForm />
      </div>
    );
  }
}
