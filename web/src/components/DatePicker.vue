<template>
    <v-menu
            v-model="menu"
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
                    readonly
                    v-bind="attrs"
                    v-on="on"
            ></v-text-field>
        </template>
        <v-date-picker
                v-model="inputDate"
                no-title
                @input="menu = false"
        ></v-date-picker>
    </v-menu>
</template>

<script>
import DateUtils from "@/utils/dateUtils";

export default {
    name: "DatePicker",
    mixins: [DateUtils],
    props: ['date'],
    data() {
        return {
            menu: false,
            inputDate: this.date
        }
    },
    computed: {
        dateFormatted() {
            return this.formatDate(this.inputDate)
        }
    },
    watch: {
        inputDate() {
            this.$emit('date-selected', this.inputDate)
        }
    }
}
</script>

<style scoped>

</style>