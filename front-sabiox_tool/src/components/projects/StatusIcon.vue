<template>
    <img :src="statusMap[status]" v-bind="$attrs" alt="Status Icon" />
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'StatusIcon'
})
</script>

<script setup>
import emptyIcon from 'src/assets/state-empty.svg'
import inProgressIcon from 'src/assets/state-in-progress.svg'
import documentationIcon from 'src/assets/state-documentation.svg'
import okIcon from 'src/assets/state-ok.svg'

const props = defineProps({
  status: {
    type: String,
    default: 'empty'
  }
})

const statusMap = {
  'empty': emptyIcon,
  'in-progress': inProgressIcon,
  'documentation': documentationIcon,
  'ok': okIcon
}

</script>
