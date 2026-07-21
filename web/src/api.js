import axios from 'axios';
import router from './router';
import store from './store';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

api.interceptors.response.use(
    response => response,
    error => errorHandler(error)
)

const errorHandler = (error) => {
    const status = error.response ? error.response.status : null;
    switch (status) {
        case 400:
            store.commit('SET_ERROR', {requestData: error.response.config.data});
            router.push({name: '400'});
            break;
        case 404:
            router.push('/404');
            break;
        case 500:
            router.push('/500');
            break;
        default:
            store.commit('SET_ERROR', {errorMessage: error.response && error.response.data ? error.response.data.error : 'Unknown error'});
            router.push({name: 'error'});
            break;
    }

    return Promise.reject(error);
}

export default api;
