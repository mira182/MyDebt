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
    getDebt(debtId) {
        //TODO change 1 -> debtId
        return api.get('debts/' + 1);
    },
    getPaymentsForDebt(debtId) {
        //TODO change 1 -> debtId
        return api.get('debts/' + 1 + '/payments');
    },
    createPayment(debtId, payment) {
        return api.post('debts/' + debtId + '/addPayment', payment);
    },
    updatePayment(paymentId, payment) {
        return api.post('payments/' + paymentId, payment);
    },
    deletePayment(debtId, paymentId) {
        return api.delete('debts/' + debtId + '/payments/' + paymentId);
    }
}