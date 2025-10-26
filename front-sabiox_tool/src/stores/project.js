import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'
import { api } from 'src/boot/axios'

export const useProjectStore = defineStore('project', () => {
  const project = ref(null)
  const phases = ref([])
  const loading = ref(false)

  const selectedPhaseType = ref('REQUIREMENTS')
  const selectedLifeCycleIndex = ref(null)

  watch(phases, (newPhases) => {
    const phase = newPhases.find(p => p.phaseType === selectedPhaseType.value)
    if (phase && phase.lifeCycles?.length) {
      selectedLifeCycleIndex.value = phase.lifeCycles.length - 1
    } else {
      selectedLifeCycleIndex.value = null
    }
  }, { immediate: true })

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

  const fetchPhases = async (projectId) => {
    if (!projectId) return
    loading.value = true
    try {
      const { data } = await api.get(`project/${projectId}/phase`)
      phases.value = data
    } finally {
      loading.value = false
    }
  }

  const clearProject = () => {
    project.value = null
    phases.value = []
    selectedPhaseType.value = 'REQUIREMENTS'
    selectedLifeCycleIndex.value = 0
  }

  const getSelectedLifeCycle = computed(() => {
    const phase = phases.value.find(p => p.phaseType === selectedPhaseType.value)
    if (!phase || !phase.lifeCycles?.length) return null
    console.log('phase: ',phase)
    console.log('life cycle idx: ', selectedLifeCycleIndex.value)
    return phase.lifeCycles[selectedLifeCycleIndex.value] || null
  })

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
    phases,
    loading,
    selectedPhaseType,
    selectedLifeCycleIndex,
    fetchProject,
    fetchPhases,
    clearProject,
    updateActivityStage,
    activitiesList,
    getSelectedLifeCycle
  }
})
