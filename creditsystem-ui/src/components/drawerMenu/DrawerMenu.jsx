import React, {useState} from 'react';
import './drawermenu.css'
import {NavLink} from "react-router-dom";
import {useSelector} from "react-redux";

const SidebarItem = (props) => {
    return (
        <NavLink activeStyle={{backgroundColor: "#f5f7f8", color: "black"}} to={props.to}>
            <span><i className={` ${props.iconClass}`}/></span>
            <span className="text-muted sidebar-item-component">{props.description}</span>
        </NavLink>
    )
}

const roles = {
    customer: "Customer",
    creditProvider: "CreditProvider",
}

const menuItems = (userRole, sidebarItems) => {
    switch (userRole) {
        case roles.customer:
            return sidebarItems;
        case roles.creditProvider:
            return sidebarItems.slice(0, 3);
        default:
            return [];
    }
}


const selectRole = state => state.auth.userRole;

const DrawerMenu = () => {
    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => setIsOpen(!isOpen);
    const prefix = "fa fa-fw";
    const size = "fa-2x";
    const icons = ["fa-address-card", "fa-university",];
    const routes = ["/contracts", "/credits"];
    const descriptions = ["contracts", "credit propositions"];


    const role = useSelector(selectRole);

    const sidebarItems = icons.map((icon, index) =>
        ({
            iconClass: `${prefix} ${icon} ${size}`,
            to: routes[index],
            description: descriptions[index]
        }));

    const items = menuItems(role, sidebarItems);

    if (items.length === 0) return null;

    return (
        <div className="sidebar" style={{"width": isOpen ? "250px" : "80px"}}>
            <div className="sidebar-hamburger-wrapper">
                <button onClick={toggle} className="sidebar-hamburger-btn"
                        style={{"marginRight": isOpen ? "0px" : "15px"}}
                >
                    <i className={`fa fa-fw ${isOpen ? "fa-times" : "fa-bars"} fa-2x`}/>
                </button>
            </div>
            <div className="sidebar-items">
                {items.map(item => {
                    return (<SidebarItem key={item.iconClass} {...item}/>);
                })}
            </div>
        </div>
    )
}

export default DrawerMenu