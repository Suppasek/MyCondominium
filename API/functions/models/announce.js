const firebase = require("../firebase");

const db = firebase.admin.firestore();

exports.publish = (tag, body) =>
  db
    .collection("announce")
    .doc()
    .set(
      JSON.parse(
        JSON.stringify({
          tag,
          body,
          timestamp: Date.now()
        })
      ),
      { merge: true }
    );
