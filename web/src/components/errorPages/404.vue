<template>
  <div class="text-center">
    <h1>404 - Page not found</h1>
    <v-btn
        elevation="4"
        large
        raised
        @click="$router.push('/')"
    >HOME</v-btn>
  </div>
</template>

<script>
export default {
  name: "PageNotFound404"
}
</script>

<style scoped>

</style>