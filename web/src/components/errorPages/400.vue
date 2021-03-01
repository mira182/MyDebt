<template>
    <div class="text-center">
        <h1>400 - Bad Request</h1>
        <v-container>
            <v-row>
                <v-col class="md-4" offset="md-3">
                    <v-textarea
                            label="Request data" readonly
                            rows="5"
                            :value="requestDataPretty"
                    ></v-textarea>
                </v-col>
                <v-spacer></v-spacer>
            </v-row>
        </v-container>
        <v-btn
                elevation="4"
                large
                raised
                @click="$router.push('/')"
        >HOME
        </v-btn>
    </div>
</template>

<script>
export default {
    name: "BadRequest",
    props: ['requestData'],
    filters: {
        pretty: function (value) {
            return JSON.stringify(JSON.parse(value), null, 2);
        }
    },
    computed: {
        requestDataPretty() {
            return this.$options.filters.pretty(this.requestData);
        }
    }
}
</script>

<style scoped>

</style>