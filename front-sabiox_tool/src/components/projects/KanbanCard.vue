<template>
  <q-card class="custom-card" flat>
    <div class="card-title">
      {{ title }}
    </div>

    <div class="card-content">
      <div
        v-for="(row, index) in rows"
        :key="index"
        :class="['card-row', { invisible: row === '__empty__' }]"
      >
        {{ row !== '__empty__' ? row : 'placeholder' }}
      </div>
    </div>
  </q-card>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    default: ''
  },
  rows: {
    type: Array,
    required: true
  }
});
</script>

<style scoped>
.custom-card {
  border: 2px solid var(--q-border);
  border-radius: 8px;
  display: inline-block;
  padding: 8px 16px;
  background-color: var(--q-surface);
}

.card-title {
  text-align: center;
  font-weight: 600;
  margin-bottom: 8px;
  white-space: nowrap;
}

.card-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
}

.card-row {
  white-space: nowrap;
}

/* 🔹 Esta classe é aplicada *junta* com .card-row */
.invisible {
  visibility: hidden;
}
</style>
