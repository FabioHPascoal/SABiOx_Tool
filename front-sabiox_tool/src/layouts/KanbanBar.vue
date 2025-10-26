<!-- <template>
  <transition name="slide-down">
    <div
      v-if="modelValue && kanbanCards.length"
      class="bg-background row wrap q-pa-md"
      style="border-bottom: 3px solid var(--q-border); justify-content: space-between;"
    >
      <kanban-card
        v-for="card in kanbanCards"
        :key="card.title"
        :title="card.title"
        :rows="card.rows"
        style="flex: 1 1 200px; margin: 8px;"
      />
    </div>

  </transition>
</template> -->

<!-- <template>
  <transition name="slide-down">
    <div
      v-if="modelValue && kanbanCards.length"
      class="bg-background row wrap q-pa-md q-gutter-md"
      style="border-bottom: 3px solid var(--q-border);"
    >
      <kanban-card
        v-for="card in kanbanCards"
        :key="card.title"
        :title="card.title"
        :rows="card.rows"
      />
    </div>
  </transition>
</template> -->

<!-- <template>
  <transition name="slide-down">
    <div
      v-if="modelValue && kanbanCards.length"
      class="bg-background row wrap q-pa-md q-gutter-md"
      style="border-bottom: 3px solid var(--q-border);"
    >
      <kanban-card
        v-for="card in kanbanCards"
        :key="card.title"
        :title="card.title"
        :rows="card.rows"
        style="flex: 1 1 200px; min-width: 200px;"
      />
    </div>
  </transition>
</template> -->

<template>
  <transition name="slide-down">
    <div
      v-if="modelValue"
      class="bg-background row wrap q-pa-md"
      style="
        border-bottom: 3px solid var(--q-border);
        justify-content: space-between;
        column-gap: 16px;
        row-gap: 16px;
      "
    >
      <template v-if="kanbanCards.length">
        <kanban-card
          v-for="card in kanbanCards"
          :key="card.title"
          :title="card.title"
          :rows="card.rows"
          style="flex: 0 0 auto;"
        />
      </template>

      <div
        v-else
        class="text-grey text-center q-pa-lg full-width"
        style="opacity: 0.7;"
      >
        No life cycles available for this phase
      </div>
    </div>
  </transition>
</template>


<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'KanbanBar'
})
</script>

<script setup>
import { computed } from 'vue'
import { useProjectStore } from 'src/stores/project'
import KanbanCard from '../components/projects/KanbanCard.vue'
import { PHASE_CARDS, ACTIVITY_LABELS } from 'src/constants/kanbanConfig'

const props = defineProps({
  modelValue: Boolean
})

const projectStore = useProjectStore()

const selectedCycle = computed(() => projectStore.getSelectedLifeCycle)

const orderedActivities = computed(() => {
  const acts = selectedCycle.value?.activities || {}
  return Object.entries(acts)
    .map(([key, val]) => ({ key, ...val }))
    .sort((a, b) => a.id - b.id)
})

const phaseStages = computed(() => PHASE_CARDS[projectStore.selectedPhaseType] || [])

const kanbanCards = computed(() => {
  if (!orderedActivities.value.length) return []

  return phaseStages.value.map(stageName => {
    const rows = orderedActivities.value.map(activity =>
      activity.stage === stageName
        ? (ACTIVITY_LABELS[activity.key] || activity.key)
        : '__empty__'
    )

    const title = stageName
      .toLowerCase()
      .replace(/_/g, ' ')
      .replace(/\b\w/g, l => l.toUpperCase())

    return { title, rows }
  })
})
</script>

<style scoped>
.slide-down-enter-from {
  max-height: 0;
  opacity: 0;
}
.slide-down-enter-to {
  max-height: 200px;
  opacity: 1;
}
.slide-down-leave-from {
  max-height: 200px;
  opacity: 1;
}
.slide-down-leave-to {
  max-height: 0;
  opacity: 0;
}
.slide-down-enter-active,
.slide-down-leave-active {
  transition: all 0.3s ease;
}
</style>
