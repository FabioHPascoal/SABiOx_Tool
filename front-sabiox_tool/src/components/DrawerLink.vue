<template>
  <q-item
    clickable
    :to="to"
    :exact="exact"
    active-class="active-item"
  >
    <q-item-section v-if="icon" side>
      <q-icon :name="icon" color="onPrimary" />
    </q-item-section>
    <q-item-section>
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
  icon: String,
  exact: Boolean
})
</script>
