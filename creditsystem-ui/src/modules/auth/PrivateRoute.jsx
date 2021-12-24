import React from "react";
import { useSelector } from "react-redux";
import {Redirect, Route} from "react-router-dom"

const selectIsLogged = state => state.auth.isLoggedIn;
const selectRole = state => state.auth.userRole;

function PrivateRoute({component: Component, roles, ...rest}) {
    const userRole = useSelector(selectRole);
    const isLoggedIn = useSelector(selectIsLogged);

    return (
        <Route
            { ...rest }
            render={ props => {
                if ( isLoggedIn === false ) {
                    return (
                        <Redirect
                            to={ {
                                pathname: "/login",
                                state: {from: props.location}
                            } }
                        />
                    );
                }
                if ( roles && roles.indexOf(userRole) === -1 ) {
                    return <Redirect to={ {pathname: '/'} }/>;
                }
                return (
                    <Component { ...props } />
                );
            }

            }
        />

    );
}

export default PrivateRoute