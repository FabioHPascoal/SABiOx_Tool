<template>
  <div :class="sticky && 'sticky-header'">
    <q-card-section class="row no-wrap">
      <div class="full-width-flex">
        <slot name="title" :loading="loading">
          <div class="text-h6 text-primary flex items-center full-height">
            <slot name="before-title" />
            <q-skeleton v-if="loading" width="55%" height="1em" />
            <template v-else>
              <slot name="title-label">
                {{ title }}
              </slot>
            </template>
            <slot name="after-title" />
          </div>
        </slot>
      </div>
      <div v-if="!noCloseButton">
        <q-btn
          @click="$emit('close')"
          icon="close"
          size="sm"
          color="accent"
          flat
          round
        />
      </div>
    </q-card-section>
    <q-separator v-if="!noSeparator" />
  </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DialogHeader'
})
</script>

<script setup>

/* const props = */ defineProps({
  title: String,
  noCloseButton: Boolean,
  noSeparator: Boolean,
  sticky: Boolean,
  loading: Boolean
})

/* const emits = */ defineEmits(['close'])

</script>

<style lang="scss">
.sticky-header {
  position: sticky;
  background-color: var(--q-background);
  top: 0;
  z-index: $z-max + 1;
}
</style>
