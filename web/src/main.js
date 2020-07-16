import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import axios from 'axios';
import VuetifyConfirm from 'vuetify-confirm';
import i18n from './i18n'

Vue.use(VuetifyConfirm, {vuetify});

Vue.config.productionTip = false;
Vue.http = Vue.prototype.$http = axios;
Vue.use(require('vue-moment'));

new Vue({
  vuetify,
  i18n,
  render: h => h(App)
}).$mount('#app');
