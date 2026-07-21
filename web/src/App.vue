<template>
  <v-app>
    <v-app-bar>
      <v-app-bar-title>{{ $t('title') }}</v-app-bar-title>

      <v-spacer></v-spacer>

      <!-- Page-level actions teleport in here (e.g. Add debt from Debts.vue) -->
      <div id="app-bar-actions" class="d-flex align-center mr-2"></div>

      <v-btn
              icon
              variant="text"
              :title="isDark ? 'Switch to light theme' : 'Switch to dark theme'"
              :aria-label="isDark ? 'Switch to light theme' : 'Switch to dark theme'"
              class="mr-2"
              @click="isDark = !isDark"
      >
        <v-icon>{{ isDark ? 'mdi-weather-night' : 'mdi-white-balance-sunny' }}</v-icon>
      </v-btn>
      <LocaleSwitch />
    </v-app-bar>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>

      <v-snackbar v-model="snackbar.show" :color="snackbar.color" :timeout="snackbar.timeout">
        {{ snackbar.message }}
        <template #actions>
          <v-btn variant="text" @click="snackbar.show = false">Close</v-btn>
        </template>
      </v-snackbar>
    </v-main>

    <ConfirmDialog />
  </v-app>
</template>

<script>
import LocaleSwitch from './components/LocaleSwitch.vue';
import ConfirmDialog from './components/ConfirmDialog.vue';

export default {
  components: { LocaleSwitch, ConfirmDialog },
  computed: {
    isDark: {
      get() {
        return this.$vuetify.theme.global.name === 'dark';
      },
      set(val) {
        this.$vuetify.theme.global.name = val ? 'dark' : 'light';
      }
    },
    snackbar() {
      return this.$store.state.snackbar;
    }
  }
}
</script>
