const functions = require("firebase-functions");
const express = require("express");
const app = express();
const package = require("./models/package");
const cors = require("cors");

app.use(cors());

app.post("/room/:id/package", (req, res) =>
  package
    .add(req)
    .then(() => res.status(201).send("success"))
    .catch(err => res.status(500).send(err))
);

exports.condo = functions.https.onRequest(app);
