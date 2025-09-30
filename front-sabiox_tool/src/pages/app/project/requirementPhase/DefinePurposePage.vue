<template>
  teste
</template>

<script>
import { defineComponent, onMounted, ref } from 'vue'
import { onBeforeRouteUpdate, useRoute } from 'vue-router'
import cloneDeep from 'lodash.clonedeep'
import { api } from 'src/boot/axios'

import {
  DEFAULT_PROJECT
} from 'src/constants/projects'

export default defineComponent({
  name: 'ProjectPage'
})
</script>

<script setup>

const $route = useRoute()

const innerProject = ref(cloneDeep(DEFAULT_PROJECT))

const projectLoading = ref(false)
const getProject = async (projectId) => {
  if (!projectId) return

  try {
    projectLoading.value = true

    const { data } = await api.get(`project/${projectId}`)

    Object.assign(innerProject.value, data)

  } finally {
    projectLoading.value = false
  }
}

// const getProject = async (projectId) => {
//   if (!projectId) return

//   try {
//     projectLoading.value = true

//     const { data } = await api.get(`project/${projectId}`)

//     Object.assign(innerProject.value, data)

//     currentActionPlanId.value = data?.actionPlan?.id
//     currentResponsibleId.value = data?.responsible?.id
//     currentStatus.value = data?.status
//     currentPriority.value = data?.priority
//   } finally {
//     projectLoading.value = false
//   }
// }

const loadProjectData = async (projectId) => {
  await Promise.allSettled([getProject(projectId)])
}

const loadPageData = async (projectId) => {
  await loadProjectData(projectId)
}

onBeforeRouteUpdate(async (to, from) => {
  if (to.params.id === from.params.id) return

  await loadProjectData(to.params.id)
})

onMounted(async () => {
  await loadPageData($route.params.id)
})

</script>