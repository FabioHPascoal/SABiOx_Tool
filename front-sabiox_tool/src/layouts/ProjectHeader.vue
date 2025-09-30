<template>
    <project-toolbar v-model:projectKanban="projectKanbanVal"/>
    <project-kanban v-model:modelValue="projectKanbanVal"/>
</template>

<script>
import { defineComponent, ref } from 'vue'

export default defineComponent({
  name: 'ProjectHeader'
})
</script>

<script setup>
  import ProjectKanban from './ProjectKanban.vue'
  import ProjectToolbar from './ProjectToolbar.vue'

  const projectKanbanVal = ref(true)
</script>