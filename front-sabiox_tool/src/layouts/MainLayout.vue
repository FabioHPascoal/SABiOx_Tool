<!-- <template>
  <q-layout view="lHh lpR lFf">

    <desktop-header v-if="$q.screen.gt.sm" v-model:left-drawer="leftDrawerVal" :profile-links="profileLinks" />
    <mobile-header v-else v-model:left-drawer="leftDrawerVal" :profile-links="profileLinks" />
    
    <desktop-header v-model:left-drawer="leftDrawerVal" />

    <app-drawer v-model="leftDrawerVal" :links="links" />

    <q-page-container>
      <router-view />
    </q-page-container>

  </q-layout>
</template> -->

<!-- <script>
import { computed, defineComponent } from 'vue'

// import { storeToRefs } from 'pinia'
// import { useAuthStore } from 'src/stores/auth'
import useMainLayout from 'src/composables/mainLayout'

export default defineComponent({
  name: 'MainLayout'
})
</script>

<script setup>
import AppDrawer from './AppDrawer.vue'
// import MobileHeader from './MobileHeader.vue'
import DesktopHeader from './DesktopHeader.vue'

const { leftDrawerVal } = useMainLayout()

// const authStore = useAuthStore()

const linksMain = computed(() => {
  const allDrawerLinks = [
    {
      name: 'home',
      icon: 'home',
      label: 'Home',
      to: { name: 'App.Home' },
      exact: true
    }
  ]

  return allDrawerLinks.filter((link) => {
    return typeof link.showIf === 'function'
      ? link.showIf()
      : typeof link.showIf === 'undefined' || !!link.showIf
  })
})

</script> -->

<template>
  <q-layout view="hHr lpR lFf">
    <desktop-header v-model:app-drawer="appDrawerVal" />

    <app-drawer v-model="appDrawerVal" :links="linksMain"/>

    <project-drawer v-if="isProject"/>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { computed, defineComponent, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

// import { storeToRefs } from 'pinia'
// import { useAuthStore } from 'src/stores/auth'
import useMainLayout from 'src/composables/mainLayout'

export default defineComponent({
  name: 'MainLayout'
})
</script>

<script setup>
import AppDrawer from './AppDrawer.vue'
import ProjectDrawer from 'src/components/projects/ProjectDrawer.vue'
import DesktopHeader from './DesktopHeader.vue'

const route = useRoute()
// const { appDrawerVal } = useMainLayout()
const appDrawerVal = ref(false)

const isProject = computed(() => route.name?.startsWith('App.Project'))

const linksMain = computed(() => {
  const allDrawerLinks = [
    {
      name: 'home',
      icon: 'home',
      label: 'Home',
      to: { name: 'App.Home' },
      exact: true
    }
  ]

  return allDrawerLinks.filter((link) => {
    return typeof link.showIf === 'function'
      ? link.showIf()
      : typeof link.showIf === 'undefined' || !!link.showIf
  })
})
</script>
