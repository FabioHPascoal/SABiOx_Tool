<template>
  <page-title title="Elicit Requirements" />

  <div class="q-pa-md">
    <div class="q-mb-md flex justify-between">
      <q-btn
        class="outlined-btn"
        label="Add Requirement"
        icon="add"
        padding="sm"
        no-caps
        @click="showDialog = true"
      />
      <q-btn-toggle
        class="custom-card"
        v-model="filterMode"
        no-caps
        toggle-color="primary"
        color="white"
        text-color="primary"
        padding="sm md"
        :options="[
          { label: 'All', value: FILTER_MODE_ALL},
          { label: 'Funcional', value: FILTER_MODE_FUNCTIONAL },
          { label: 'Non-funcional', value: FILTER_MODE_NON_FUNCTIONAL },
          { label: 'Domain', value: FILTER_MODE_DOMAIN }
        ]"
      />
    </div>

    <transition-group name="fade-move" tag="div" class="column q-gutter-y-md q-mx-none">
      <expansible-card
        v-for="requirement in filteredRequirements"
        :key="requirement.id"
        :title="requirement.description"
        :badge-label="getBadgeLabel(requirement.requirementType)"
        :badge-color="getBadgeColor(requirement.requirementType)"
        :user="requirement.user"
        :creation-date="requirement.creationDate"
      >
        <q-card-section
          v-for="comment in requirement.comments"
          :key="comment.id"
          class="q-pa-sm bg-grey-4"
        >
          <div class="row q-gutter-md">
            <!-- COLUNA ESQUERDA -->
            <div class="col-auto text-center comment-sidebar">
              <q-avatar size="42px" class="q-mb-xs">
                <img :src="comment.user.avatarUrl" :alt="comment.user.name" />
              </q-avatar>
              <div class="text-caption text-weight-medium">{{ comment.user.name }}</div>
              <div class="text-caption text-grey-7 q-mt-xs">
                {{ formatDate(comment.creationDate) }}
              </div>

              <div class="q-mt-sm">
                <q-btn
                  flat
                  dense
                  icon="thumb_up"
                  size="sm"
                  color="primary"
                  :disable="true"
                />
                <span class="text-caption text-grey-8">{{ comment.likeCount }}</span>
              </div>

              <div>
                <q-btn
                  flat
                  dense
                  icon="thumb_down"
                  size="sm"
                  color="negative"
                  :disable="true"
                />
                <span class="text-caption text-grey-8">{{ comment.dislikeCount }}</span>
              </div>
            </div>

            <!-- COLUNA DIREITA -->
            <div class="col comment-body text-body2">
              {{ comment.body }}
            </div>
          </div>

          <!-- Separador entre comentários -->
          <q-separator
            v-if="requirement.comments.indexOf(comment) !== requirement.comments.length - 1"
            class="q-my-sm"
            color="border"
          />
        </q-card-section>

      </expansible-card>
    </transition-group>

    <q-dialog v-model="showDialog" persistent>
      <q-card style="min-width: 400px">
        <q-card-section class="text-h6">
          Add Requirement
        </q-card-section>

        <q-separator />

        <q-card-section class="q-gutter-md">
          <q-input
            v-model="form.description"
            label="Description"
            type="textarea"
            outlined
            autogrow
            dense
          />

        <q-select
          v-model="form.requirementType"
          label="Requirement Type"
          outlined
          dense
          emit-value
          map-options
          :options="[
            { label: 'Functional', value: 'FUNCTIONAL' },
            { label: 'Non-functional', value: 'NON_FUNCTIONAL' },
            { label: 'Domain', value: 'DOMAIN' }
          ]"
        />
        </q-card-section>

        <q-separator />

        <q-card-actions align="right">
          <q-btn flat label="Cancel" color="grey" v-close-popup />
          <q-btn
            label="Add"
            color="positive"
            :loading="saving"
            @click="addRequirement"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>

  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useProjectStore } from 'src/stores/project'
