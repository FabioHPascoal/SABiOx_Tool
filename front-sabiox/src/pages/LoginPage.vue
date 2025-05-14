<template>
  <div class="fullscreen flex flex-center bg-grey-2">
    <q-card class="q-pa-md" style="width: 400px; max-width: 90vw;">
      <q-card-section>
        <div class="text-h6">Login</div>
      </q-card-section>

      <q-card-section>
        <q-form @submit.prevent="login">
          <q-input
            v-model="loginData.email"
            label="Email"
            type="email"
            outlined
            dense
            class="q-mb-sm"
            required
          />
          <q-input
            v-model="loginData.password"
            label="Password"
            type="password"
            outlined
            dense
            class="q-mb-md"
            required
          />
          <q-btn type="submit" label="Enter" color="primary" class="full-width" />
        </q-form>
      </q-card-section>

      <q-card-actions align="center">
        <q-btn flat label="Don't have an account? Sign up" to="/register" color="primary" />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'

const router = useRouter()
const $q = useQuasar()

const loginData = ref({
  email: '',
  password: ''
})

async function login() {
  try {
    const response = await axios.post('/api/auth/login', loginData.value)
    localStorage.setItem('auth_token', response.data.token)
    router.push('/app')
  } 
  catch (error) {
    console.error('Erro ao fazer login:', error)
    $q.notify({
      type: 'negative',
      message: 'Erro ao fazer login. Verifique suas credenciais.'
    })
  }
}
</script>
