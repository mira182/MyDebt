<template>
    <div class="text-center">
        <h1>Error<span v-if="errorMessage"> - {{ errorMessage }}</span></h1>
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
    name: "ErrorPage",
    computed: {
        errorMessage() {
            return this.$store.state.error.errorMessage;
        }
    }
}
</script>
