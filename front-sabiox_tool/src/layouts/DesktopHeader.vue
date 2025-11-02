<template>
  <q-header
    style="
      border-bottom: 3px solid var(--q-border);
      background-color: var(--q-background); 
      color: var(--q-onBackground)
    "
  >
    <q-toolbar class="q-pr-none">
      <router-link :to="{ name: 'App.Home' }" class="flex flex-center">
        <app-logo light style="width: 100px" />
      </router-link>

      <q-space />

      <q-card class="q-pa-sm" no-caps flat>
        <q-item dense class="q-pr-xs">
          <q-item-section>
            <q-item-label class="text-weight-bold text-right">
              {{ userName }}
            </q-item-label>

            <q-item-label
              class="cursor-pointer text-negative text-right text-subtitle2 logout-text"
              @click="logout"
            >
              Logout
            </q-item-label>
          </q-item-section>

          <q-item-section side>
            <the-avatar
              :avatar-url="userAvatarUrl"
              default-avatar-icon="account_circle"
              text-color="white"
              size="xl"
            />
          </q-item-section>
        </q-item>
      </q-card>

      <q-btn class="q-mr-md" dense flat round icon="menu" @click="toggleAppDrawer" />
    </q-toolbar>
  </q-header>
</template>

<script>
import { computed, defineComponent } from 'vue'
import { storeToRefs } from 'pinia'
import { useAuthStore } from 'src/stores/auth'

export default defineComponent({
  name: 'DesktopHeader'
})
</script>

<script setup>
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import { api } from 'src/boot/axios'
import AppLogo from 'src/components/common/AppLogo.vue'

const props = defineProps({
  appDrawer: Boolean
})

const emit = defineEmits(['update:app-drawer'])

const router = useRouter()
const $q = useQuasar()
const authStore = useAuthStore()
const { getUser } = storeToRefs(authStore)

const appDrawerVal = computed({
  get: () => props.appDrawer,
  set: (newValue) => emit('update:app-drawer', newValue)
})

const userName = computed(() => getUser.value?.name ?? 'Usuário')
const userAvatarUrl = computed(() => getUser.value?.avatarUrl)

const toggleAppDrawer = () => {
  appDrawerVal.value = !appDrawerVal.value
}

const logout = async () => {
  try {
    const token = localStorage.getItem('access_token')
    if (!token) return

    await api.delete('/auth/logout', {
      headers: { Authorization: `Bearer ${token}` }
    })

    localStorage.removeItem('access_token')
    authStore.logout?.()

    $q.notify({
      type: 'positive',
      message: 'You have been logged out successfully.'
    })

    router.push({ name: 'Login' })
  } catch (err) {
    console.error(err)
    $q.notify({
      type: 'negative',
      message: 'Error logging out. Please try again.'
    })
  }
}
</script>

<style scoped>
.logout-text:hover {
  text-decoration: underline;
}
</style>