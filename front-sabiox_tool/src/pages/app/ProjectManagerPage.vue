<template>
  <q-page padding>
    <!-- Cabeçalho -->
    <div class="row justify-between items-center q-mb-md">
      <h4>Project Manager</h4>
      <q-btn label="New project" color="primary" icon="add" @click="dialogVisible = true" no-caps />
    </div>

    <!-- Lista de projetos -->
    <q-list bordered separator v-if="projetos.length">
      <q-item v-for="(projeto, index) in projetos" :key="index" clickable @click="abrirProjeto(projeto)">
        <q-item-section avatar>
          <q-icon name="folder" />
        </q-item-section>
        <q-item-section>
          <q-item-label>{{ projeto.title }}</q-item-label>
          <q-item-label caption>{{ projeto.description }}</q-item-label>
        </q-item-section>
      </q-item>
    </q-list>

    <div v-else class="text-grey-6 text-center q-mt-md">
      Nenhum projeto encontrado.
    </div>

    <!-- Diálogo para criação de projeto -->
    <q-dialog v-model="dialogVisible">
      <q-card style="min-width: 400px">
        <q-card-section>
          <div class="text-h6">Novo Projeto</div>
        </q-card-section>

        <q-card-section class="q-gutter-md">
          <q-input v-model="form.title" label="Título" filled />
          <q-input v-model="form.description" label="Descrição" filled type="textarea" />
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="Cancelar" color="primary" v-close-popup />
          <q-btn label="Salvar" color="primary" @click="criarProjeto" :loading="salvando" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useQuasar } from 'quasar'
import { api } from 'src/boot/axios'
import { useAuthStore } from 'stores/auth'

const $q = useQuasar()
const authStore = useAuthStore()

const projetos = ref([])
const dialogVisible = ref(false)
const salvando = ref(false)

const form = ref({
  title: '',
  description: ''
})

// Carrega projetos ao montar o componente
async function carregarProjetos() {
  try {
    const response = await api.get('/projects')
    projetos.value = response.data
  } catch (error) {
    console.error('Erro ao carregar projetos:', error)
    $q.notify({ type: 'negative', message: 'Erro ao buscar projetos' })
  }
}

// Cria novo projeto
async function criarProjeto() {
  salvando.value = true
  try {
    const payload = {
      ...form.value,
      userId: authStore.getUser?.id
    }

    const response = await api.post('/projects', payload)
    projetos.value.push(response.data)

    $q.notify({ type: 'positive', message: 'Projeto criado com sucesso!' })

    // Resetar
    dialogVisible.value = false
    form.value.title = ''
    form.value.description = ''
  } catch (error) {
    console.error('Erro ao criar projeto:', error)
    $q.notify({ type: 'negative', message: 'Erro ao criar projeto' })
  } finally {
    salvando.value = false
  }
}

// Placeholder para ação ao clicar em um projeto
function abrirProjeto(projeto) {
  console.log('Abrir projeto:', projeto)
  // Ex: this.$router.push(`/projects/${projeto.id}`)
}

onMounted(() => {
  carregarProjetos()
})
</script>
