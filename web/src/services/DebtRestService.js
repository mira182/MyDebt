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
        console.log("Debt ID:",debtId)
        return api.get('debts/' + debtId);
    },
    getPaymentsForDebt(debtId) {
        console.log("Debt ID:",debtId)
        return api.get('debts/' + debtId + '/payments');
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