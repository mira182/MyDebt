<template>
  <div>
    <div v-if="debts != null && debts.length <= 0">
      <CreateDebtDialog v-on:save-debt="createDebt($event)"/>
    </div>
    <div id="debts" class="blog-post" v-if="debts != null && debts.length > 0">
      <CreateDebtDialog v-on:save-debt="createDebt($event)"/>
      <v-tabs v-model="tab">
        <v-tabs-slider></v-tabs-slider>
        <v-tab v-for="debt in debts" :key="debt.title">
          {{ debt.title }}
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item v-for="debt in debts" :key="debt.tab">
          <v-card flat>
            <div v-if="debt">
              <v-row justify="space-around">
                <v-col>
                  <span class="text-h6 blue--text text--darken-3">{{ $t('debts.initialDebt') }}</span>
                  <v-chip class="ma-2">
                    {{ debt.initialDebt | formatPrice }} Kc
                  </v-chip>
                </v-col>
                <v-col>
                  <span class="text-h6 blue--text text--darken-3">{{ $t('debts.currentDebt') }}</span>
                  <v-chip class="ma-2">
                    {{ debt.currentDebt | formatPrice }} Kc
                  </v-chip>
                </v-col>
                <v-col>
                  <span class="text-h6 blue--text text--darken-3">{{ $t('debts.paidDebt') }}</span>
                  <v-chip class="ma-2">
                    {{ debt.initialDebt - debt.currentDebt | formatPrice }} Kc
                  </v-chip>
                </v-col>
                <v-col>
                  <span class="text-h6 blue--text text--darken-3">{{ $t('debts.debtFrom') }}</span>
                  <v-chip class="ma-2">
                    {{ debt.debtStartDate }}
                  </v-chip>
                </v-col>
                <v-col>
                  <v-btn icon @click="deleteDebt(debt.id, tab)">
                    <v-icon dark>delete</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
              <Payments :debt-id="debt.id" v-on:payment-added="refreshDebt(debt.id)" v-on:payment-deleted="refreshDebt(debt.id)" payments="payments"/>
            </div>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </div>
  </div>
</template>

<script>
import Payments from "./Payments";
import DebtRestService from "../services/DebtRestService";
import CreateDebtDialog from "@/components/dialogs/CreateDebtDialog";
import Vue from "vue";

Vue.filter('formatPrice', function (value) {
  let val = (value/1).toFixed(0);
  return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
});

export default {
  name: 'Debts',
  components: { Payments, CreateDebtDialog },
  data () {
    return {
      tab: null,
      debts: null
    }
  },
  mounted() {
    this.debts = this.getDebts();
  },
  methods: {
    refreshDebt(debtId) {
      DebtRestService.getDebt(debtId).then(result => {
        if (result) {
          this.debts.find(debt => debt.id = result.data.id).currentDebt = result.data.currentDebt;
        }
      });
    },
    createDebt(debt) {
      DebtRestService.createDebt(debt).then(response => {
        if (response) {
          this.getDebts();
        }
      }).catch(() => {
        this.$store.dispatch('setSnackbar', {
          show: true,
          color: 'error',
          message: "Saving debt failed."
        });
      });
    },
    getDebts() {
      DebtRestService.getDebts().then(response => {
        if (response.status === 200) {
          this.debts = response.data;
        }
      });
    },
    deleteDebt(debtId, idx) {
      this.$confirm('Do you really want to delete debt?', {title: 'Warning'}).then(res => {
        if (res) {
          DebtRestService.deleteDebt(debtId).then(deleted => {
              if (deleted) {
                if (this.debts.length === 1) { // if we delete the only tab, none active anymore
                  this.active = null
                } else if (idx < this.active) { // if we delete to the left of active, move one to the left
                  this.active--
                } else if (idx === this.active && idx === this.debts.length - 1) { // if we delete the active and it was the last one, move one to the left, else ignore, as the next one will become the active one
                  this.active--
                }
                this.debts.splice(idx, 1);
              }
          }).catch(() =>
              this.$store.dispatch('setSnackbar', {
                show: true,
                color: 'error',
                message: "Deleting debt failed."
              })
          );
        }
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.one-line {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
