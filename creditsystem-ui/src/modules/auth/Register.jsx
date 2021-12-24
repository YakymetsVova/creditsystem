import React, {useState} from 'react';
import {Redirect} from "react-router-dom"


import {register} from "../../redux/actions/auth";
import {useDispatch, useSelector} from 'react-redux';

const selectLoggedIn = state => state.auth.isLoggedIn;

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState(null);
    const [lastName, setLastName] = useState(null);
    const [userRole, setUserRole] = useState("Customer");

    const dispatch = useDispatch();
    const isLoggedIn = useSelector(selectLoggedIn);

    const handleRegister = (e) => {
        e.preventDefault();
        dispatch(register({email, password, userRole, firstName, lastName}));
    }

    if (isLoggedIn) {
        return <Redirect
            to={{
                pathname: "/credits"
            }}
        />;
    }

    return (
        <div className="col-md-12 ba">
            <div className="d-flex justify-content-center mt-5">
                <form onSubmit={handleRegister} className="bg-white p-5 rounded">
                    <p className="h2 mb-0 text-center font-weight-bold">Register</p>
                    {userRole === "Customer" &&
                    <>
                        <div className="form-group row">
                            <label>
                                First name
                                <input
                                    className="form-control"
                                    value={firstName}
                                    onChange={(e) => {
                                        setFirstName(e.target.value)
                                    }}/>
                            </label>
                        </div>

                        <div className="form-group row">
                            <label>
                                Last name
                                <input
                                    className="form-control"
                                    type="other"
                                    value={lastName}
                                    onChange={(e) => {
                                        setLastName(e.target.value)
                                    }}/>
                            </label>
                        </div>
                    </>
                    }

                    <div className="form-group row">
                        <label>
                            Email
                            <input
                                className="form-control"
                                value={email}
                                onChange={(e) => {
                                    setEmail(e.target.value)
                                }}/>
                        </label>
                    </div>

                    <div className="form-group row">
                        <label>
                            Password
                            <input
                                className="form-control"
                                type="password"
                                value={password}
                                onChange={(e) => {
                                    setPassword(e.target.value)
                                }}/>
                        </label>
                    </div>

                    <div className="form-group row">
                        <label>
                            User role
                            <select className="form-select" id="spokes_from_select"
                                    value={userRole}
                                    onChange={(e) => setUserRole(e.target.value)}>
                                <option value="Customer">Customer</option>
                                <option value="CreditProvider">Credit provider</option>
                            </select>
                        </label>
                    </div>

                    <div className="d-flex justify-content-end">
                        <input className="btn btn-dark mt-2" type="submit" value="Submit"/>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Register;