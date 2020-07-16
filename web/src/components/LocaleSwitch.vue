<template>
<!--        <select v-model="$i18n.locale">-->
<!--            <option v-for="(lang, i) in langs" :key="`Lang${i}`" :value="lang">{{ lang }}</option>-->
<!--        </select>-->
        <v-col class="d-flex" sm="2">
            <v-select v-model="$i18n.locale" :items="langs" outlined></v-select>
        </v-col>
</template>

<script>
    export default {
        data () {
            return { langs: ['en', 'cz'] }
        }
    }
</script>