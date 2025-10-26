<!-- <template>
  <page-title title="Define Purpose" />

  <div class="q-pa-md">
    
    <div class="q-mb-md q-gutter-sm">

      <q-btn
        class="outlined-btn"
        label="Edit Purpose"
        :icon="matEdit"
        padding="sm md"
        no-caps
      />
      <div class="purpose-card q-pa-md">

        <div class="purpose-step">
          <div class="step-content">
            <div class="step-question">What?</div>
            <div class="step-answer">
              The purpose of the ontology is to represent the concepts of object orientation present in a source code.
            </div>
          </div>
        </div>

        <div class="purpose-step">
          <div class="step-content">
            <div class="step-question">For What?</div>
            <div class="step-answer">
              So that these concepts can be interpreted and identified by different programming languages in a unique way.
            </div>
          </div>
        </div>

        <div class="purpose-step">
          <div class="step-content">
            <div class="step-question">Why?</div>
            <div class="step-answer">
              Because each programming language defines its own syntax and semantics, and there is no standardization of source code concepts nor mature initiatives that meet these requirements, resulting in source code with heterogeneity and interoperability difficulties.
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import { matEdit } from '@quasar/extras/material-icons'
import { useProjectStore } from 'src/stores/project'
const projectStore = useProjectStore()
</script>

<style scoped>
.purpose-card {
  border: 2px solid var(--q-border);
  border-radius: 8px;
  background-color: #f4f4f4;
  position: relative;
}

.purpose-step {
  display: flex;
  align-items: flex-start;
  margin-bottom: 16px;
}

.step-content {
  flex: 1;
}

.step-question {
  font-weight: bold;
  margin-bottom: 4px;
}

.step-answer {
  background-color: #eaeaea;
  padding: 8px;
  border-radius: 4px;
  white-space: pre-line;
}

.outlined-btn {
  border: 2px solid var(--q-border);
  border-radius: 8px;
  background-color: #f4f4f4;
}
</style> -->

<template>
  <page-title title="Define Purpose" />

  <div class="q-pa-md">

    <div class="q-mb-md q-gutter-sm flex justify-between items-center">
      <q-btn
        class="outlined-btn"
        :label="isEditing ? 'Cancel' : 'Edit'"
        :icon="isEditing ? matClose : matEdit"
        @click="handleEditToggle"
        padding="sm"
        no-caps
      />

      <q-btn
        v-if="isEditing"
        class="save-btn"
        color="positive"
        label="Save Editions"
        icon="save"
        padding="sm"
        no-caps
        @click="savePurpose"
        :loading="saving"
      />
    </div>

    <q-card flat bordered class="purpose-card">

      <q-card-section>
        <div v-for="(item, index) in purposeItems" :key="index" class="q-mb-md">
          <div class="text-h6 q-mb-xs">{{ item.label }}</div>

          <div v-if="!isEditing" class="step-answer q-pa-sm bg-grey-3 rounded-borders">
            {{ item.model }}
          </div>

          <q-input
            v-else
            v-model="item.model"
            filled
            type="textarea"
            autogrow
            outlined
            dense
          />
        </div>
      </q-card-section>

    </q-card>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { matEdit, matClose } from '@quasar/extras/material-icons'
import { useProjectStore } from 'src/stores/project'
import { api } from 'boot/axios'
import { useQuasar } from 'quasar'

const $q = useQuasar()
const projectStore = useProjectStore()

const isEditing = ref(false)
const saving = ref(false)

const purposeItems = ref([
  { key: 'whatQuestion', label: 'What?', model: '' },
  { key: 'forWhatQuestion', label: 'For What?', model: '' },
  { key: 'whyQuestion', label: 'Why?', model: '' }
])

const lifeCycle = projectStore.getSelectedLifeCycle

const fetchPurpose = async () => {
  if (!lifeCycle?.id) return
  try {
    const { data } = await api.get(`/project/life_cycle/${lifeCycle.id}/define_purpose`)
    if (data) {
      purposeItems.value.forEach(item => {
        item.model = data[item.key] || ''
      })
    }
  } catch (e) {
    console.error('Erro ao carregar propósito:', e)
    $q.notify({ type: 'negative', message: 'Failed to load purpose' })
  }
}

onMounted(() => fetchPurpose())

const handleEditToggle = async () => {
  if (isEditing.value) {
    await fetchPurpose()
  }
  isEditing.value = !isEditing.value
}

const savePurpose = async () => {
  if (!lifeCycle?.id) return

  saving.value = true
  try {
    const payload = Object.fromEntries(
      purposeItems.value.map(i => [i.key, i.model])
    )

    await api.put(`/project/life_cycle/${lifeCycle.id}/define_purpose`, payload)

    $q.notify({
      type: 'positive',
      message: 'Purpose successfully updated!'
    })
    isEditing.value = false
  } catch (err) {
    console.error('Erro ao salvar:', err)
    $q.notify({
      type: 'negative',
      message: 'Failed to save purpose'
    })
  } finally {
    saving.value = false
  }
}
</script>

<style scoped>
.purpose-card {
  border: 2px solid var(--q-border);
  border-radius: 8px;
  background-color: #f4f4f4;
}

.outlined-btn {
  border: 2px solid var(--q-border);
  border-radius: 8px;
}

.save-btn {
  border-radius: 8px;
}
</style>
