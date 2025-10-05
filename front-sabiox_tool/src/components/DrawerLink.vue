<template>
  <q-item
    clickable
    :to="to"
    :exact="exact"
    active-class="active-item"
    class="q-py-none"
  >
    <q-item-section v-if="icon" side>
      <q-icon :name="icon" :color="color" />
    </q-item-section>
    <q-item-section :class="color ? `text-${color}` : ''">
      <q-item-label>{{ label }}</q-item-label>
    </q-item-section>
  </q-item>
</template>

<script setup>
defineOptions({
  name: 'DrawerLink'
})

/* const props = */ defineProps({
  label: {
    type: String,
    required: true
  },
  to: {
    type: Object,
    required: true
  },
  color: String,
  icon: String,
  exact: Boolean
})
</script>
