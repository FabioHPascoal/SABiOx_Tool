<template>
    <project-toolbar v-model:kanbanBar="kanbanBarVal"/>
    <kanban-bar
      v-if="projectStore.phases.length"
      :model-value="kanbanBarVal"
      phase-type="REQUIREMENTS"
    />
</template>

<script>
import { defineComponent, ref } from 'vue'
import { useProjectStore } from 'src/stores/project'

export default defineComponent({
  name: 'ProjectHeader'
})
</script>

<script setup>
  import KanbanBar from './KanbanBar.vue'
  import ProjectToolbar from './ProjectToolbar.vue'

  const projectStore = useProjectStore()
  const kanbanBarVal = ref(true)
</script>