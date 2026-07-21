<template>
    <div v-if="!loading">
        <v-data-table
                :headers="headers"
                :items="payments"
                :sort-by="sortBy"
                multi-sort
                class="elevation-1">
            <template #item.paymentDate="{ item }">
                <span>{{ $formatDate(item.paymentDate) }}</span>
            </template>
            <template #item.amount="{ item }">
                <span>{{ $formatPrice(item.amount) }} Kč</span>
            </template>
            <template #item.actions="{ item }">
                <EditPaymentDialog v-bind:payment="item" v-on:save-payment="editPayment($event)" />
                <v-icon size="small" @click="deleteItem(item)">
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
    </div>
</template>

<script>
    import DebtRestService from "../services/DebtRestService";
    import EditPaymentDialog from "@/components/dialogs/EditPaymentDialog.vue";

    export default {
        name: "Payments",
        components: {EditPaymentDialog},
        // reloadKey is bumped by the parent to trigger a reload after a payment
        // is added from the debt summary panel.
        props: ['debtId', 'reloadKey'],
        data() {
            return {
                loading: true,
                payments: null,
                sortBy: [{key: 'paymentDate', order: 'desc'}]
            }
        },
        mounted() {
            this.getPaymentsForDebt(this.debtId)
        },
        watch: {
            reloadKey() {
                this.getPaymentsForDebt(this.debtId)
            }
        },
        computed: {
            headers() {
                return [
                    {title: this.$t('payments.paymentDate'), key: 'paymentDate'},
                    {title: this.$t('payments.amount'), key: 'amount'},
                    {title: this.$t('payments.actions'), key: 'actions', sortable: false},
                ];
            }
        },
        methods: {
            getPaymentsForDebt(debtId) {
                DebtRestService.getPaymentsForDebt(debtId).then(response => {
                    this.payments = response.data;
                    this.loading = false;
                })
            },
            editPayment(payment) {
                DebtRestService.updatePayment(payment.id, payment)
                        .then(() => {
                            const idx = this.payments.findIndex(p => p.id === payment.id);
                            if (idx !== -1) this.payments.splice(idx, 1, payment);
                            this.$emit('payment-edited');
                        })
                        .catch(() => {
                            this.$store.dispatch('setSnackbar', {
                                show: true,
                                color: 'error',
                                message: "Updating payment failed."
                            })
                        })
            },

            deleteItem(item) {
                const index = this.payments.indexOf(item);
                this.$confirm('Do you really want to delete payment?', {title: 'Warning'}).then(res => {
                    if (res) {
                        DebtRestService.deletePayment(this.debtId, item.id)
                                .then(() => {
                                    this.$emit('payment-deleted')
                                    this.payments.splice(index, 1)
                                })
                                .catch(() => {
                                    this.$store.dispatch('setSnackbar', {
                                        show: true,
                                        color: 'error',
                                        message: "Deleting payment failed."
                                    })
                                })
                    }
                })
            },
        }
    }
</script>
