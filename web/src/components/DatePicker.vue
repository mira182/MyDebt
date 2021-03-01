<template>
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
</template>

<script>
import DateUtils from "@/utils/dateUtils";

export default {
    name: "DatePicker",
    mixins: [DateUtils],
    props: ['inputDate'],
    data: vm => ({
        date: null,
        dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
        menu1: false
    }),
    created() {
        this.date = this.inputDate ? this.inputDate : new Date().toISOString().split("T")[0]
        this.$emit('date-selected', this.date)
    },
    watch: {
        date() {
            this.dateFormatted = this.formatDate(this.date)
            this.$emit('date-selected', this.date)
        }
    },
}
</script>

<style scoped>

</style>