import { useQuasar } from 'quasar'
import { useAuthStore } from 'src/stores/auth'
import { computed, onMounted, ref } from 'vue'

export default function () {
  const $q = useQuasar()

  const authStore = useAuthStore()

  const appDrawer = ref($q.screen.gt.sm)
  const appDrawerVal = computed({
    get: () => appDrawer.value,
    set: (newValue) => { appDrawer.value = newValue }
  })

  onMounted(async () => {
    await authStore.loadUser()
  })

  return {
    appDrawer,
    appDrawerVal
  }
}
