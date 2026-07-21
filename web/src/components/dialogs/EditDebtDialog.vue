<template>
    <v-dialog v-model="dialog" persistent max-width="400px">

        <template v-slot:activator="{ props }">
            <v-btn variant="text" size="small" v-bind="props">
                <v-icon size="small" class="mr-1">mdi-pencil</v-icon>{{ $t('debts.editDebt') }}
            </v-btn>
        </template>

        <v-card>
            <v-form ref="form" v-model="valid" lazy-validation>
                <v-card-title>
                    <span class="text-h5">{{ $t('debts.editDebt') }}</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <v-text-field v-model="editedDebt.title" label="Title*" type="text"
                                              :rules="titleRules"></v-text-field>
                                <MoneyField v-model="editedDebt.initialDebt" label="Initial Debt*"
                                            :rules="amountRules"/>
                                <DatePicker v-bind:date="editedDebt.debtStartDate"
                                            v-on:date-selected="updateDate($event)"/>
                                <v-textarea
                                        name="description"
                                        rows="2"
                                        label="Description"
                                        v-model="editedDebt.description"
                                ></v-textarea>
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
    name: "EditDebtDialog",
    components: {DatePicker, MoneyField},
    props: ['debt'],
    data: function () {
        return {
            valid: true,
            dialog: false,
            titleRules: [
                value => !!(value && value.trim()) || 'Required.',
            ],
            amountRules: [
                value => !!value || 'Required.',
            ],
            editedDebt: Object.assign({}, this.debt)
        }
    },
    watch: {
        // Re-sync the local copy each time the dialog opens, so it reflects the
        // latest saved values (e.g. after a payment changed the debt).
        dialog(open) {
            if (open) this.editedDebt = Object.assign({}, this.debt);
        }
    },
    methods: {
        updateDate(date) {
            this.editedDebt.debtStartDate = date
        },
        async submit() {
            const {valid} = await this.$refs.form.validate();
            if (valid) {
                this.$emit('save-debt', this.editedDebt);
                this.dialog = false;
            }
        }
    },
}
</script>
