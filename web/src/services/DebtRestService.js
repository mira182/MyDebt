import axios from 'axios';

export default {
    getDebts() {
        return axios.get('http://localhost:8082/debts');
    },
    createDebt(debt) {
        return axios.post('http://localhost:8082/debts', debt);
    },
    createPayment(debtId, payment) {
        return axios.post('http://localhost:8082/debts/' + debtId + '/addPayment', payment);
    },
    getDebt(debtId) {
        return axios.get('http://localhost:8082/debts/' + debtId);
    },
    getPaymentsForDebt(debtId) {
        return axios.get('http://localhost:8082/debts/' + debtId + '/payments');
    }
}