import { defineStore } from 'pinia'
import { api } from 'src/boot/axios'

export const useAuthStore = defineStore('authStore', {
  state: () => ({
    user: null
  }),
  getters: {
    getUser (state) {
      return state.user
    },
    isAuth () {
      const user = this.getUser
      return !!user
    }
  },
  actions: {
    async attemptLogin (user) {
      const { data } = await api.post('auth/login', user)

      localStorage.setItem('access_token', data.token)
      this.user = data.user

      return data
    },
    async logout () {
      await api.post('auth/logout')

      this.user = null

      localStorage.removeItem('access_token')
    },
    async loadUser () {
      const { data } = await api.get('user')

      Object.assign(this.user, data)

      return this.user
    },
    setUser (user) {
      this.user = user
    }
  },
  persist: {
    paths: ['user']
  }
})
