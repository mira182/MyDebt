<template>
    <div class="text-center">
        <h1>500 - Server error</h1>
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
    name: "ServerError500"
}
</script>
