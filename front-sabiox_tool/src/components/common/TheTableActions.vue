<template>
  <div class="q-gutter-xs justify-end">
    <q-btn
      v-for="action in filteredActions"
      :key="action"
      @click="action.onClick(row)"
      :icon="getIcon(row, action)"
      :label="getLabel(row, action)"
      :disable="isRowLoading(row, action.type) || isRowDisabled(row, action)"
      :to="action.to"
      color="accent"
      stack
      flat
    >
      <q-inner-loading :showing="isRowLoading(row, action.type)" />
    </q-btn>
  </div>
</template>

<script>
import { defineComponent, computed } from 'vue'

export default defineComponent({
  name: 'TheTableActions'
})
</script>

<script setup>

const props = defineProps({
  row: {
    type: Object,
    required: true
  },
  actions: {
    type: Array,
    default: () => []
  }
})

const filteredActions = computed(() => {
  return props.actions.filter((action) => {
    return typeof action.showIf === 'function'
      ? action.showIf(props.row)
      : typeof action.showIf === 'undefined' || !!action.showIf
  })
})

const isRowDisabled = (row, action) => {
  return typeof action.disableIf === 'function'
    ? action.disableIf(row)
    : typeof action.disableIf !== 'undefined' || !!action.disableIf
}

const isRowLoading = (row, action) => {
  return typeof action.loadingIf === 'function'
    ? action.loadingIf(row)
    : typeof action.loadingIf !== 'undefined' || !!action.loadingIf
}

const getLabel = (row, action) => {
  return typeof action.label === 'function'
    ? action.label(row)
    : action.label
}

const getIcon = (row, action) => {
  return typeof action.icon === 'function'
    ? action.icon(row)
    : action.icon
}

</script>
