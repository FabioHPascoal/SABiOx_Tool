<template>
  <div class="fullscreen flex flex-center bg-grey-2">
    <q-card class="q-pa-md" style="width: 400px; max-width: 90vw;">
      <q-card-section>
        <div class="text-h6">Login</div>
      </q-card-section>

      <q-card-section>
        <q-form @submit.prevent="onSubmit">
          <q-input
            v-model="user.email"
            label="Email *"
            type="email"
            outlined
            dense
            class="q-mb-sm"
            required
          />
          <q-input
            v-model="user.password"
            :rules="userRules.password"
            :type="showPassword ? '' : 'password'"
            label="Password *"
            outlined
            dense
            class="q-mb-md"
          />
          <q-btn 
            type="submit"
            label="Enter"
            :disable="submitLoading"
            color="primary"
            class="full-width"
            no-caps
          />
        </q-form>
      </q-card-section>

      <q-card-actions align="center">
        <q-btn
          flat label="Don't have an account? Sign up" 
          to="/register" 
          color="primary"
          class="text-none"
          no-caps
        />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script>
import { defineComponent, ref, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import { useAuthStore } from 'src/stores/auth'

import { minLengthRule, requiredRule, validEmailRule } from 'src/utils/rules'

const DEFAULT_USER = {
  email: '',
  password: ''
}

const useUserRules = () => {
  return {
    email: [requiredRule(), validEmailRule()],
    password: [
      requiredRule(),
      minLengthRule(8, 'Password must have more than 8 characters')
    ]
  }
}

export default defineComponent({
  name: 'LoginPage'
})
</script>

<script setup>

const q = useQuasar()
const route = useRoute()
const router = useRouter()

const authStore = useAuthStore()

const user = reactive({ ...DEFAULT_USER })

const { email } = route.query
if (validEmailRule()(email) === true) user.email = email

const showPassword = ref(false)

const userRules = reactive(useUserRules(user))

const submitLoading = ref(false)
const onSubmit = async () => {
  try {
    submitLoading.value = true

    const data = await authStore.attemptLogin(user)

    q.notify({
      type: 'positive',
      message: `Welcome, ${data.user.name}.`
    })

    if (route.query.redirect) {
      await router.push({ path: route.query.redirect })
    } else {
      await router.push({
        name: 'App.Home'
      })
    }
  } finally {
    submitLoading.value = false
  }
}

</script>