<!-- <template>
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
</template> -->

<template>
  <transition-group
    name="fade-move"
    tag="div"
  >
    <project-card-small
      v-for="project in projectsComposable.rows"
      :key="project.projectId"
      :project="project"
    />
  </transition-group>
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

<style scoped>
  .fade-move-enter-active,
  .fade-move-leave-active {
    transition: all 0.4s ease;
  }

  .fade-move-enter-from,
  .fade-move-leave-to {
    opacity: 0;
    transform: translateY(20px);
  }

  .fade-move-move {
    transition: transform 0.4s ease;
  }
</style>
