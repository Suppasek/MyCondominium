import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import { Provider } from "react-redux";
import rootReducer from "./reducers";
import { createStore } from "redux";
import { MemoryRouter } from "react-router";

const store = createStore(rootReducer);

const AppWithRouter = () => (
  <Provider store={store}>
    <MemoryRouter>
      <App />
    </MemoryRouter>
  </Provider>
);

ReactDOM.render(<AppWithRouter />, document.getElementById("root"));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
