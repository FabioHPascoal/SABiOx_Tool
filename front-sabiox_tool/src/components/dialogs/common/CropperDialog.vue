<template>
  <q-dialog ref="dialogRef" v-bind="dialogProps" v-on="dialogListeners">
    <q-card style="width: 600px; overflow-x: hidden">
      <Cropper
        v-if="fileBase64"
        ref="cropper"
        :src="fileBase64"
        :stencil-props="{ aspectRatio: 1 }"
        class="cropper"
      />
      <q-card-actions align="center">
        <q-btn
          @click="rotate(-90)"
          color="primary"
          label="Girar 90º"
          icon="undo"
          outline
        />
        <q-btn
          @click="rotate(90)"
          color="primary"
          label="Girar 90º"
          icon="redo"
          outline
        />
      </q-card-actions>
      <q-card-actions align="right">
        <q-btn
          @click="onCloseDialog"
          label="Descartar"
          color="accent"
          padding="sm md"
          flat
        />
        <q-btn
          @click="cropImage"
          label="Recortar"
          color="primary"
          text-color="dark"
          padding="sm md"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { defineComponent, ref, onBeforeMount } from 'vue'
import { useDialogPluginComponent } from 'quasar'

import { getFileBase64 } from 'src/utils/files'

export default defineComponent({
  name: 'CropperDialog',
  setup () {
    //
  }
})
</script>

<script setup>
import { Cropper } from 'vue-advanced-cropper'
import 'vue-advanced-cropper/dist/style.css'

import { useDialog } from 'src/composables/dialog'

const props = defineProps({
  file: {
    type: File,
    required: true
  }
})

/* const emits = */ defineEmits([...useDialogPluginComponent.emits])

const { dialogRef, onDialogHide, onDialogOK: onOKClick, onDialogCancel: onCancelClick } = useDialogPluginComponent()
const { dialogProps, dialogListeners, onCloseDialog } = useDialog({ onDialogHide, onCancelClick, onOKClick })

// const cropperRef = ref(null)
const fileBase64 = ref(null)

const cropper = ref(null)

onBeforeMount(async () => {
  fileBase64.value = await getFileBase64(props.file)
})

const rotate = (angle) => {
  cropper.value?.rotate(angle)
}

const cropImage = () => {
  const result = cropper.value?.getResult()
  if (result?.canvas) {
    onOKClick(result.canvas.toDataURL('image/jpeg'))
  }
}

</script>

<style>
.cropper {
  height: 60vh;
  max-width: 100%;
}
</style>