import { api } from 'boot/axios'
import { useQuasar } from 'quasar'

const $q = useQuasar()
const projectStore = useProjectStore()

const requirements = ref([])
const loading = ref(false)
const saving = ref(false)
const showDialog = ref(false)
const form = ref({ description: '', requirementType: '' })

const lifeCycle = computed(() => projectStore.getSelectedLifeCycle)

const fetchRequirements = async () => {
  if (!lifeCycle.value?.id) return
  loading.value = true
  try {
    const { data } = await api.get(`/project/life_cycle/${lifeCycle.value.id}/elicit_requirements`)
    requirements.value = data?.requirements || []
  } catch (err) {
    console.error('Failed to load requirements:', err)
    $q.notify({ type: 'negative', message: 'Failed to load requirements' })
  } finally {
    loading.value = false
  }
}

watch(() => lifeCycle.value?.id, (id) => {
  if (id) fetchRequirements()
})
onMounted(() => fetchRequirements())

const addRequirement = async () => {
  if (!form.value.description?.trim()) {
    $q.notify({ type: 'warning', message: 'Description is required' })
    return
  }

  if (!lifeCycle.value?.id) return

  saving.value = true
  try {
    const payload = { ...form.value }
    await api.post(`/project/life_cycle/${lifeCycle.value.id}/requirement`, payload)
    await fetchRequirements()
    await projectStore.fetchPhases()

    $q.notify({ type: 'positive', message: 'Requirement added successfully' })
    showDialog.value = false
    form.value = { title: '', description: '' }
  } catch (err) {
    console.error('Erro ao adicionar subdomínio:', err)
    $q.notify({ type: 'negative', message: 'Failed to add requirement' })
  } finally {
    saving.value = false
  }
}

const getBadgeLabel = (type) => {
  switch (type) {
    case 'FUNCTIONAL': return 'Functional'
    case 'NON_FUNCTIONAL': return 'Non-functional'
    case 'DOMAIN': return 'Domain'
    default: return 'Unknown'
  }
}

const getBadgeColor = (type) => {
  switch (type) {
    case 'FUNCTIONAL': return 'green'
    case 'NON_FUNCTIONAL': return 'orange'
    case 'DOMAIN': return 'blue'
    default: return 'grey'
  }
}

const FILTER_MODE_ALL = 'all'
const FILTER_MODE_FUNCTIONAL = 'functional'
const FILTER_MODE_NON_FUNCTIONAL = 'non_functional'
const FILTER_MODE_DOMAIN = 'domain'

const filterMode = ref(FILTER_MODE_ALL)

const filteredRequirements = computed(() => {
  if (filterMode.value === FILTER_MODE_ALL) {
    return requirements.value
  }
  if (filterMode.value === FILTER_MODE_FUNCTIONAL) {
    return requirements.value.filter(r => r.requirementType === 'FUNCTIONAL')
  }
  if (filterMode.value === FILTER_MODE_NON_FUNCTIONAL) {
    return requirements.value.filter(r => r.requirementType === 'NON_FUNCTIONAL')
  }
  if (filterMode.value === FILTER_MODE_DOMAIN) {
    return requirements.value.filter(r => r.requirementType === 'DOMAIN')
  }
  return null
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

</script>

<style scoped>
.outlined-btn {
border: 2px solid var(--q-border);
border-radius: 8px;
}
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

.comment-section {
  border: 1px solid var(--q-border);
  border-radius: 8px;
}

.comment-sidebar {
  width: 120px;
  min-width: 120px;
  background-color: #f9f9f9;
  border-right: 1px solid var(--q-border);
  padding: 8px;
  border-radius: 8px 0 0 8px;
}

.comment-body {
  white-space: pre-wrap;
  word-break: break-word;
}

.q-btn[disabled] {
  opacity: 0.7;
  cursor: default !important;
}

</style>
