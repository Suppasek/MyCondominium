const firebase = require("../firebase");

const db = firebase.admin.firestore();

var randomNo = () => Math.floor(Math.random() * 10000);

exports.add = (room, name, packageNo) =>
  db
    .collection("rooms")
    .doc("house_no " + room.replace(/\s/g, ""))
    .collection("package")
    .doc(packageNo)
    .set(
      JSON.parse(
        JSON.stringify({
          name,
          packageNo,
          status: "pending",
          verify: randomNo().toString()
        })
      ),
      { merge: true }
    );

exports.get = (room, res) =>
  db
    .collection("rooms")
    .doc("house_no " + room)
    .collection("package")
    .get()
    .then(function(querySnapshot) {
      let packages = [];
      querySnapshot.forEach(function(doc) {
        packages.push(doc.data());
      });
      res.send(packages);

      return null;
    })
    .catch(err => {
      res.status(500).send(err);
      console.log(err);
    });
