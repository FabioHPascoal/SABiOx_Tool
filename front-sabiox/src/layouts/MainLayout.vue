<template>
  <q-layout view="hHh LpR lff">

    <!-- <desktop-header v-if="$q.screen.gt.sm" v-model:left-drawer="leftDrawerVal" :profile-links="profileLinks" />
    <mobile-header v-else v-model:left-drawer="leftDrawerVal" :profile-links="profileLinks" /> -->
    
    <desktop-header/>

    <!-- <app-drawer v-model="leftDrawerVal" :links="links" /> -->

    <q-page-container>
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<script>
import { defineComponent } from 'vue'

// import { storeToRefs } from 'pinia'
// import { useAuthStore } from 'src/stores/auth'
// import useMainLayout from 'src/composables/mainLayout'

export default defineComponent({
  name: 'MainLayout'
})
</script>

<script setup>

// import AppDrawer from './AppDrawer.vue'
// import MobileHeader from './MobileHeader.vue'
import DesktopHeader from './DesktopHeader.vue'

// const { leftDrawerVal } = useMainLayout()

// const authStore = useAuthStore()

// const links = computed(() => {
//   const allDrawerLinks = [
//     {
//       name: 'requirements',
//       label: 'Requirements',
//       to: { name: 'App.Project.Requirements' },
//     },
//     {
//       name: 'setup',
//       label: 'Setup',
//       to: { name: 'App.Project.Setup' }
//     },
//     {
//       name: 'capture',
//       label: 'Capture',
//       to: { name: 'App.Project.Capture' }
//     },
//     {
//       name: 'design',
//       label: 'Design',
//       to: { name: 'App.Project.Design' }
//     },
//     {
//       name: 'implementation',
//       label: 'Implementation',
//       to: { name: 'App.Project.Implementation' },
//     }
//   ]

//   return allDrawerLinks.filter((link) => {
//     return typeof link.showIf === 'function'
//       ? link.showIf()
//       : typeof link.showIf === 'undefined' || !!link.showIf
//   })
// })

</script>
