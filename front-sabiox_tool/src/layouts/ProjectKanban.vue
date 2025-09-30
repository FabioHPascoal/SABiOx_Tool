<template>
  <transition name="slide-down">
    <div
      v-if="modelValue"
      class="bg-background"
      style="height: 200px; border-bottom: 3px solid var(--q-border)"
    >
    </div>
  </transition>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'ProjectKanban'
})
</script>

<script setup>
const props = defineProps({
  modelValue: Boolean
})
</script>

<style scoped>
/* Estado inicial da entrada */
.slide-down-enter-from {
  max-height: 0;
  opacity: 0;
}

/* Estado final da entrada */
.slide-down-enter-to {
  max-height: 200px;
  opacity: 1;
}

/* Estado inicial da saída */
.slide-down-leave-from {
  max-height: 200px;
  opacity: 1;
}

/* Estado final da saída */
.slide-down-leave-to {
  max-height: 0;
  opacity: 0;
}

/* Duração e easing */
.slide-down-enter-active,
.slide-down-leave-active {
  transition: all 0.3s ease;
}
</style>
