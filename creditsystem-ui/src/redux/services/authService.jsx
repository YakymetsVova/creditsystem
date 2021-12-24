import axios from './httpservice'
import jwtDecode from 'jwt-decode'
import {login} from "../actions/auth";

const authAction = (response) => {
    if ( response.data ) {
        const token = response.data.token;
        localStorage.setItem("token", JSON.stringify(token));
        const user = jwtDecode(token);
        localStorage.setItem("userRole", user.userRole);
    }
    return response.data;
}


class AuthService {
    async login(email, password) {
        return axios
            .post("/auth/login", {email, password})
            .then(authAction);
    }

    async logout() {
        //await axios.get("/auth/logout");
        localStorage.removeItem("token");
        localStorage.removeItem("userRole");
    }

    async register({email, password, userRole, firstName = null, lastName = null}) {
        await axios.post("/auth/register", {email, password, userRole, firstName, lastName})
    }
}

export default new AuthService();