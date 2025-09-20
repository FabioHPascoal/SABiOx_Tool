<template>
  <q-dialog ref="dialogRef" v-on="dialogListeners" v-bind="dialogProps">
    <q-card class="default-dialog">
      <q-form ref="formRef" @submit.prevent="onSubmit">
        <dialog-header @close="onCloseDialog" title="New Project" sticky />
      </q-form>
    </q-card>
  </q-dialog>
</template>

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

<script>
import { defineComponent, ref, shallowReactive } from 'vue'
import { useDialogPluginComponent, useQuasar } from 'quasar'
import cloneDeep from 'lodash.clonedeep'
import { api } from 'src/boot/axios'

import { useDialog } from 'src/composables/dialog'
import { DEFAULT_PROJECT } from 'src/constants/projects'

export default defineComponent({
  name: 'NewProjectDialog'
})
</script>

<script setup>

const props = defineProps({
  onCreate: Function
})

/* const emits = */ defineEmits([...useDialogPluginComponent.emits])

const $q = useQuasar()
const { dialogRef, onDialogHide, onDialogOK: onOKClick, onDialogCancel: onCancelClick } = useDialogPluginComponent()

const { dialogProps, dialogListeners, onCloseDialog } = shallowReactive(useDialog(
  { onDialogHide, onCancelClick, onOKClick },
  async ({ confirmExit, isEqual }) => {
    if (submitLoading.value) return

    if (step.value === 5) {
      onOKClick()
      return
    }

    if (!isEqual(initialProject, innerProject.value) && await confirmExit() === false) return

    onCancelClick()
  }
))

const innerProject = ref(cloneDeep(DEFAULT_PROJECT))
const initialProject = cloneDeep(innerProject.value)

const firstAnswerDeadline = ref(null)

const step = ref(1)
const stepperRef = ref(null)

const formRef = ref(null)
const submitStep = async () => {
  if (step.value === 4) {
    await onSubmit()
    return
  }

  if (!(await formRef.value.validate())) return

  stepperRef.value.next()
}

const submitLoading = ref(false)
const onSubmit = async () => {
  if (step.value !== 4) {
    await submitStep()
    return
  }

  try {
    submitLoading.value = true

    const { data } = await api.post('project', innerProject.value)

    firstAnswerDeadline.value = data?.firstAnswerDeadline

    $q.notify({
      type: 'positive',
      message: 'Project created successfully'
    })

    stepperRef.value.next()

    if (props.onCreate) await props.onCreate()
  } finally {
    submitLoading.value = false
  }
}
</script>

<style lang="scss">
.project-stepper {
  .q-stepper__step-inner {
    padding-top: 0;
  }
}
</style>
