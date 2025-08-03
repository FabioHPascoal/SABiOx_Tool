<template>
  <q-header style="
    border-bottom: 2px solid var(--q-border);
    background-color: var(--q-background); 
    color: var(--q-onBackground)">
    <q-toolbar class="q-pr-none">
      <!-- <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" /> -->
      <router-link :to="{ name: 'App.Home' }" class="flex flex-center">
        <app-logo light style="width: 100px" />
      </router-link>
      <q-space />
      <q-btn
        padding="md"
        no-caps
        flat>
        <q-item dense class="q-pr-xs">
          <q-item-section>
            <q-item-label class="text-weight-medium text-right">
              {{ userName }}
            </q-item-label>
          </q-item-section>
        </q-item>
      </q-btn>
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
import AppLogo from 'src/components/common/AppLogo.vue'

// const props = defineProps({
//   leftDrawer: Boolean
// })

// const emit = defineEmits(['update:left-drawer'])

const authStore = useAuthStore()
const { getUser } = storeToRefs(authStore)

// const leftDrawerVal = computed({
//   get: () => props.leftDrawer,
//   set: (newValue) => emit('update:left-drawer', newValue)
// })

const userName = computed(() => getUser.value?.name ?? 'Usuário')

// const toggleLeftDrawer = () => {
//   leftDrawerVal.value = !leftDrawerVal.value
// }

</script>
