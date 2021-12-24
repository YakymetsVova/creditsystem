import './App.css';
import AppLayout from "./appLayout/AppLayout";
import CreditsCatalog from "./modules/credits/CreditsCatalog";
import {BrowserRouter, Route, Switch} from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/js/dist/alert"
import CreditDetail from "./modules/credits/creditDetail/CreditDetail";
import useAuth from "./redux/helpers/auth";
import Login from "./modules/auth/Login";
import PrivateRoute from "./modules/auth/PrivateRoute";
import ContractsCatalog from "./modules/contracts/ContractsCatalog";
import ContractDetail from "./modules/contracts/contractDetail/ContractDetail";
import Register from "./modules/auth/Register";

const App = () => {
    const roles = {
        creditProvider: "CreditProvider",
        customer: "Customer"
    }

    useAuth();
    return (
        <BrowserRouter>
            <AppLayout>
                <Switch>
                    <PrivateRoute roles={[roles.creditProvider]} path="/credits/create" component={CreditDetail}/>
                    <PrivateRoute roles={[roles.creditProvider, roles.customer]} path="/credits/detail/:id"
                                  component={CreditDetail}/>
                    <PrivateRoute roles={[roles.creditProvider, roles.customer]} path="/credits"
                                  component={CreditsCatalog}/>
                    <PrivateRoute roles={[roles.creditProvider, roles.customer]} path="/contracts/detail/:id"
                                  component={ContractDetail}/>
                    <PrivateRoute roles={[roles.creditProvider, roles.customer]} path="/contracts"
                                  component={ContractsCatalog}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Register}/>
                    <PrivateRoute roles={[roles.creditProvider, roles.customer]} path="/" component={CreditsCatalog}/>
                </Switch>
            </AppLayout>
        </BrowserRouter>
    );
}

export default App;
