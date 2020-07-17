<template>
        <v-dialog v-model="dialog" persistent max-width="400px">

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
                                <v-text-field v-model="newDebt.initialDebt" label="Initial Debt*" type="number" suffix="Kc" :rules="amountRules"></v-text-field>
                                <v-text-field v-model="newDebt.title" label="Title*" type="text" required></v-text-field>
                                <v-menu
                                        ref="menu1"
                                        v-model="menu1"
                                        :close-on-content-click="false"
                                        transition="scale-transition"
                                        offset-y
                                        max-width="290px"
                                        min-width="290px"
                                >
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-text-field
                                                v-model="newDebt.debtStartDate"
                                                label="Debt start date*"
                                                hint="DD/MM/YYYY format"
                                                persistent-hint append-icon="event"
                                                v-bind="attrs"
                                                @blur="date = parseDate(newDebt.debtStartDate)"
                                                v-on="on"
                                        ></v-text-field>
                                    </template>
                                    <v-date-picker v-model="date" no-title @input="menu1 = false"></v-date-picker>
                                </v-menu>
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
    export default {
        data: vm => ({
            valid: true,
            dialog: false,
            date: new Date().toISOString().substr(0, 10),
            menu1: false,
            amountRules: [
                value => !!value || 'Required.',
            ],
            newDebt: {debtStartDate: vm.formatDate(new Date().toISOString().substr(0, 10))},
        }),

        watch: {
            date () {
                this.newDebt.debtStartDate = this.formatDate(this.date)
            },
        },

        methods: {
            formatDate (date) {
                if (!date) return null;

                const [year, month, day] = date.split('-');
                return `${day}/${month}/${year}`
            },
            parseDate (date) {
                if (!date) return null;

                const [day, month, year] = date.split('/');
                return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
            },
            submit () {
                if (this.$refs.form.validate()) {
                    this.newDebt.currentDebt = this.newDebt.initialDebt;
                    this.$emit('save-debt', this.newDebt);
                    this.dialog = false;
                }
            }
        },
    }
</script>