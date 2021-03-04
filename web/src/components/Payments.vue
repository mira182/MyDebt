<template>
    <div v-if="!loading">
        <v-data-table
                :headers="headers"
                :items="payments"
                :sort-by="['paymentDate']"
                :sort-desc="[false, true]"
                multi-sort
                class="elevation-1">
            <template v-slot:item.paymentDate="{ item }">
                <span>{{ item.paymentDate | formatDate }}</span>
            </template>
            <template v-slot:item.amount="{ item }">
                <span>{{ item.amount | formatPrice }} Kc</span>
            </template>
            <template v-slot:item.actions="{ item }">
                <EditPaymentDialog v-bind:payment="item" v-on:save-payment="editPayment($event)" />
                <v-icon small @click="deleteItem(item)">
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>

        <v-container fluid class="pa-0">
            <v-row>
                <v-spacer></v-spacer>
                <v-col class="text-right">
                    <CreatePaymentDialog v-on:save-payment="addPayment($event)"/>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    import CreatePaymentDialog from "./dialogs/CreatePaymentDialog";
    import DebtRestService from "../services/DebtRestService";
    import DateUtils from '../utils/dateUtils';
    import EditPaymentDialog from "@/components/dialogs/EditPaymentDialog";

    export default {
        name: "Payments",
        components: {EditPaymentDialog, CreatePaymentDialog},
        props: ['debtId'],
        mixins: [DateUtils],
        data() {
            return {
                loading: true,
                payments: null
            }
        },
        mounted() {
            this.getPaymentsForDebt(this.debtId)
        },
        computed: {
            headers() {
                return [
                    {text: this.$t('payments.paymentDate'), value: 'paymentDate'},
                    {text: this.$t('payments.amount'), value: 'amount'},
                    {text: this.$t('payments.actions'), value: 'actions', sortable: false},
                ];
            }
        },
        methods: {
            addPayment(payment) {
                DebtRestService.createPayment(this.debtId, payment).then(response => {
                    if (response) {
                        this.getPaymentsForDebt(this.debtId)
                        this.$emit('payment-added')
                    }
                }).catch(() => {
                    this.$store.dispatch('setSnackbar', {
                        show: true,
                        color: 'error',
                        message: "Saving payment failed."
                    })
                })
            },
            getPaymentsForDebt(debtId) {
                DebtRestService.getPaymentsForDebt(debtId).then(response => {
                    this.payments = response.data;
                    this.loading = false;
                })
            },
            editPayment(payment) {
                DebtRestService.updatePayment(payment.id, payment)
                        .then(() => {
                            let itemIndex = this.payments.indexOf(payment)
                            this.payments.splice(itemIndex, 1, payment)
                        })
                        .catch(() => {
                            this.$store.dispatch('setSnackbar', {
                                show: true,
                                color: 'error',
                                message: "Saving payment failed."
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
                                        message: "Saving payment failed."
                                    })
                                })
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>