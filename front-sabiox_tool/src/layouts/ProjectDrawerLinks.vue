<template>
  <q-item class="flex justify-center">
    <q-item-label :class="color ? `text-${color}` : ''" class="text-h5">
      {{ title }}
    </q-item-label>
  </q-item>
  <q-list>
    <q-item
      v-for="link in links"
      :key="link.name"
      :to="link.to"
      clickable
      active-class="bg-btnPressed text-onBackground"
      class="custom-item q-py-sm"
    >
    <q-item-section>
      <q-item-label :class="color ? `text-${color}` : ''">
        {{ link.label }}
      </q-item-label>
    </q-item-section>
    <q-item-section side>
      <status-icon
        :status="statusMap[link.stage] || 'empty'"
        width="18"
        height="18"
      />
    </q-item-section>
    </q-item>
  </q-list>
</template>

<script>
import { computed, defineComponent, ref, onMounted } from 'vue'

import StatusIcon from '../components/projects/StatusIcon.vue'

export default defineComponent({
  components: { StatusIcon },
  name: 'ProjectDrawerLinks'
})
</script>

<script setup>

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  color: {
    type: String,
    default: 'onBackground'
  },
  links: {
    type: Object,
    required: true
  }
})

const statusMap = {
  'NOT_STARTED': 'empty',
  'IN_PROGRESS': 'in-progress',
  'DOCUMENTATION': 'documentation',
  'CONTROL': 'documentation',
  'EVALUATE': 'documentation',
  'COMPLETED': 'ok'
}
</script>

<style scoped>
  .custom-item {
    min-height: unset;
  }
</style>