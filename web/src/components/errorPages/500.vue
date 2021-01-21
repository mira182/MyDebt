<template>
  <div class="text-center">
    <h1>500 - Server error</h1>
    <v-btn
        elevation="4"
        large
        raised
        @click="$router.push('/debts')"
    >HOME</v-btn>
  </div>
</template>

<script>
export default {
  name: "ServerError500"
}
</script>

<style scoped>

</style>