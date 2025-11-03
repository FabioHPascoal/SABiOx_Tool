import { boot } from 'quasar/wrappers'
import axios from 'axios'
import { Notify } from 'quasar'

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL + '/api', 

  withCredentials: true,
  timeout: 180000,
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'Application/json'
  }
})

export default boot(({ app }) => {
  api.interceptors.request.use((request) => {
    const token = localStorage.getItem('access_token')
    if (token) request.headers.Authorization = `Bearer ${token}`
    return request
  }, error => {
    console.error('REQUEST ERROR', { ...error })

    return Promise.reject(error)
  })

  app.config.globalProperties.$axios = axios
  app.config.globalProperties.$api = api
})

export { api }
