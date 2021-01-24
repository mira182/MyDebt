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
    switch (error.response.status) {
        case 400: router.push({ name: '400', params: {requestData: error.response.config.data}}); break;
        case 404: router.push('/404'); break;
        case 500: router.push('/500'); break;
        default: router.push({ name: 'error', params: {errorMessage: error.response.data.error }}); break;
    }

    return Promise.reject(error);
}

const successHandler = (response) => {
    return response
}

export default api;