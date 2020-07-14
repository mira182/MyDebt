<template>
  <div>
    Debt: {{debt}}
  </div>
</template>

<script>
export default {
  name: 'Debt',
  props: {
    msg: String
  },
  mounted () {
    axios
            .get('https://localhost:8082/debt')
            .then(response => (this.info = response))
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
