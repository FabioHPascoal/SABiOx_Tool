import { boot } from 'quasar/wrappers'
import axios from 'axios'
import { Notify } from 'quasar'

// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const api = axios.create({
  baseURL: new URL('api', import.meta.env.VITE_API_URL).href,

  withCredentials: true,
  timeout: 180000,
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'Application/json'
  }
})

export default boot(({ app }) => {
  // api.interceptors.request.use((request) => {
  //   const token = localStorage.getItem('access_token')

  //   if (token) request.headers.Authorization = `Bearer ${token}`

  //   return request
  api.interceptors.request.use((request) => {
    const token = localStorage.getItem('access_token')
    console.log('Used token:', token)
    if (token) request.headers.Authorization = `Bearer ${token}`
    return request
  }, error => {
    console.error('REQUEST ERROR', { ...error })

    return Promise.reject(error)
  })

  api.interceptors.response.use((response) => {
    return response
  }, (error) => {
    Notify.create({
      type: 'warning',
      message: 'Houve um erro. Tente novamente mais tarde.'
    })

    return Promise.reject(error)
  })

  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
})

export { api }
