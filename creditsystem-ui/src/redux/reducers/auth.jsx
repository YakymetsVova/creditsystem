import {
    LOGIN_SUCCESS,
    LOGIN_FAIL,
    LOGOUT, REGISTER_SUCCESS,
} from "../types/types";
import jwtDecode from "jwt-decode";

const storageToken = JSON.parse(localStorage.getItem("token"));
const userRole = storageToken ? jwtDecode(storageToken).userRole : null;
const initialState = storageToken
    ? {isLoggedIn: true, token: storageToken, userRole,}
    : {isLoggedIn: false, token: null, userRole: null,};


export default function auth(state = initialState, action) {
    const {type, payload, userRole} = action;
    switch (type) {
        case LOGIN_SUCCESS:
            return {
                ...state,
                isLoggedIn: true,
                token: payload.token,
                userRole: userRole,
            };
        case LOGIN_FAIL:
        case LOGOUT:
            return {
                ...state,
                isLoggedIn: false,
                token: null,
                userRole: null,
            };
        case REGISTER_SUCCESS:
            return {
                ...state
            }
        default:
            return state;
    }
}