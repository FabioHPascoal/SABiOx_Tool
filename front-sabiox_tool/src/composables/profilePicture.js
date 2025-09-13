import { format, useQuasar } from 'quasar'
import { ref, computed, watch } from 'vue'
import { base64ToBlob, checkFileType, getFileBase64, getSizeInBytes } from 'src/utils/files'

import CropperDialog from 'src/components/dialogs/common/CropperDialog.vue'

const MAX_PROFILE_PICTURE_SIZE = '3 MB'
const maxProfilePictureSize = getSizeInBytes(MAX_PROFILE_PICTURE_SIZE)
const ACCEPTED_IMAGE_FILE_TYPES = ['image/jpeg', 'image/webp', 'image/png']

export const profilePictureProps = {
  modelValue: {
    type: [String, Blob]
  },
  avatarSize: {
    type: String,
    default: '200px'
  },
  icon: {
    type: String,
    default: 'person'
  },
  disable: {
    type: Boolean,
    default: false
  },
  rules: Array
}

export const profilePictureEmits = ['update:model-value', 'generate-base64']

export default function (props, { emit }) {
  const $q = useQuasar()

  const modelVal = computed({
    get: () => props.modelValue,
    set: (newValue) => emit('update:model-value', newValue)
  })

  const profilePictureInput = ref(null)

  const profilePicturePreview = ref(null)

  watch(() => modelVal.value, async (val) => {
    profilePicturePreview.value = val instanceof Blob
      ? await getFileBase64(val)
      : val
  }, { immediate: true })

  const onFileUpload = (evt) => {
    const [file] = evt.target.files
    if (file) openCropperDialog(file)
    profilePictureInput.value.value = ''
  }
  const onFileDrop = (evt) => {
    const [file] = evt.dataTransfer.files
    if (file) openCropperDialog(file)
  }

  const acceptedImageFileTypes = ACCEPTED_IMAGE_FILE_TYPES.join(',')

  const openCropperDialog = (file) => {
    if (!checkFileType(file, acceptedImageFileTypes)) {
      return $q.notify({
        type: 'alert',
        message: 'Formato do arquivo não suportado.'
      })
    }

    if (file.size > maxProfilePictureSize) {
      return $q.notify({
        type: 'alert',
        message: `Arquivo muito grande. Tamanho máximo aceito: ${format.humanStorageSize(maxProfilePictureSize)}`
      })
    }

    $q.dialog({
      component: CropperDialog,
      componentProps: { file }
    })
      .onOk(async fileBase64 => {
        const blob = await base64ToBlob(fileBase64)
        modelVal.value = new File([blob], file.name, {
          type: file.type,
          lastModified: file.lastModified
        })
        emit('generate-base64', fileBase64)
      })
  }

  return {
    modelVal,
    profilePictureInput,
    profilePicturePreview,
    onFileUpload,
    onFileDrop,
    acceptedImageFileTypes
  }
}
