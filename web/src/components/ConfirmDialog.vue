<template>
  <v-dialog v-model="state.show" max-width="400" persistent>
    <v-card>
      <v-card-title>{{ state.title }}</v-card-title>
      <v-card-text>{{ state.message }}</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn variant="text" @click="answerConfirm(false)">Cancel</v-btn>
        <v-btn color="primary" variant="text" @click="answerConfirm(true)">OK</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { confirmState, answerConfirm } from '@/plugins/confirm';

export default {
  name: 'ConfirmDialog',
  data() {
    return { state: confirmState };
  },
  methods: {
    answerConfirm
  }
}
</script>
