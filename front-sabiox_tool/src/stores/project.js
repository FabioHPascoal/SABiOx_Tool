import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { api } from 'src/boot/axios'

export const useProjectStore = defineStore('project', () => {
  const project = ref(null)
  const loading = ref(false)

  const fetchProject = async (id) => {
    if (!id) return
    loading.value = true
    try {
      const { data } = await api.get(`project/${id}`)
      project.value = data
    } finally {
      loading.value = false
    }
  }

  const clearProject = () => {
    project.value = null
  }
  const updateActivityStage = (activityKey, newStage) => {
    const activities = project.value?.activitiesDTO?.activities
    if (activities && activities[activityKey]) {
      activities[activityKey].stage = newStage
    }
  }

  const activitiesList = computed(() => {
    const acts = project.value?.activitiesDTO?.activities || {}
    return Object.entries(acts).map(([key, val]) => ({
      key,
      id: val.id,
      stage: val.stage
    }))
  })

  return {
    project,
    loading,
    fetchProject,
    clearProject,
    updateActivityStage,
    activitiesList
  }
})
