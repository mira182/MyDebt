<template>
    <v-dialog v-model="dialog" persistent max-width="400px">
        <template v-slot:activator="{ props }">
            <v-icon size="small" class="mr-2" v-bind="props">
                mdi-pencil
            </v-icon>
        </template>

        <v-card>
            <v-form ref="form"
                    v-model="valid"
                    lazy-validation>
                <v-card-title>
                    <span class="text-h5">Edit Payment</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <MoneyField v-model="editedPayment.amount" label="Amount*" :rules="amountRules"/>
                                <DatePicker v-bind:date="editedPayment.paymentDate" v-on:date-selected="updatePaymentDate($event)"/>
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
    name: "EditPaymentDialog",
    components: {DatePicker, MoneyField},
    props: ['payment'],
    data() {
        return {
            valid: true,
            dialog: false,
            editedPayment: Object.assign({}, this.payment),
            amountRules: [
                value => !!value || 'Required.',
            ]
        }
    },
    watch: {
        dialog(open) {
            if (open) this.editedPayment = Object.assign({}, this.payment);
        }
    },
    methods: {
        updatePaymentDate(date) {
            this.editedPayment.paymentDate = date
        },
        async submit() {
            const {valid} = await this.$refs.form.validate();
            if (valid) {
                this.$emit('save-payment', this.editedPayment);
                this.dialog = false;
            }
        }
    }
}
</script>
