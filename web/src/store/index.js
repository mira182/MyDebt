import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        snackbar: {}
    },
    mutations: {
        SET_SNACKBAR(state, snackbar) {
            state.snackbar = snackbar;
        }
    },
    actions: {
        setSnackbar({commit}, snackbar) {
            snackbar.show = true;
            snackbar.color = snackbar.color || 'success';
            snackbar.timeout = !snackbar.timeout ? 3000 : snackbar.timeout;
            commit('SET_SNACKBAR', snackbar);
        }
    }
})