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
    >
    <q-item-section>
      <q-item-label :class="color ? `text-${color}` : ''">
        {{ link.label }}
      </q-item-label>
    </q-item-section>
      <!-- <q-item-section avatar>
        <q-icon :name="link.icon" :color="color ? `text-${color}` : ''" />
      </q-item-section> -->
    </q-item>
  </q-list>
</template>

<script>
import { computed, defineComponent, ref } from 'vue'
import { useQuasar } from 'quasar'

import emptyIcon from 'src/assets/state-empty.svg'
import inProgressIcon from 'src/assets/state-in-progress.svg'
import documentationIcon from 'src/assets/state-documentation.svg'
import okIcon from 'src/assets/state-ok.svg'

export default defineComponent({
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

</script>
