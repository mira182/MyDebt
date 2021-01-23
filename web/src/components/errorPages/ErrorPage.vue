<template>
  <div class="text-center">
    <h1>Error<span v-if="errorMessage"> - {{errorMessage}}</span></h1>
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
  name: "ErrorPage",
  props: ['errorMessage']
}
</script>

<style scoped>

</style>