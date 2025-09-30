<template>
  <page-title title="Project Manager" />
  <q-page padding :class="{ 'q-pt-none': $q.screen.gt.sm }">
    <div class="q-mb-md q-gutter-sm">
      <q-btn
        @click="projectsTable.openNewProjectDialog"
        label="Create project"
        icon="add_circle_outline"
        padding="sm md"
        no-caps
      />
      <q-btn-toggle
        v-model="filterMode"
        no-caps
        toggle-color="primary"
        color="white"
        text-color="primary"
        padding="sm md"
        :options="[
          { label: 'All', value: FILTER_MODE_ALL },
          { label: 'Owned', value: FILTER_MODE_OWNED },
          { label: 'Shared', value: FILTER_MODE_SHARED }
        ]"
      />
    </div>

    <project-list :projects-composable="{ 
      ...projectsTable, 
      rows: filteredRows 
    }"/>

  </q-page>
</template>

<script>
import { computed, defineComponent, onMounted, reactive, ref, watch } from 'vue'
import { useQuasar } from 'quasar'

import { api } from 'src/boot/axios'
import { useTable } from 'src/composables/table'

import NewProjectDialog from 'src/components/dialogs/projects/CreateEditProjectDialog.vue'

const useProjectsTable = () => {
  const $q = useQuasar()
  
  const { rows, isLoading, onRequest } = useTable(async () => {
    const { data } = await api.get('user/projects')
    return data
  })

  const openNewProjectDialog = () => {
    let created = false

    $q.dialog({
      component: NewProjectDialog,
      componentProps: {
        onCreate: () => {
          created = true
        }
      }
    })
      .onDismiss(async () => {
        if (created) await onRequest()
      })
  }

  return {
    rows,
    onRequest,
    isLoading,
    openNewProjectDialog
  }
}

export default defineComponent({
  name: 'ProjectManagerPage'
})
</script>

<script setup>
import ProjectList from 'src/components/projects/ProjectsList.vue'

const projectsTable = reactive(useProjectsTable())

const FILTER_MODE_ALL = 'all'
const FILTER_MODE_OWNED = 'owned'
const FILTER_MODE_SHARED = 'shared'

const filterMode = ref(FILTER_MODE_ALL)

const filteredRows = computed(() => {
  if (filterMode.value === FILTER_MODE_ALL) {
    return projectsTable.rows
  }
  if (filterMode.value === FILTER_MODE_OWNED) {
    return projectsTable.rows.filter(p => p.role === 'OWNER')
  }
  if (filterMode.value === FILTER_MODE_SHARED) {
    return projectsTable.rows.filter(p => p.role === 'MEMBER')
  }
  return null
})

const setHash = (hash) => {
  window.location.hash = hash
}

watch(filterMode, (val) => {
  setHash(val)
})

onMounted(async () => {
  const hash = window.location.hash.replace('#', '')

  if (hash === FILTER_MODE_ALL || hash === FILTER_MODE_OWNED || hash === FILTER_MODE_SHARED) {
    filterMode.value = hash
  }

  await projectsTable.onRequest()
})

</script>

<!-- <script setup>
import { ref, onMounted } from 'vue'
import { useQuasar } from 'quasar'
import { api } from 'src/boot/axios'
import { useAuthStore } from 'stores/auth'

const $q = useQuasar()
const authStore = useAuthStore()

const projects = ref([])
const dialogVisible = ref(false)
const saving= ref(false)

const form = ref({
  title: '',
  description: ''
})

// Carrega projetos ao montar o componente
async function loadProjects() {
  try {
    const response = await api.get('/user/projects')
    projects.value = response.data
  } catch (error) {
    console.error('Error loading projects:', error)
    $q.notify({ type: 'negative', message: 'Error loading projects' })
  }
}

// Cria novo projeto
async function criarProjeto() {
  saving.value = true
  try {
    const payload = {
      ...form.value,
      userEmail: authStore.getUser?.userEmail
    }

    const response = await api.post('/project', payload)
    projects.value.push(response.data)

    $q.notify({ type: 'positive', message: 'Project created' })

    // Resetar
    dialogVisible.value = false
    form.value.title = ''
    form.value.description = ''
  } catch (error) {
    console.error('Error creating project', error)
    $q.notify({ type: 'negative', message: 'Error creating project' })
  } finally {
    saving.value = false
  }
}

// Placeholder para ação ao clicar em um projeto
function abrirProjeto(projeto) {
  console.log('Abrir projeto:', projeto)
  // Ex: this.$router.push(`/projects/${projeto.id}`)
}

onMounted(() => {
  loadProjects()
})
</script> -->
