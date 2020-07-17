import axios from 'axios';

export default {
    getDebts() {
        return axios.get(process.env.VUE_APP_API_URL + 'debts');
    },
    createDebt(debt) {
        return axios.post(process.env.VUE_APP_API_URL + 'debts', debt);
    },
    createPayment(debtId, payment) {
        return axios.post(process.env.VUE_APP_API_URL + 'debts/' + debtId + '/addPayment', payment);
    },
    getDebt(debtId) {
        return axios.get(process.env.VUE_APP_API_URL + 'debts/' + debtId);
    },
    getPaymentsForDebt(debtId) {
        return axios.get(process.env.VUE_APP_API_URL + 'debts/' + debtId + '/payments');
    }
}