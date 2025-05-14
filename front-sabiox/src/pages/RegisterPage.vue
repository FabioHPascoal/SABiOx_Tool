<template>
  <div class="fullscreen flex flex-center bg-grey-2">
    <q-card class="q-pa-md" style="width: 400px; max-width: 90vw;">
      <q-card-section>
        <div class="text-h6">Registro</div>
      </q-card-section>

      <q-card-section>
        <q-form @submit.prevent="register">
          <q-input
            v-model="form.name"
            label="Nome"
            outlined
            dense
            class="q-mb-sm"
            required
          />
          <q-input
            v-model="form.username"
            label="Username"
            outlined
            dense
            class="q-mb-sm"
            required
          />
          <q-input
            v-model="form.email"
            label="Email"
            type="email"
            outlined
            dense
            class="q-mb-sm"
            required
          />
          <q-input
            v-model="form.password"
            label="Senha"
            type="password"
            outlined
            dense
            class="q-mb-md"
            required
          />
          <q-btn type="submit" label="Registrar" color="primary" class="full-width" />
        </q-form>
      </q-card-section>

      <q-card-actions align="center">
        <q-btn flat label="Já tem conta? Faça login" to="/login" color="primary" />
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

const form = ref({
  name: '',
  username: '',
  email: '',
  password: ''
})

async function register() {
  try {
    const response = await axios.post('/api/auth/register', form.value)
    localStorage.setItem('auth_token', response.data.token)
    router.push('/dashboard')
  } catch (err) {
    $q.notify({
      type: 'negative',
      message: 'Erro ao registrar: ' + (err.response?.data?.error || err.message),
    })
  }
}
</script>
