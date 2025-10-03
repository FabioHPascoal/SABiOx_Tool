<template>
  <div class="page-title" :class="titleClass">
    <div v-if="spaced && $q.screen.gt.sm" class="q-pt-md" />
    <slot name="before" />
    <div>
      {{ title }}
    </div>
    <slot name="after" />
  </div>
</template>

<script>
import { computed, defineComponent } from 'vue'
import { useQuasar } from 'quasar'

export default defineComponent({
  name: 'PageTitle'
})
</script>

<script setup>

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  spaced: {
    type: Boolean,
    default: true
  }
})

const $q = useQuasar()

const titleClass = computed(() => {
  const isDesktop = $q.screen.gt.sm

  const _classes = ['text-h5', 'text-bold']

  if (props.spaced && isDesktop) _classes.push('q-layout-padding')

  _classes.push(isDesktop ? 'text-onBackground' : 'text-onPrimary bg-primary text-center q-pb-xs q-px-sm')

  return _classes
})

</script>

<style lang="scss" scoped>
.page-title {
  margin-top: 0px;
  padding-top: 0px;
}
</style>
