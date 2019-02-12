const admin = require("firebase-admin");
const functions = require("firebase-functions");

admin.initializeApp();

const db = admin.firestore();

const randomNo = () => Math.floor(Math.random() * 1000);

exports.add = req =>
  db
    .collection("rooms")
    .doc("house_no " + req.params.id)
    .collection("package")
    .doc(req.body.packageNo)
    .set(
      JSON.parse(
        JSON.stringify({
          name: req.body.name,
          packageNo: req.body.packageNo,
          status: "pending",
          verify: randomNo
        })
      )
    );
