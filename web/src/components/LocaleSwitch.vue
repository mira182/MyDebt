<template>
    <v-chip-group v-model="currentLocale" selected-class="text-primary" mandatory>
        <v-chip v-for="lang in langs" :key="lang" :value="lang">
            {{ lang.toUpperCase() }}
        </v-chip>
    </v-chip-group>
</template>

<script>
    export default {
        data () {
            return { langs: ['en', 'cz'] }
        },
        computed: {
            currentLocale: {
                get() {
                    return this.$i18n.locale;
                },
                set(val) {
                    if (val) this.$i18n.locale = val;
                }
            }
        }
    }
</script>
