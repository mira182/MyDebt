export default {
    namespaced: true,
    state: {
        text: "",
        color: ""
    },
    mutations: {
        showMessage (state, payload) {
            state.message = payload.message
            state.color = payload.color
        },
    }
};