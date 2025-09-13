import { ref } from 'vue'

export const useTableFilters = (onRequest) => {
  const currentFilter = ref({})

  const filterTable = async (filter) => {
    if (typeof filter === 'object') currentFilter.value = { ...filter }

    await onRequest()
  }

  return { currentFilter, filterTable }
}

export const useTable = (onRequestCb) => {
  const rows = ref([])

  const isLoading = ref(false)

  const onRequest = async () => {
    try {
      isLoading.value = true

      const data = await onRequestCb()

      rows.value = data
    } finally {
      isLoading.value = false
    }
  }

  return {
    rows,
    isLoading,
    onRequest
  }
}
