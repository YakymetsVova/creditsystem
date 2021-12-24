import {useEffect} from 'react';
import axios from '../services/httpservice';
import {useDispatch} from 'react-redux';

import {LOGIN_FAIL, LOGIN_SUCCESS} from '../types/types';
import jwtDecode from "jwt-decode";

const useAuth = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        const token = JSON.parse(localStorage.getItem("token"));
        const userRole = token? jwtDecode(token).userRole: null;

        if ( ! token ) {
            dispatch({
                type: LOGIN_FAIL
            })
            axios.defaults.headers.common['Authorization'] = null;
            return;
        }
        dispatch({
            type: LOGIN_SUCCESS,
            payload: {token},
            userRole: userRole,
        });
        axios.defaults.headers.common['Authorization'] = `Bearer ${ token }`;


    }, []);
}

export default useAuth;