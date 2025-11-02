<template>
  <q-card class="custom-card" flat>
    <q-expansion-item>
      <template #header>
        <div class="col items-center justify-between full-width">
          <div class="text-subtitle1 break-word-justify text-onBackground">{{ title }}</div>

          <q-separator v-if="badgeLabel || user" class="q-my-sm" color="grey" />

          <div class="row items-center justify-between full-width q-mt-xs">
            <q-badge
              v-if="badgeLabel"
              outline
              :color="badgeColor"
              :label="badgeLabel"
            />

            <div
              v-if="user"
              class="row items-center q-gutter-sm text-caption text-grey-8"
            >
              <span>Created by</span>
              <q-avatar size="24px">
                <img :src="user.avatarUrl" :alt="user.name" />
              </q-avatar>
              <span>{{ user.name }}</span>
              <span class="q-ml-xs" v-if="creationDate">on {{ formattedDate }}</span>
            </div>
          </div>
        </div>
      </template>

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
import { computed } from 'vue'

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  badgeLabel: String,
  badgeColor: String,
  user: {
    type: Object,
    default: null
  },
  creationDate: {
    type: String,
    default: ''
  }
})

const formattedDate = computed(() => {
  if (!props.creationDate) return ''
  const date = new Date(props.creationDate)
  return date.toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
})
</script>

<style scoped>
.custom-card {
  border: 2px solid var(--q-border);
  border-radius: 8px;
}

.break-word-justify {
  white-space: normal;
  word-break: break-word;
  overflow-wrap: anywhere;
  text-align: justify;
}

:deep(.q-item__section--side:last-child) {
  padding-right: 0 !important;
}

</style>
