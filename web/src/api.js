import axios from 'axios';
import {router} from './main';

const api = axios.create({
    'baseURL': process.env.VUE_APP_API_URL,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

api.interceptors.response.use(
    response => successHandler(response),
    error => errorHandler(error)
)

const errorHandler = (error) => {
    let path = '/error';
    switch (error.response.status) {
        case 404: path = '/404'; break;
        case 500: path = '/500'; break;
    }
    console.error(error.response.status);
    router.push(path);
    return Promise.reject(error);
}

const successHandler = (response) => {
    return response
}

export default api;