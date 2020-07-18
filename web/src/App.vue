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
                <v-list-item-title @click="createDebt">Add Debt</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item link>
              <v-list-item-action>
                <v-icon>mdi-cog</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>Add Payment</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
<!--            <v-list-item>-->
<!--              <v-btn color="accent" large @click.stop="showDebtDialog = true" />-->
<!--              <CreateDebtDialog v-on:save-debt="createDebt($event)" />-->
<!--            </v-list-item>-->
          </v-list>
        </v-navigation-drawer>

        <v-app-bar app clipped-left>
          <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
          <v-toolbar-title>{{ $t('title')}}</v-toolbar-title>
          <v-spacer></v-spacer>
          <CreateDebtDialog v-on:save-debt="createDebt($event)" />
          <v-switch class="pa-0" label="" v-model="isDark"></v-switch>
          <LocaleSwitch/>
        </v-app-bar>

        <v-main>
          <v-container fluid>
            <v-row no-gutters>
              <v-col>
                <Debts :debts="debts"/>
              </v-col>
            </v-row>
          </v-container>
        </v-main>
    </v-app>
  </div>
</template>

<script>
  import Vuetify from "vuetify";
  import Debts from "./components/Debts";
  import DebtRestService from "./services/DebtRestService";
  import CreateDebtDialog from "./components/dialogs/CreateDebtDialog";
  import LocaleSwitch from "./components/LocaleSwitch";

  export default {
    components: { LocaleSwitch, CreateDebtDialog, Debts },
    vuetify: new Vuetify(),
    props: {
      source: String,
    },
    data: () => ({
      drawer: null,
      debts: null
    }),
    created() {
      this.$vuetify.theme.dark = true;
    },
    mounted() {
      this.getDebts();
    },
    methods: {
      createDebt(debt) {
        DebtRestService.createDebt(debt).then(response => {
          if (response) {
            this.getDebts();
          }
        }).catch(error => {
          console.log(error);
        });
      },
      getDebts() {
        DebtRestService.getDebts().then(response => {
          if (response.status === 200) {
            this.debts = response.data;
          }
        });
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
      }
    }
  }
</script>
