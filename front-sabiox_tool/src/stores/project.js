import { defineStore } from 'pinia'
import { ref } from 'vue'
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

  return {
    project,
    loading,
    fetchProject,
    clearProject
  }
})
