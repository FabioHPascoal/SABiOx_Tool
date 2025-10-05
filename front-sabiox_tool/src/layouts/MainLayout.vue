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
            <div v-if="isProject" class="bg-background" style="width: 20px;"></div>
            <div class="col">
              <router-view />
            </div>
          </div>
        </div>
      </div>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, onBeforeRouteUpdate } from 'vue-router'
import { useProjectStore } from 'src/stores/project'

import AppDrawer from './AppDrawer.vue'
import ProjectDrawer from 'src/layouts/ProjectDrawer.vue'
import DesktopHeader from './DesktopHeader.vue'
import ProjectHeader from './ProjectHeader.vue'

const route = useRoute()
const appDrawerVal = ref(false)
const projectStore = useProjectStore()

const isProject = computed(() => route.name?.startsWith('App.Project'))

onMounted(() => {
  if (isProject.value) projectStore.fetchProject(route.params.id)
})

onBeforeRouteUpdate((to, from) => {
  if (to.name?.startsWith('App.Project') && to.params.id !== from.params.id) {
    projectStore.fetchProject(to.params.id)
  }
})

const linksMain = computed(() => [
  {
    name: 'home',
    icon: 'home',
    label: 'Home',
    to: { name: 'App.Home' },
    exact: true
  }
])
</script>
