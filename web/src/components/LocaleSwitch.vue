<template>
<!--        <select v-model="$i18n.locale">-->
<!--            <option v-for="(lang, i) in langs" :key="`Lang${i}`" :value="lang">{{ lang }}</option>-->
<!--        </select>-->
<!--        <v-col class="d-flex" sm="2">-->
<!--            <v-select v-model="$i18n.locale" :items="langs" outlined></v-select>-->
    <v-chip-group v-model="$i18n.locale" active-class="blue--text text--lighten-1" mandatory>
        <v-chip v-for="lang in langs" :key="lang" :value="lang">
            {{ lang | capitalize }}
        </v-chip>
    </v-chip-group>
<!--        </v-col>-->
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