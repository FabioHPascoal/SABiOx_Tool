<template>
  <page-title title="Life Cycle Manager" />

  <div class="q-pa-md column q-gutter-md">
    <!-- <expansible-card
      v-for="phase in projectStore.phases"
      :key="phase.phaseId"
      :title="formatPhaseTitle(phase.phaseType)"
    > -->
    <expansible-card
      v-for="phase in projectStore.phases.slice(0, 1)"
      :key="phase.phaseId"
      :title="formatPhaseTitle(phase.phaseType)"
    >
      <div class="q-mb-md">
        <q-btn
          class="btn q-px-none"
          label="Create Life Cycle"
          icon="add_circle_outline"
          color="primary"
          no-caps
          flat
          @click="createLifeCycle(phase.phaseId)"
        />
      </div>

      <div v-for="(lifecycle, index) in phase.lifeCycles" :key="lifecycle.id" class="q-mb-md">
        <q-card flat bordered class="q-pa-md bg-surface">
          <div class="text-h6 q-mb-sm">
            Life Cycle #{{ index + 1 }}
          </div>
          <div class="text-caption text-grey">
            Started: {{ lifecycle.startDate }}
          </div>

          <div class="q-mt-md">
            <q-markup-table flat bordered dense>
              <thead>
                <tr>
                  <th>Activity</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(activity, key) in orderedActivities(lifecycle.activities)"
                  :key="key"
                >
                  <td>{{ formatActivityLabel(activity.key) }}</td>
                  <td>
                    <q-badge
                      :color="stageColor(activity.stage)"
                      align="top"
                      class="q-pa-sm text-white"
                    >
                      {{ formatStage(activity.stage) }}
                    </q-badge>
                  </td>
                </tr>
              </tbody>
            </q-markup-table>
          </div>
        </q-card>
      </div>
    </expansible-card>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useProjectStore } from 'src/stores/project'
import { api } from 'src/boot/axios'
import { useQuasar } from 'quasar'

const $q = useQuasar()
const projectStore = useProjectStore()

onMounted(async () => {
  const projectId = projectStore.project?.id
  if (projectId) {
    await projectStore.fetchPhases()
  }
})

const orderedActivities = (activities = {}) =>
  Object.entries(activities)
    .map(([key, val]) => ({ key, ...val }))
    .sort((a, b) => a.id - b.id)

const stageColor = (stage) => {
  switch (stage) {
    case 'NOT_STARTED': return 'grey'
    case 'IN_PROGRESS': return 'amber'
    case 'DOCUMENTATION': return 'blue'
    case 'CONTROL': return 'blue'
    case 'EVALUATE': return 'blue'
    case 'COMPLETED': return 'green'
    default: return 'grey'
  }
}

const formatStage = (stage) =>
  stage.toLowerCase().replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase())

const formatPhaseTitle = (phaseType) =>
  phaseType.toLowerCase().replace(/\b\w/g, l => l.toUpperCase())

const formatActivityLabel = (key) => {
  const map = {
    REQ_PURP: 'Define Purpose',
    REQ_ELIC: 'Elicit Requirements',
    REQ_DOMN: 'Define Domain',
    REQ_SUBD: 'Define Subdomains'
  }
  return map[key] || key
}

const createLifeCycle = async (phaseId) => {
  const projectId = projectStore.project?.projectId
  if (!projectId) {
    $q.notify({ type: 'negative', message: 'No project selected.' })
    return
  }

  const phase = projectStore.phases.find(p => p.phaseId === phaseId)
  if (!phase) {
    $q.notify({ type: 'negative', message: 'Phase not found.' })
    return
  }

  try {
    $q.loading.show({ message: 'Creating new life cycle...' })

    await api.post(`/project/${projectId}/lifeCycle`, {
      phaseType: phase.phaseType
    })

    await projectStore.fetchPhases()

    $q.notify({
      type: 'positive',
      message: `Life cycle created successfully for ${phase.phaseType}!`
    })
  } catch (error) {
    console.error('Error creating life cycle:', error)
    $q.notify({
      type: 'negative',
      message: error.response?.data?.message || 'Failed to create life cycle.'
    })
  } finally {
    $q.loading.hide()
  }
}
</script>

<style scoped>
.bg-surface {
  background-color: var(--q-surface);
}
</style>