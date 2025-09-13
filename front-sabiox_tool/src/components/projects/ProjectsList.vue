<template>
  <q-table
    @request="projectsComposable.onRequest"
    :rows="projectsComposable.rows"
    :loading="projectsComposable.isLoading"
    grid
    class="the-table"
  >
    <template #item="{ row }">
      <project-card-small :project="row" class="fit" />
    </template>
  </q-table>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'ProjectsList'
})
</script>

<script setup>
import projectCardSmall from 'src/components/projects/ProjectCardSmall.vue'

/* const props = */ defineProps({
  projectsComposable: {
    type: Object,
    required: true
  }
})

</script>
