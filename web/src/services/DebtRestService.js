import api from "../api";

export default {
    getDebts() {
        return api.get('debts');
    },
    createDebt(debt) {
        return api.post('debts', debt);
    },
    deleteDebt(debtId) {
        return api.delete('debts/' + debtId);
    },
    createPayment(debtId, payment) {
        return api.post('debts/' + debtId + '/addPayment', payment);
    },
    getDebt(debtId) {
        return api.get('debts/' + debtId);
    },
    getPaymentsForDebt(debtId) {
        return api.get('debts/' + debtId + '/payments');
    },
    deletePayment(debtId, paymentId) {
        return api.delete('debts/' + debtId + '/payments/' + paymentId);
    }
}