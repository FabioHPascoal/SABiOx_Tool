<template>
  <page-title title="Define Purpose" />
  <div class="q-pa-md">
    <div v-if="projectStore.loading">Carregando...</div>
    <div v-else-if="projectStore.project">
      <p>Projeto atual: <strong>{{ projectStore.project.title }}</strong></p>
    </div>
  </div>
</template>

<script setup>
import { useProjectStore } from 'src/stores/project'
const projectStore = useProjectStore()
</script>
