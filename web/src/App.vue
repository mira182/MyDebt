<template>
  <div id="app">
    <v-app id="inspire">
        <v-navigation-drawer v-model="drawer" app clipped>
          <v-list dense>
            <v-list-item link>
              <v-list-item-action>
                <v-icon>mdi-view-dashboard</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title @click="goHome()">Home</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>

        <v-app-bar app clipped-left>
          <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
          <v-toolbar-title>{{ $t('title')}}</v-toolbar-title>

          <v-spacer></v-spacer>

          <v-switch v-model="isDark" hide-details></v-switch>
          <LocaleSwitch/>
        </v-app-bar>

        <v-main>
          <v-container fluid>
            <v-row no-gutters>
              <v-col>
                <router-view></router-view>
              </v-col>
            </v-row>


            <v-snackbar v-model="snackbar.show" :color="snackbar.color" :timeout="snackbar.timeout">
              {{ snackbar.message }}

              <template v-slot:action="{ attrs }">
                <v-btn
                    text
                    v-bind="attrs"
                    @click="snackbar.show = false"
                >
                  Close
                </v-btn>
              </template>
            </v-snackbar>
          </v-container>
        </v-main>
    </v-app>
  </div>
</template>

<script>
  import Vuetify from "vuetify";
  import LocaleSwitch from "./components/LocaleSwitch";

  export default {
    components: { LocaleSwitch },
    vuetify: new Vuetify(),
    props: {
      source: String,
    },
    data: () => ({
      drawer: null
    }),
    created() {
      this.$vuetify.theme.dark = true;
    },
    methods: {
      goHome() {
        if (this.$route.fullPath !== '/') this.$router.push('/');
      }
    },
    computed: {
      isDark: {
        get() {
          return this.$vuetify.theme.isDark
        },
        set() {
          this.$vuetify.theme.isDark = !this.$vuetify.theme.isDark;
        }
      },
      snackbar() {
        return this.$store.state.snackbar;
      }
    }
  }
</script>
