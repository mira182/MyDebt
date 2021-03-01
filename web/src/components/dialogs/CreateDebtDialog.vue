<template>
    <v-dialog v-model="createDebtDialog" persistent max-width="400px">

        <template v-slot:activator="{ on, attrs }">
            <v-btn text v-bind="attrs" v-on="on">
                <v-icon dark>mdi-plus</v-icon>
            </v-btn>
        </template>

        <v-card>
            <v-form ref="form" v-model="valid" lazy-validation>
                <v-card-title>
                    <span class="headline">New Debt</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <v-text-field v-model="newDebt.title" label="Title*" type="text"
                                              required></v-text-field>
                                <v-text-field v-model="newDebt.initialDebt" label="Initial Debt*" type="number"
                                              suffix="Kc"
                                              :rules="amountRules"></v-text-field>
                                <DatePicker v-on:date-selected="updateDate($event)"/>
                                <v-textarea
                                        name="description"
                                        rows="2"
                                        label="Description"
                                        v-model="newDebt.description"
                                ></v-textarea>
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
    components: {DatePicker},
    data: function () {
        return {
            valid: true,
            createDebtDialog: false,
            menu1: false,
            amountRules: [
                value => !!value || 'Required.',
            ],
            newDebt: {}
        }
    },
    methods: {
        updateDate(date) {
            this.newDebt.debtStartDate = date
        },
        submit() {
            if (this.$refs.form.validate()) {
                this.newDebt.currentDebt = this.newDebt.initialDebt;
                this.$emit('save-debt', this.newDebt);
                this.createDebtDialog = false;
            }
        }
    },
}
</script>