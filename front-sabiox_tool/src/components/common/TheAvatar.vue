<template>
  <q-avatar
    :icon="avatarIcon"
    :color="color"
    :text-color="textColor"
    :size="size"
    class="bg-transparent"
  >
    <img
      v-if="!avatarIcon"
      @error="onError"
      @load="onLoad"
      :src="innerAvatarUrl"
    />
  </q-avatar>
</template>

<script>
import { ref, computed, defineComponent, watch } from 'vue'

export default defineComponent({
  name: 'TheAvatar'
})
</script>

<script setup>

const props = defineProps({
  avatarUrl: String,
  defaultAvatarIcon: {
    type: String,
    default: 'person'
  },
  size: {
    type: String,
    default: 'md'
  },
  color: {
    type: String,
    default: 'primary'
  },
  textColor: {
    type: String,
    default: 'primary'
  },
  isAnonymous: Boolean
})

const hasError = ref(false)
const avatarIcon = computed(() => {
  return (props.avatarUrl && !hasError.value) || props.isAnonymous
    ? undefined
    : props.defaultAvatarIcon
})

const onError = () => {
  hasError.value = true
}
const onLoad = () => {
  hasError.value = false
}

watch(() => props.avatarUrl, () => {
  hasError.value = false
})

const innerAvatarUrl = computed(() => {
  return props.isAnonymous
    ? '/anonymous-icon.png'
    : props.avatarUrl
})
</script>
