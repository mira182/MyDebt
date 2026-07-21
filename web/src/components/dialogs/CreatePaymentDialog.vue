<template>
    <v-dialog v-model="dialog" persistent max-width="400px">
        <template v-slot:activator="{ props }">
            <v-btn variant="text" size="small" color="primary" v-bind="props">
                <v-icon size="small" class="mr-1">mdi-plus</v-icon>{{ $t('debts.addPayment') }}
            </v-btn>
        </template>

        <v-card>
            <v-form ref="form" v-model="valid" lazy-validation>
                <v-card-title>
                    <span class="text-h5">New Payment</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <MoneyField v-model="newPayment.amount" label="Amount*" :rules="amountRules"/>
                                <DatePicker v-bind:date="newPayment.paymentDate" label="Date*"
                                            :rules="dateRules"
                                            v-on:date-selected="updatePaymentDate($event)" />
                            </v-col>
                        </v-row>
                    </v-container>
                    <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" variant="text" @click="dialog = false">Close</v-btn>
                    <v-btn color="primary" variant="text" @click="submit()">Save</v-btn>
                </v-card-actions>
            </v-form>
        </v-card>
    </v-dialog>
</template>

<script>
import DatePicker from "@/components/DatePicker.vue";
import MoneyField from "@/components/MoneyField.vue";

    export default {
        components: {DatePicker, MoneyField},
        data() {
            return {
                valid: true,
                dialog: false,
                amountRules: [
                    value => !!value || 'Required.',
                ],
                dateRules: [
                    value => !!value || 'Required.',
                ],
                newPayment: {},
            }
        },
        methods: {
            updatePaymentDate: function(date) {
                this.newPayment.paymentDate = date;
            },
            async submit () {
                const {valid} = await this.$refs.form.validate();
                if (valid) {
                    this.$emit('save-payment', this.newPayment);
                    this.newPayment = {};
                    this.dialog = false;
                }
            }
        },
    }
</script>
