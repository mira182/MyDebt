<template>
    <v-chip-group v-model="$i18n.locale" active-class="blue--text text--lighten-1" mandatory>
        <v-chip v-for="lang in langs" :key="lang" :value="lang">
            {{ lang | capitalize }}
        </v-chip>
    </v-chip-group>
</template>

<script>
    export default {
        data () {
            return { langs: ['en', 'cz'] }
        },
        filters: {
            capitalize: function (value) {
                if (!value) return '';
                return value.toUpperCase();
            }
        }
    }
</script>