import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import axios from 'axios';
import VuetifyConfirm from 'vuetify-confirm';
import i18n from './i18n';
import 'vuetify/dist/vuetify.css';
import Vuex from 'vuex';
import store from './store/index';
import VueRouter from 'vue-router'
import PageNotFound404 from '@/components/errorPages/404';
import ServerError500 from '@/components/errorPages/500';
import BadRequest from '@/components/errorPages/400';
import Debts from "@/components/Debts";
import ErrorPage from "@/components/errorPages/ErrorPage";

Vue.config.productionTip = false;
Vue.http = Vue.prototype.$http = axios;
Vue.use(VuetifyConfirm, {vuetify});
Vue.use(require('vue-moment'));
Vue.use(Vuex);
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: Debts
  },
  {
    path: '/400',
    name: '400',
    component: BadRequest,
    props: true
  },
  {
    path: '/404',
    component: PageNotFound404
  },
  {
    path: '/500',
    component: ServerError500
  },
  {
    path: '/error',
    name: 'error',
    component: ErrorPage,
    props: true
  }
]

// 3. Create the router instance and pass the `routes` option
// You can pass in additional options here, but let's
// keep it simple for now.
export const router = new VueRouter({
  mode: 'history',
  routes // short for `routes: routes`
})

new Vue({
  vuetify,
  store,
  router,
  i18n,
  render: h => h(App)
}).$mount('#app');