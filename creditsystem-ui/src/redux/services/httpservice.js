import axios from "axios";


const instance = axios.create({
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8081/api/customer',
    timeout: 10000,
});

instance.interceptors.request.use(async config => {
    config.baseURL = localStorage.getItem("userRole") === "Customer"
        ? 'http://localhost:8081/api/customer' : 'http://localhost:8080/api/credit-provider';
    return config;
});


export default instance;