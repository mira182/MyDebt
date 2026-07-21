import { createRouter, createWebHistory } from 'vue-router'
import Debts from '@/components/Debts.vue'
import BadRequest from '@/components/errorPages/400.vue'
import PageNotFound404 from '@/components/errorPages/404.vue'
import ServerError500 from '@/components/errorPages/500.vue'
import ErrorPage from '@/components/errorPages/ErrorPage.vue'

const routes = [
  { path: '/', component: Debts },
  { path: '/400', name: '400', component: BadRequest },
  { path: '/404', component: PageNotFound404 },
  { path: '/500', component: ServerError500 },
  { path: '/error', name: 'error', component: ErrorPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
