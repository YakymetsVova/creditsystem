import {
    LOGIN_FAIL,
    LOGIN_SUCCESS,
    LOGOUT, REGISTER_SUCCESS
} from '../types/types'

import AuthService from "../services/authService";
import jwtDecode from "jwt-decode";
import axios from '../services/httpservice'

export const login = (email, password) => async (dispatch) => {
    try {
        const token = await AuthService.login(email, password);
        const decoded = jwtDecode(token.token);
        const role = decoded.userRole;
        await dispatch({
            type: LOGIN_SUCCESS,
            payload: {token},
            userRole: role,
        });
        const storage_token = JSON.parse(localStorage.getItem("token"));
        axios.defaults.headers.common['Authorization'] = `Bearer ${ storage_token }`;


        return true;
    } catch (error) {
        dispatch({
            type: LOGIN_FAIL,
        });
    }
    return false;
};

export const logout = () => async (dispatch) => {
    await AuthService.logout();
    dispatch({
        type: LOGOUT,
    });
    return true;
};

export const register = (data) => async (dispatch) => {
    await AuthService.register({...data})
    dispatch({
        type: REGISTER_SUCCESS
    });
    dispatch(login(data.email, data.password));
}