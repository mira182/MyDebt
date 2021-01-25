<template>
    <v-dialog v-model="dialog" persistent max-width="400px">
        <template v-slot:activator="{ on, attrs }">
                    <v-btn icon v-bind="attrs" v-on="on">
                        <v-icon dark>mdi-plus</v-icon>
                    </v-btn>
        </template>

            <v-card>
                <v-form ref="form"
                        v-model="valid"
                        lazy-validation>
                    <v-card-title>
                        <span class="headline">New Payment</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col>
                                    <v-text-field v-model="newPayment.amount" label="Amount*" type="number" suffix="Kc"
                                                  :rules="amountRules"></v-text-field>
                                  <v-menu
                                      ref="menu1"
                                      v-model="menu1"
                                      :close-on-content-click="false"
                                      transition="scale-transition"
                                      offset-y
                                      max-width="290px"
                                      min-width="auto"
                                  >
                                    <template v-slot:activator="{ on, attrs }">
                                      <v-text-field
                                          v-model="dateFormatted"
                                          label="Date"
                                          hint="DD-MM-YYYY format"
                                          persistent-hint
                                          append-icon="mdi-calendar"
                                          v-bind="attrs"
                                          @blur="date = parseDate(dateFormatted)"
                                          v-on="on"
                                      ></v-text-field>
                                    </template>
                                    <v-date-picker
                                        v-model="date"
                                        no-title
                                        @input="menu1 = false"
                                    ></v-date-picker>
                                  </v-menu>
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
import DateUtils from "../../utils/dateUtils";

    export default {
        mixins: [DateUtils],
        data: vm => ({
            valid: true,
            dialog: false,
            date: new Date().toISOString().split("T")[0],
            dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
            menu1: false,
            amountRules: [
                value => !!value || 'Required.',
            ],
            newPayment: {},
        }),

        watch: {
          date() {
            this.dateFormatted = this.formatDate(this.date)
          },
        },
        methods: {
            submit () {
                if (this.$refs.form.validate()) {
                    this.newPayment.paymentDate = this.date;
                    this.$emit('save-payment', this.newPayment);
                    this.dialog = false;
                }
            }
        },
    }
</script>