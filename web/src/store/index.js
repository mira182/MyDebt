import { createStore } from 'vuex'

export default createStore({
    state: {
        snackbar: {},
        // Populated by the axios error interceptor before routing to an error page.
        error: { errorMessage: '', requestData: '' }
    },
    mutations: {
        SET_SNACKBAR(state, snackbar) {
            state.snackbar = snackbar;
        },
        SET_ERROR(state, error) {
            state.error = { errorMessage: '', requestData: '', ...error };
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
