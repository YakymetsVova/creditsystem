import {Link} from "react-router-dom";
import mainLogo from "../../resources/creditsystem-logo.png";
import UserDropdown from "./userDropdown";
import "./appbar.css"
import {useSelector} from "react-redux";

const selectIsLoggedIn = state => state.auth.isLoggedIn

const AppBar = () => {
    const isLoggedIn = useSelector(selectIsLoggedIn);
    return (
        <header className="header">
            <Link to="/request">
                <img src={mainLogo} style={{width: "170px", height: "170px"}} className="headerLogo"
                     alt="Creditsystem"/>
            </Link>
            <div className="dropdown show headerAccount">
                {
                    isLoggedIn &&
                    <UserDropdown/>
                }
            </div>
        </header>
    )
}

export default AppBar;