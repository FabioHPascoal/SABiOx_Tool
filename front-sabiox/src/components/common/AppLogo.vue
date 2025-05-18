<template>
  <img :src="src" v-bind="$attrs" alt="App Logo" />
</template>

<script>
import { computed } from 'vue'

import logoLight from 'src/assets/app-logo-light.svg'
import logoDark from 'src/assets/app-logo-dark.svg'

export default {
  name: 'AppLogo',
  inheritAttrs: true
}
</script>

<script setup>

const props = defineProps({
  dark: Boolean
})

const src = computed(() => {
  return props.dark
    ? logoDark
    : logoLight
})

</script>
