<template>
    <v-snackbar
            v-model="snackbar"
            :timeout="timeout"
    >
        {{ text }}

        <template v-slot:action="{ attrs }">
            <v-btn
                    color="blue"
                    text
                    v-bind="attrs"
                    @click="snackbar = false"
            >
                Close
            </v-btn>
        </template>
    </v-snackbar>
</template>

<script>
    export default {
        name: "AlertSnackBar",
        props: {
            text: String,
            snackbar: Boolean
        },
        data: () => ({
            timeout: 2000,
        }),
    }
</script>

<style scoped>

</style>