const firebase = require("../firebase");

const db = firebase.admin.firestore();

exports.getToken = room =>
  db
    .collection("users")
    .where("room", "==", room)
    .get()
    .then(snapshot => {
      return snapshot.docs[0].get("deviceToken");
    });
