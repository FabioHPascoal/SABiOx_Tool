<template>
  <q-card class="custom-card" flat>
    <q-expansion-item
      :label="title"
      :icon="icon? icon : ''"
      header-class="text-subtitle1"
    >
      <q-separator color="border" />
      <q-card-section>
        <slot></slot>
      </q-card-section>
    </q-expansion-item>
  </q-card>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'ExpansibleCard'
})
</script>

<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  icon: String
});
</script>

<style scoped>
.custom-card {
  border: 2px solid var(--q-border);
  border-radius: 8px;
}
</style>
