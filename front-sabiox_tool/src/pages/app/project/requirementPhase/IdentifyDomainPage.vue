<template>
  <page-title title="Identify and Size Domain" />

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
        @click="saveDomain"
        :loading="saving"
      />
    </div>

    <q-card flat bordered class="purpose-card">

      <q-card-section>
        <div v-for="(item, index) in items" :key="index" class="q-mb-md">
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
import { ref, onMounted, watch, computed, nextTick } from 'vue'
import { matEdit, matClose } from '@quasar/extras/material-icons'
import { useProjectStore } from 'src/stores/project'
import { api } from 'boot/axios'
import { useQuasar } from 'quasar'

const $q = useQuasar()
const projectStore = useProjectStore()

const isEditing = ref(false)
const saving = ref(false)

const items = ref([
  { key: 'description', label: 'Domain', model: '' },
  { key: 'horizontalDimension', label: 'Horizontal Dimension', model: '' },
  { key: 'verticalDimension', label: 'Vertical Dimension', model: '' }
])

const lifeCycle = computed(() => projectStore.getSelectedLifeCycle)

const fetchDomain = async () => {
  if (!lifeCycle.value?.id) return
  try {
    const { data } = await api.get(`/project/life_cycle/${lifeCycle.value.id}/identify_domain`)
    if (data?.domainDTO) {
      items.value.forEach(item => {
        item.model = data.domainDTO[item.key] || ''
      })
    }
  } catch (e) {
    console.error('Erro ao carregar domínio:', e)
    $q.notify({ type: 'negative', message: 'Failed to load data' })
  }
}

watch(() => lifeCycle.value?.id, (id) => {
  if (id) fetchDomain()
})

onMounted(() => fetchDomain())

const handleEditToggle = async () => {
  if (isEditing.value) {
    await fetchDomain()
  }
  isEditing.value = !isEditing.value
}

const saveDomain = async () => {
  if (!lifeCycle.value?.id) return

  saving.value = true
  try {
    const payload = Object.fromEntries(
      items.value.map(i => [i.key, i.model])
    )

    await api.put(`/project/life_cycle/${lifeCycle.value.id}/identify_domain`, payload)
    await projectStore.fetchPhases()

    $q.notify({
      type: 'positive',
      message: 'Changes saved successfully'
    })
    isEditing.value = false
  } catch (err) {
    console.error('Erro ao salvar:', err)
    $q.notify({
      type: 'negative',
      message: 'Failed to save changes'
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
