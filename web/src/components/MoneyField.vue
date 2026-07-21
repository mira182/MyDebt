<template>
    <v-text-field
            ref="field"
            :model-value="display"
            :label="label"
            :rules="rules"
            suffix="Kč"
            inputmode="numeric"
            @keydown="onKeydown"
            @update:model-value="onInput"
    ></v-text-field>
</template>

<script>
function toNumber(value) {
    const digits = String(value === null || value === undefined ? '' : value).replace(/[^\d]/g, '');
    return digits === '' ? null : Number(digits);
}

function formatMoney(value) {
    const num = toNumber(value);
    return num === null ? '' : String(num).replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
}

export default {
    name: "MoneyField",
    props: {
        modelValue: [String, Number],
        label: String,
        rules: {type: Array, default: () => []}
    },
    emits: ['update:modelValue'],
    data() {
        return {
            display: formatMoney(this.modelValue)
        }
    },
    watch: {
        modelValue(val) {
            if (toNumber(val) !== toNumber(this.display)) {
                this.display = formatMoney(val);
            }
        }
    },
    methods: {
        onKeydown(e) {
            if (e.ctrlKey || e.metaKey || e.altKey) return;
            if (e.key.length > 1) return; // Backspace, Tab, Arrow*, Delete, Enter...
            if (!/[0-9]/.test(e.key)) e.preventDefault();
        },
        onInput(raw) {
            const num = toNumber(raw);
            const formatted = formatMoney(num);
            this.display = formatted;
            this.$emit('update:modelValue', num);

            this.$nextTick(() => {
                const el = this.$refs.field && this.$refs.field.$el
                        ? this.$refs.field.$el.querySelector('input')
                        : null;
                if (el && el.value !== formatted) el.value = formatted;
            });
        }
    }
}
</script>
