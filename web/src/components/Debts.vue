<template>
  <div id="debts" class="blog-post" v-if="debts && debts.length > 0">
    <v-tabs v-model="tab">
      <v-tab v-for="debt in debts" :key="debt.title">
        {{ debt.title }}
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item v-for="debt in debts" :key="debt.tab">
        <v-card flat>
          <div v-if="debt">
            <v-row justify="end" align="center">
              <v-col offset="1">
                <span class="text-h6 blue--text text--darken-3">{{ $t('debts.initialDebt')}}</span>
                <v-chip class="ma-2">
                  {{debt.initialDebt | formatPrice}} Kc
                </v-chip>
              </v-col>
              <v-col>
                <span class="text-h6 blue--text text--darken-3">{{ $t('debts.currentDebt')}}</span>
                <v-chip class="ma-2">
                  {{debt.currentDebt | formatPrice}} Kc
                </v-chip>
              </v-col>
              <v-col>
                <span class="text-h6 blue--text text--darken-3">{{ $t('debts.debtFrom')}}</span>
                <v-chip class="ma-2">
                  {{debt.debtStartDate}}
                </v-chip>
              </v-col>
            </v-row>
            <Payments :debt-id="debt.id" v-on:payment-added="refreshDebt(debt)" payments="payments"/>
          </div>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import Payments from "./Payments";
import DebtRestService from "../services/DebtRestService";

export default {
  name: 'Debts',
  components: { Payments },
  props: ['debts'],
  data () {
    return {
      tab: null,
    }
  },
  methods: {
    refreshDebt(debt) {
      DebtRestService.getDebt(debt.id).then(result => {
        this.debts.find(debt => debt.id = result.data.id).currentDebt = result.data.currentDebt;
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
