<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide" :persistent="isPersistent" :position="$q.screen.xs ? 'bottom' : 'standard'">
    <q-card class="q-dialog-plugin">
      <dialog-header :title="title" no-close-button sticky />
      <q-card-section v-if="message">
        {{ message }}
      </q-card-section>
      <q-card-section v-if="secondaryMessage">
        {{ secondaryMessage }}
      </q-card-section>
      <q-card-actions :align="reverse ? 'left' : 'right'" class="q-gutter-x-sm" :class="{ reverse }">
        <q-btn
          v-if="!hideCancelButton"
          v-bind="cancelProps"
          @click="onCancelButtonClick"
          :disable="cancelLoading || cancelBtnLoading || okLoading || okBtnLoading"
          data-cy="confirmation-cancel"
        >
          <q-inner-loading :showing="cancelBtnLoading || cancelLoading" />
        </q-btn>
        <q-btn
          v-if="!hideOkButton"
          v-bind="okProps"
          @click="onOkButtonClick"
          :disable="cancelLoading || cancelBtnLoading || okLoading || okBtnLoading"
          data-cy="confirmation-ok"
        >
          <q-inner-loading :showing="okLoading || okBtnLoading" />
        </q-btn>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { defineComponent, ref, computed } from 'vue'
import { useDialogPluginComponent } from 'quasar'

export default defineComponent({
  name: 'ConfirmationDialog'
})
</script>

<script setup>

const props = defineProps({
  negative: {
    type: Boolean,
    default: false
  },
  reverse: {
    type: Boolean,
    default: false
  },
  okLabel: {
    type: String,
    default: 'Confirmar'
  },
  cancelLabel: {
    type: String,
    default: 'Cancelar'
  },
  title: {
    type: String,
    default: 'Confirmar'
  },
  hideCancelButton: Boolean,
  hideOkButton: Boolean,
  message: String,
  secondaryMessage: String,
  persistent: Boolean,
  ok: Function,
  cancel: Function,
  okBtnLoading: Boolean,
  cancelBtnLoading: Boolean
})

/* const emits = */ defineEmits([...useDialogPluginComponent.emits])

const { dialogRef, onDialogHide, onDialogOK: onOKClick, onDialogCancel: onCancelClick } = useDialogPluginComponent()

const defaultProps = {
  padding: 'sm md'
}

const primaryProps = {
  ...defaultProps,
  color: props.negative ? 'negative' : 'primary',
  textColor: props.negative ? 'white' : 'dark'
}
const secondaryProps = {
  ...defaultProps,
  color: 'accent',
  flat: true
}

const okProps = {
  label: props.okLabel,
  ...(props.reverse ? secondaryProps : primaryProps)
}
const cancelProps = {
  label: props.cancelLabel,
  ...(props.reverse ? primaryProps : secondaryProps)
}

const cancelLoading = ref(false)
const onCancelButtonClick = async () => {
  try {
    cancelLoading.value = true

    if (props.cancel) await props.cancel()

    onCancelClick()
  } finally {
    cancelLoading.value = false
  }
}

const okLoading = ref(false)
const onOkButtonClick = async () => {
  try {
    okLoading.value = true

    if (props.ok) await props.ok()

    onOKClick()
  } finally {
    okLoading.value = false
  }
}

const isPersistent = computed(() => cancelLoading.value || okLoading.value || props.persistent)

</script>
