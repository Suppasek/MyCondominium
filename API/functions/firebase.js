const admin = require("firebase-admin");
const serviceAccount = require("./waterusagechecker-firebase-adminsdk-oisyq-b759e84a3a.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

exports.admin = admin;
