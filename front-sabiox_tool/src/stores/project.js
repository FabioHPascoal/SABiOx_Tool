import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'
import { api } from 'src/boot/axios'

export const useProjectStore = defineStore('project', () => {
  const project = ref(null)
  const phases = ref([])
  const loading = ref(false)

  const selectedPhaseType = ref('REQUIREMENTS')
  const selectedLifeCycleIndex = ref(null)

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

  const fetchPhases = async () => { 
    if (!project?.value.projectId) return 
    loading.value = true 
    try { 
      const { data } = await api.get(`project/${project.value.projectId}/phase`)
      phases.value = data } 
      finally { 
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
    return phase.lifeCycles[selectedLifeCycleIndex.value] || null
  })

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
    activitiesList,
    getSelectedLifeCycle
  }
}, {
  persist: {
    paths: ['selectedPhaseType', 'selectedLifeCycleIndex']
  }
})
