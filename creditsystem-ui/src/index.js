import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {Provider} from 'react-redux';
import '../node_modules/font-awesome/css/font-awesome.min.css';
import store from './redux/store'
import httpInit from "./httpinit";

httpInit();

ReactDOM.render(
    <Provider store={ store }>
        <React.StrictMode>
            <App/>
        </React.StrictMode>
    </Provider>,
    document.getElementById('root')
);