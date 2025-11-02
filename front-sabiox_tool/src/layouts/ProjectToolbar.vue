<template>
  <div
    class="q-py-sm row no-wrap justify-center items-center q-gutter-md"
    style="border-bottom: 3px solid var(--q-border)"
  >
    <q-select
      v-model="projectStore.selectedPhaseType"
      :options="phaseOptions"
      label="Phase"
      dense
      outlined
      style="min-width: 180px;"
    />

    <q-select
      v-model="projectStore.selectedLifeCycleIndex"
      :options="lifeCycleOptions"
      label="Life Cycle"
      dense
      outlined
      style="min-width: 160px;"
      emit-value
      map-options
    />

    <q-btn
      v-for="action in actions"
      class="q-mx-xs"
      :key="action.label"
      :ripple="false"
      dense
      unelevated
      :toggle="action.toggleble"
      v-model="action.model"
      :color="getButtonColor(action)"
      @click="action.onClick(action)"
    >
      <q-icon
        :name="action.icon"
        :color="getIconColor(action)"
      />
      <q-tooltip>{{ action.label }}</q-tooltip>
    </q-btn>
  </div>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import { useProjectStore } from 'src/stores/project'

const props = defineProps({ kanbanBar: Boolean })
const emit = defineEmits(['update:kanbanBar'])
const projectStore = useProjectStore()

// const phaseOptions = ['REQUIREMENTS', 'SETUP', 'CAPTURE', 'DESIGN', 'IMPLEMENTATION']
const phaseOptions = ['REQUIREMENTS']

const lifeCycleOptions = computed(() => {
  const phase = projectStore.phases.find(p => p.phaseType === projectStore.selectedPhaseType)
  if (!phase || !phase.lifeCycles?.length) return []
  return phase.lifeCycles.map((_, index) => ({
    label: `#${index + 1}`,
    value: index
  }))
})

const actions = reactive([
  {
    label: 'Toggle Kanban View',
    icon: 'visibility',
    toggleble: true,
    model: props.kanbanBar,
    onClick: (action) => emit('update:kanbanBar', !action.model)
  }
])

watch(() => props.kanbanBar, val => (actions[0].model = val))

const getButtonColor = (action) => (action.model ? 'btnPressed' : 'bg-Background')
const getIconColor = (action) => (action.model ? 'onBackground' : 'accent')

</script>
