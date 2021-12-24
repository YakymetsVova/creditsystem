import { combineReducers } from "redux";

import credits from "./credits";
import auth from "./auth";
import contracts from "./contracts";

export default combineReducers({
    credits: credits,
    auth: auth,
    contracts: contracts
});