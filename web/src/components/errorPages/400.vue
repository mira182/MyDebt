<template>
    <div class="text-center">
        <h1>400 - Bad Request</h1>
        <v-container>
            <v-row>
                <v-col cols="12" md="6" offset-md="3">
                    <v-textarea
                            label="Request data" readonly
                            rows="5"
                            :model-value="requestDataPretty"
                    ></v-textarea>
                </v-col>
            </v-row>
        </v-container>
        <v-btn
                elevation="4"
                size="large"
                @click="$router.push('/')"
        >HOME
        </v-btn>
    </div>
</template>

<script>
export default {
    name: "BadRequest",
    computed: {
        requestData() {
            return this.$store.state.error.requestData;
        },
        requestDataPretty() {
            if (!this.requestData) return '';
            try {
                return JSON.stringify(JSON.parse(this.requestData), null, 2);
            } catch (e) {
                return String(this.requestData);
            }
        }
    }
}
</script>
