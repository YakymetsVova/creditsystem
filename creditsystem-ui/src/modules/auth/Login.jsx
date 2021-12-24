import React, {useState} from 'react';
import {NavLink, Redirect, useHistory} from "react-router-dom"


import {login} from "../../redux/actions/auth";
import {useDispatch, useSelector} from 'react-redux';

const selectLoggedIn = state => state.auth.isLoggedIn;

const Login = () => {
    const [ email, setEmail ] = useState('')
    const [ password, setPassword ] = useState('')
    const dispatch = useDispatch();
    const isLoggedIn = useSelector(selectLoggedIn);
    const history = useHistory();

    const handleLogin = (e) => {
        e.preventDefault();
        dispatch(login(email, password));
    }
    if ( isLoggedIn ) {
        return <Redirect
            to={ {
                pathname: "/credits"
            } }
        />;
    }
    return (
        <div className="col-md-12 ba">
            <div className="d-flex justify-content-center mt-5">
                <form onSubmit={ handleLogin } className="bg-white p-5 rounded">
                    <p className="h2 mb-0 text-center font-weight-bold">Login</p>
                    <div className="form-group row">
                        <label>
                            Email
                            <input
                                className="form-control"
                                value={ email }
                                onChange={ (e) => {
                                    setEmail(e.target.value)
                                } }/>
                        </label>
                    </div>
                    <div className="form-group row">
                        <label>
                            Password
                            <input
                                className="form-control"
                                type="password"
                                value={ password }
                                onChange={ (e) => {
                                    setPassword(e.target.value)
                                } }/>
                        </label>
                    </div>
                    <div>
                        <NavLink to="/register" className="text-decoration-none">Don't have an account? Register</NavLink>
                    </div>
                    <div className="d-flex justify-content-end">
                        <input className="btn btn-dark mt-2" type="submit" value="Submit"/>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;