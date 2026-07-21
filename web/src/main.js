import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import confirm from './plugins/confirm'
import i18n from './i18n'
import store from './store'
import router from './router'
import '@mdi/font/css/materialdesignicons.css'

const app = createApp(App)

// Global formatters replacing the Vue 2 filters (removed in Vue 3).
app.config.globalProperties.$formatPrice = (value) => {
    if (value === null || value === undefined || value === '') return ''
    const val = (value / 1).toFixed(0)
    return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ' ')
}
app.config.globalProperties.$formatDate = (date) => {
    if (!date) return ''
    const [year, month, day] = date.split('-')
    return `${day}-${month}-${year}`
}

app.use(vuetify)
app.use(store)
app.use(router)
app.use(i18n)
app.use(confirm)

app.mount('#app')
