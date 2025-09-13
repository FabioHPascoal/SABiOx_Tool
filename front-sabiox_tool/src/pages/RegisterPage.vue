<template>
  <q-page class="flex flex-center" :class="[$q.screen.xs ? 'bg-white' : 'bg-primary']" padding>
    <div style="width: 450px">
      <q-card class="bg-white" flat>
        <q-form @submit.prevent="onSubmit">
          <q-card-section>
            <div>
              <q-card-section class="text-h6 text-primary text-weight-bold text-center text-uppercase">
                <app-logo style="width: 200px" />
              </q-card-section>
              <div class="text-subtitle1 text-center">
                Sign up!
              </div>
            </div>
            <div class="row q-col-gutter-md q-mt-sm">
              <div class="col-12 text-center">
                <profile-picture-upload
                  v-model="avatar"
                  outline
                  avatar-size="100px"
                  add-label="Foto"
                />
                <transition
                  enter-active-class="animated fadeIn"
                  leave-active-class="animated fadeOut"
                >
                  <div v-if="avatar" class="text-center">
                    <q-btn @click="removeAvatar" flat dense>
                      <span class="text-underline">Remove profile picture</span>
                    </q-btn>
                  </div>
                </transition>
              </div>
              <div class="col-12">
                <q-input
                  v-model="user.name"
                  :rules="userRules.name"
                  label="Nome *"
                />
              </div>
              <div class="col-12">
                <!-- TODO: change username rules later -->
                <q-input
                  v-model="user.username"
                  :rules="userRules.username"
                  label="Username *"
                />
              </div>
              <div class="col-12">
                <q-input
                  v-model="user.email"
                  :rules="userRules.email"
                  label="E-mail *"
                  type="email"
                />
              </div>
              <div class="col-12">
                <q-input
                  v-model="user.password"
                  label="Senha *"
                  :rules="userRules.password"
                  :type="showPassword ? '' : 'password'"
                >
                  <template #append>
                    <q-btn
                      @click="showPassword = !showPassword"
                      :icon="showPassword ? 'visibility' : 'visibility_off'"
                      round
                      flat
                    />
                  </template>
                </q-input>
              </div>
              <div class="col-12">
                <q-input
                  v-model="user.password_confirmation"
                  label="Repetir Senha *"
                  :rules="userRules.password_confirmation"
                  :type="showPasswordConfirmation ? '' : 'password'"
                >
                  <template #append>
                    <q-btn
                      @click="showPasswordConfirmation = !showPasswordConfirmation"
                      :icon="showPasswordConfirmation ? 'visibility' : 'visibility_off'"
                      round
                      flat
                    />
                  </template>
                </q-input>
              </div>
            </div>
          </q-card-section>
          <q-card-actions>
            <q-btn
              no-caps
              type="submit"
              label="Create account"
              :loading="submitLoading"
              color="primary"
              text-color="onPrimary"
              class="full-width"
              padding="md"
            />
          </q-card-actions>
        </q-form>
      </q-card>
    </div>
  </q-page>
</template>

<script>
import { defineComponent, reactive, ref, computed } from 'vue'
import cloneDeep from 'lodash.clonedeep'
import { useQuasar } from 'quasar'
import { api } from 'src/boot/axios'
import { useRouter } from 'vue-router'
import { useAuthStore } from 'src/stores/auth'

import {
  minLengthRule,
  requiredRule,
  validEmailRule,
  validNameRule,
  validUsernameRule,
  sameValueRule
} from 'src/utils/rules'
import { objectToFormData } from 'src/utils'

export const DEFAULT_USER = {
  name: '',
  username: '',
  email: '',
  password: '',
  password_confirmation: ''
}

const useUserRules = (user) => {
  return {
    name: [requiredRule(), validNameRule()],
    username: [requiredRule(), validUsernameRule()],
    email: [requiredRule(), validEmailRule()],
    password: [
      requiredRule(),
      minLengthRule(8, 'Your password should have at least 8 characters.')
    ],
    password_confirmation: [
      requiredRule(),
      sameValueRule(user.password, 'The passwords must be the same.')
    ]
  }
}

export default defineComponent({
  name: 'RegisterPage'
})
</script>

<script setup>
import AppLogo from 'src/components/common/AppLogo.vue'
import ProfilePictureUpload from 'src/components/common/ProfilePictureUpload.vue'

const $q = useQuasar()
const $router = useRouter()

const authStore = useAuthStore()

const user = reactive(cloneDeep(DEFAULT_USER))

const userRules = computed(() => useUserRules(user))

const showPassword = ref(false)
const showPasswordConfirmation = ref(false)

const avatar = ref(null)
const removeAvatar = () => {
  avatar.value = null
}

const submitLoading = ref(false)
const onSubmit = async () => {
  try {
    submitLoading.value = true

    const { data } = await api.post('auth/register', objectToFormData({
      ...user,
      ...(avatar.value && avatar.value instanceof Blob && { avatar: avatar.value })
    }))

    $q.notify({
      type: 'positive',
      message: `Bem-vindo(a), ${user.name}.`
    })

    localStorage.setItem('access_token', data.token)
    authStore.setUser(data)

    await $router.push({
      name: 'App.Home'
    })
  } finally {
    submitLoading.value = false
  }
}

</script>
