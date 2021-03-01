<template>
    <v-dialog v-model="dialog" persistent max-width="400px">
        <template v-slot:activator="{ on, attrs }">
        <v-icon small class="mr-2" v-bind="attrs" v-on="on">
            mdi-pencil
        </v-icon>
        </template>

        <v-card>
            <v-form ref="form"
                    v-model="valid"
                    lazy-validation>
                <v-card-title>
                    <span class="headline">Edit Payment</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <v-text-field v-model="payment.amount" label="Amount*" type="number" suffix="Kc"
                                              :rules="amountRules"></v-text-field>
                                <DatePicker v-bind:date="payment.paymentDate" v-on:date-selected="updatePaymentDate($event)"/>
                            </v-col>
                        </v-row>
                    </v-container>
                    <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
                    <v-btn color="blue darken-1" text v-on:click="submit()">Save</v-btn>
                </v-card-actions>
            </v-form>
        </v-card>
    </v-dialog>
</template>

<script>
import DatePicker from "@/components/DatePicker";

export default {
    name: "EditPaymentDialog",
    components: {DatePicker},
    props: ['payment'],
    data() {
        return {
            valid: true,
            dialog: false,
            amountRules: [
                value => !!value || 'Required.',
            ]
        }
    },
    methods: {
        updatePaymentDate(date) {
            this.payment.paymentDate = date
        },
        submit() {
            if (this.$refs.form.validate()) {
                this.$emit('save-payment', this.payment)
                this.dialog = false
            }
        }
    }
}
</script>

<style scoped>

</style>