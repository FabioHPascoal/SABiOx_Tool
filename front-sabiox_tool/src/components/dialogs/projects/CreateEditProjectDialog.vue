<!-- <q-dialog v-model="dialogVisible">
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
        <q-btn label="Salvar" color="primary" @click="criarProjeto" :loading="saving" />
    </q-card-actions>
    </q-card>
</q-dialog> -->

<!-- // Cria novo projeto
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
} -->

<template>
  <q-dialog ref="dialogRef" v-on="dialogListeners" v-bind="dialogProps">
    <q-card ref="cardRef" class="full-width">
      <q-form @submit.prevent="onSubmit">
        <dialog-header @close="onCloseDialog" :title="headerTitle" sticky />
          <q-card-section>
            <div class="row q-col-gutter-md">
              <div class="col-12">
                <q-input
                  v-model="innerProject.title"
                  label="Title *"
                  :rules="rules.title"
                />
              </div>
              <div class="col-12">
                <q-input
                  v-model="innerProject.description"
                  label="Description *"
                  :rules="rules.description"
                />
              </div>
              <!-- <div class="col-12">
                <steps-editor v-model="innerActionPlan.steps" :action-plan-id="actionPlanId" />
              </div> -->
            </div>
          </q-card-section>
        <q-card-actions align="right">
          <q-btn
            v-if="!!props.projectId"
            @click="attemptDeleteProject"
            :disable="deleteLoading || submitLoading"
            label="Delete"
            color="negative"
            padding="sm md"
          >
            <q-inner-loading :showing="deleteLoading" />
          </q-btn>
          <q-space />
          <q-btn
            @click="onCloseDialog"
            label="Close"
            color="accent"
            padding="sm md"
            flat
          />
          <q-btn
            type="submit"
            label="Save"
            :disable="submitLoading || deleteLoading"
            color="primary"
            text-color="onPrimary"
            padding="sm md"
          >
            <q-inner-loading :showing="submitLoading" />
          </q-btn>
        </q-card-actions>
      </q-form>
    </q-card>
  </q-dialog>
</template>

<script>
import { defineComponent, defineProps, defineEmits, ref, shallowReactive, reactive, computed, onMounted } from 'vue'
import { useDialogPluginComponent, useQuasar } from 'quasar'
import { api } from 'src/boot/axios'
import cloneDeep from 'lodash.clonedeep'

import { requiredRule } from 'src/utils/rules'
import { useDialog } from 'src/composables/dialog'
import ConfirmationDialog from 'src/components/dialogs/common/ConfirmationDialog.vue'
import { DEFAULT_PROJECT } from 'src/constants/projects'

const useRules = (project) => {
  return {
    title: [requiredRule()],
    description: [requiredRule()]
  }
}

export default defineComponent({
  name: 'CreateEditActionPlanDialog'
})
</script>

<script setup>

const props = defineProps({
  projectId: String,
  onCreate: Function,
  onUpdate: Function,
  onDelete: Function
})

/* const emit = */ defineEmits([...useDialogPluginComponent.emits])

const { dialogRef, onDialogHide, onDialogOK: onOKClick, onDialogCancel: onCancelClick } = useDialogPluginComponent()

const { dialogProps, dialogListeners, onCloseDialog } = shallowReactive(useDialog(
  { onDialogHide, onCancelClick, onOKClick },
  async ({ confirmExit, isEqual }) => {
    if (submitLoading.value || deleteLoading.value) return
    if (!isEqual(initialActionPlan, innerProject.value) && await confirmExit() === false) return
    onCancelClick()
  }
))

const cardRef = ref(null)

const $q = useQuasar()

const headerTitle = computed(() => props.projectId ? 'Edit Project' : 'Create Project')

const innerProject = ref(cloneDeep(DEFAULT_PROJECT))
let initialActionPlan = cloneDeep(innerProject.value)

const rules = reactive(useRules(innerProject))

// const actionPlanLoading = ref(false)
// const getActionPlan = async () => {
//   if (!props.actionPlanId) return

//   try {
//     actionPlanLoading.value = true

//     const { data } = await api.get(`action-plan/${props.actionPlanId}`)

//     Object.assign(innerActionPlan.value, data)

//     initialActionPlan = cloneDeep(innerActionPlan.value)
//   } finally {
//     actionPlanLoading.value = false
//   }
// }

const deleteLoading = ref(false)
const attemptDeleteProject = async () => {
  $q.dialog({
    component: ConfirmationDialog,
    componentProps: {
      title: 'Delete Project',
      message: 'Are you sure you want to delete this project? This action cannot be undone.',
      okLabel: 'Delete',
      cancelLabel: 'Cancel',
      negative: true,
      ok: async () => {
        try {
          deleteLoading.value = true

          const { data } = await api.put(`project/disable/${props.projectId}`)

          $q.notify({
            type: 'positive',
            message: 'Project deleted successfully.'
          })

          if (props.onDelete) props.onDelete(data)

          onOKClick()
        } finally {
          deleteLoading.value = false
        }
      }
    },
    persistent: true
  })
}

const submitCreateFn = async (payload) => {
  try {
    submitLoading.value = true

    const { data } = await api.post('project', payload)

    $q.notify({
      type: 'positive',
      message: 'Project created successfully.'
    })

    if (props.onCreate) await props.onCreate(data)
  } finally {
    submitLoading.value = false
  }
}

const submitUpdateFn = async (payload) => {
  try {
    submitLoading.value = true

    const { data } = await api.put(`project/${props.projectId}`, payload)

    $q.notify({
      type: 'positive',
      message: 'Project updated successfully.'
    })

    if (props.onUpdate) await props.onUpdate(data)
  } finally {
    submitLoading.value = false
  }
}

const submitLoading = ref(false)
const onSubmit = async () => {
  const payload = cloneDeep(innerProject.value)

  props.projectId
    ? await submitUpdateFn(payload)
    : await submitCreateFn(payload)

  onOKClick()
}

// onMounted(async () => {
//   if (props.actionPlanId) await getActionPlan()
// })

</script>
