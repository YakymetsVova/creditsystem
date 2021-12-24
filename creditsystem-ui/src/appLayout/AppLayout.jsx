import React, {useState} from 'react';
import './applayout.css'
import AppBar from "../components/appBar/appBar";
import DrawerMenu from "../components/drawerMenu/DrawerMenu";


const AppLayout = ({children}) => {

    const menuList = useState(1);

    return (
        <div>
                <>
                    <AppBar menuList={menuList} />
                    <DrawerMenu
                        menuList={menuList}/>
                </>
                <main>
                    {children}
                </main>
        </div>
    );
};

export default AppLayout;