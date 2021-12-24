import React, {useState} from 'react';
import {DropdownToggle, DropdownMenu, DropdownItem, ButtonDropdown} from "reactstrap"
import {useDispatch} from "react-redux";
import {logout} from "../../redux/actions/auth";


const UserDropdown = (props) => {
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const toggle = () => setDropdownOpen(prevState => !prevState);
    const dispatch = useDispatch();
    return (
        <ButtonDropdown isOpen={dropdownOpen} toggle={toggle}>
            <DropdownToggle caret tag="i" data-toggle="dropdown">
                <i className="fa fa-user fa-2x"/>
           </DropdownToggle>
           <DropdownMenu right>
               <DropdownItem>Change password</DropdownItem>
               <DropdownItem onClick={e => {e.preventDefault(); dispatch(logout())
               }}>Log out</DropdownItem>
           </DropdownMenu>
        </ButtonDropdown>
    );
}

export default UserDropdown;