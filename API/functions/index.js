const functions = require("firebase-functions");
const express = require("express");
const app = express();
const package = require("./models/package");
const announce = require("./models/announce");
const user = require("./models/user");
const cors = require("cors");
const firebase = require("./firebase");

app.use(cors());

const messaging = firebase.admin.messaging();

app.get("/room/:id/package", (req, res) => package.get(req.params.id, res));

app.post("/room/:id/package", (req, res) =>
  package
    .add(req.params.id, req.body.name, req.body.packageNo)
    .then(async () => {
      let deviceToken = await user
        .getToken(req.params.id)
        .then(res.send("success"))
        .catch(err => res.send(err));

      let payload = {
        notification: {
          title: "package has arrived",
          body: req.body.packageNo
        },
        token: deviceToken
      };

      messaging.send(payload);

      return null;
    })
    .catch(err => res.send(err))
);

app.post("/announce", (req, res) =>
  announce
    .publish(req.body.tag, req.body.message)
    .then(res.send("success"))
    .catch(err => res.send(err))
);

exports.condo = functions.https.onRequest(app);
