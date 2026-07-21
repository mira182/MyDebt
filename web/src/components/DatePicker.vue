<template>
    <v-menu v-model="menu" :close-on-content-click="false" transition="scale-transition">
        <template v-slot:activator="{ props }">
            <v-text-field
                    :model-value="displayDate"
                    :label="label"
                    :rules="rules"
                    hint="DD-MM-YYYY"
                    persistent-hint
                    readonly
                    append-inner-icon="mdi-calendar"
                    v-bind="props"
            ></v-text-field>
        </template>
        <v-date-picker
                v-model="pickerDate"
                hide-header
                @update:model-value="menu = false"
        ></v-date-picker>
    </v-menu>
</template>

<script>
export default {
    name: "DatePicker",
    props: {
        date: String,
        label: {type: String, default: 'Date'},
        rules: {type: Array, default: () => []}
    },
    data() {
        return {
            menu: false,
            pickerDate: this.parseInput(this.date)
        }
    },
    computed: {
        displayDate() {
            if (!this.pickerDate) return '';
            const d = this.pickerDate;
            return `${this.pad(d.getDate())}-${this.pad(d.getMonth() + 1)}-${d.getFullYear()}`;
        }
    },
    watch: {
        // Follow the prop when the parent swaps the value in or clears it
        // (form reset after saving, or an edit dialog opening on another row).
        date(val) {
            const current = this.pickerDate ? this.toIso(this.pickerDate) : null;
            if ((val || null) !== current) {
                this.pickerDate = this.parseInput(val);
            }
        },
        pickerDate(val) {
            this.$emit('date-selected', val ? this.toIso(val) : null);
        }
    },
    methods: {
        pad(n) {
            return String(n).padStart(2, '0');
        },
        parseInput(s) {
            if (!s) return null;
            // Build a local Date to avoid the UTC off-by-one from new Date('yyyy-mm-dd').
            const [y, m, d] = s.split('-').map(Number);
            return new Date(y, m - 1, d);
        },
        toIso(d) {
            return `${d.getFullYear()}-${this.pad(d.getMonth() + 1)}-${this.pad(d.getDate())}`;
        }
    }
}
</script>
