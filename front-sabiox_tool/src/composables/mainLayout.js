import { useQuasar } from 'quasar'
import { useAuthStore } from 'src/stores/auth'
import { computed, onMounted, ref } from 'vue'

export default function () {
  const $q = useQuasar()

  const authStore = useAuthStore()

  const leftDrawer = ref($q.screen.gt.sm)
  const leftDrawerVal = computed({
    get: () => leftDrawer.value,
    set: (newValue) => { leftDrawer.value = newValue }
  })

  onMounted(async () => {
    await authStore.loadUser()
  })

  return {
    leftDrawer,
    leftDrawerVal
  }
}
