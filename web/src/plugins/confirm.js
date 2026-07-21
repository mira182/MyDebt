import { reactive } from 'vue'

export const confirmState = reactive({
    show: false,
    message: '',
    title: 'Warning',
    resolve: null
})

export function confirm(message, options = {}) {
    confirmState.message = message
    confirmState.title = options.title || 'Warning'
    confirmState.show = true
    return new Promise((resolve) => {
        confirmState.resolve = resolve
    })
}

export function answerConfirm(value) {
    confirmState.show = false
    const resolve = confirmState.resolve
    confirmState.resolve = null
    if (resolve) resolve(value)
}

export default {
    install(app) {
        app.config.globalProperties.$confirm = confirm
    }
}
