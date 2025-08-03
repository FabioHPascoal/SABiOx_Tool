import { route } from 'quasar/wrappers'
import { createRouter, createWebHistory } from 'vue-router'
import routes from './routes'

export default route(function () {
  const Router = createRouter({
    history: createWebHistory(),
    routes
  })

  // Middleware de autenticação
  Router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('auth_token')
    if (to.matched.some(record => record.meta.requiresAuth) && !token) {
      next('/login')
    } else {
      next()
    }
  })

  return Router
})
