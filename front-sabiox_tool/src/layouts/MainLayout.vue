<!-- <template>
  <q-layout view="hHr lpR lFf">
    <desktop-header v-model:app-drawer="appDrawerVal" />

    <app-drawer v-model="appDrawerVal" :links="linksMain"/>

    <project-drawer v-if="isProject"/>

    <q-page-container>
      <div class="column fit">
        <project-header v-if="isProject"/>
        
        <div class="col">
          <router-view />
        </div>
      </div>
    </q-page-container>
    
  </q-layout>
</template> -->

<template>
  <q-layout view="hHr lpR lFf">
    <desktop-header v-model:app-drawer="appDrawerVal" />
    <app-drawer v-model:modelValue="appDrawerVal" :links="linksMain"/>
    <project-drawer v-if="isProject"/>
    <q-page-container>
      <div class="column fit">
        <project-header v-if="isProject"/>
        <div class="col">
          <div class="row fit">
            <div v-if="isProject" class="bg-background" style="width: 22px;"/>
            <div class="col">
              <router-view />
            </div>
          </div>
        </div>
      </div>
    </q-page-container>
  </q-layout>
</template>

<script>
import { computed, defineComponent, ref } from 'vue'
import { useRoute } from 'vue-router'

export default defineComponent({
  components: { ProjectHeader },
  name: 'MainLayout'
})
</script>

<script setup>
import AppDrawer from './AppDrawer.vue'
import ProjectDrawer from 'src/layouts/ProjectDrawer.vue'
import DesktopHeader from './DesktopHeader.vue'
import ProjectHeader from './ProjectHeader.vue'

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
