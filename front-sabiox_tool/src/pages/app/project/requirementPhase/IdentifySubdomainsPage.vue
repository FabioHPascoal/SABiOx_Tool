<template>
  <page-title title="Identify Subdomains" />

  <div class="q-pa-md">

    <div class="q-mb-md q-gutter-sm flex justify-between items-center">
      <q-btn
        class="outlined-btn"
        label="Add Subdomain"
        icon="add"
        padding="sm"
        no-caps
        @click="showDialog = true"
      />
    </div>

    <q-card flat bordered class="purpose-card">
      <q-card-section>
        <div v-if="loading" class="text-grey text-center q-pa-md">
          Loading subdomains...
        </div>

        <div v-else-if="subdomains.length === 0" class="text-grey text-center q-pa-md">
          No subdomains found.
        </div>

        <div v-else>
          <div
            v-for="(sub) in subdomains"
            :key="sub.id"
          >
            <div class="text-h6">{{ sub.title }}</div>
            <div class="q-mb-md bg-grey-3 q-pa-sm rounded-borders">
              {{ sub.description }}
            </div>
          </div>
        </div>
        <!-- <div v-else>
          <div
            v-for="(sub) in subdomains"
            :key="sub.id"
            class="q-mb-md bg-grey-3 q-pa-sm rounded-borders"
          >
            <div class="text-h6">{{ sub.title }}</div>
            <div class="text-body2 text-grey-8">
              {{ sub.description }}
            </div>
          </div>
        </div> -->
      </q-card-section>
    </q-card>

    <q-dialog v-model="showDialog" persistent>
      <q-card style="min-width: 400px">
        <q-card-section class="text-h6">
          Add Subdomain
        </q-card-section>

        <q-separator />

        <q-card-section class="q-gutter-md">
          <q-input
            v-model="form.title"
            label="Title"
            outlined
            dense
            required
          />
          <q-input
            v-model="form.description"
            label="Description"
            type="textarea"
            outlined
            autogrow
            dense
          />
        </q-card-section>

        <q-separator />

        <q-card-actions align="right">
          <q-btn flat label="Cancel" color="grey" v-close-popup />
          <q-btn
            label="Add"
            color="positive"
            :loading="saving"
            @click="addSubdomain"
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

const subdomains = ref([])
const loading = ref(false)
const saving = ref(false)
const showDialog = ref(false)
const form = ref({ title: '', description: '' })

const lifeCycle = computed(() => projectStore.getSelectedLifeCycle)

// Busca os subdomínios existentes
const fetchSubdomains = async () => {
  if (!lifeCycle.value?.id) return
  loading.value = true
  try {
    const { data } = await api.get(`/project/life_cycle/${lifeCycle.value.id}/identify_subdomains`)
    subdomains.value = data?.subdomains || []
  } catch (err) {
    console.error('Erro ao carregar subdomínios:', err)
    $q.notify({ type: 'negative', message: 'Failed to load subdomains' })
  } finally {
    loading.value = false
  }
}

watch(() => lifeCycle.value?.id, (id) => {
  if (id) fetchSubdomains()
})

onMounted(() => fetchSubdomains())

// Adiciona novo subdomínio
const addSubdomain = async () => {
  if (!form.value.title?.trim()) {
    $q.notify({ type: 'warning', message: 'Title is required' })
    return
  }

  if (!lifeCycle.value?.id) return

  saving.value = true
  try {
    const payload = { ...form.value }
    await api.put(`/project/life_cycle/${lifeCycle.value.id}/identify_subdomains`, payload)
    await fetchSubdomains()
    await projectStore.fetchPhases()

    $q.notify({ type: 'positive', message: 'Subdomain added successfully' })
    showDialog.value = false
    form.value = { title: '', description: '' }
  } catch (err) {
    console.error('Erro ao adicionar subdomínio:', err)
    $q.notify({ type: 'negative', message: 'Failed to add subdomain' })
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
