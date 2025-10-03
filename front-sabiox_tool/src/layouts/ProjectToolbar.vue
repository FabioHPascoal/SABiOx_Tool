<!-- <template>
    <div class="bg-background" style="height: 50px; border-bottom: 3px solid var(--q-border)">
      
    </div>
</template> -->

<template>
  <div
    class="q-py-xs row no-wrap justify-center"
    style="border-bottom: 3px solid var(--q-border)"
  >
    <q-btn
      v-for="action in actions"
      class="q-mx-xs"
      :key="action.label"
      :ripple="false"
      dense
      unelevated
      :toggle="action.toggleble"
      v-model="action.model"
      :color="getButtonColor(action)"
      @click="action.onClick(action)"
    >
      <q-icon
        :name="action.icon"
        :color="getIconColor(action)"
      />
      <q-tooltip>{{ action.label }}</q-tooltip>
    </q-btn>
  </div>
</template>

<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  projectKanban: Boolean
})

const emit = defineEmits(['update:projectKanban'])

const actions = reactive([
  {
    label: 'Normal Action',
    icon: 'home',
    toggleble: false,
    model: false,
    onClick: () => console.log('Cliquei Home')
  },
  {
    label: 'Toggle Kanban View',
    icon: 'visibility',
    toggleble: true,
    model: props.projectKanban, // estado inicial sincronizado
    onClick: (action) => {
      emit('update:projectKanban', !action.model)
    }
  }
])

// Mantém sincronizado de fora pra dentro
watch(
  () => props.projectKanban,
  (val) => {
    actions[1].model = val
  }
)

// Cor do botão
const getButtonColor = (action) => {
  return action.model ? 'btnPressed' : 'bg-Background'
}

// Cor do ícone
const getIconColor = (action) => {
  return action.model ? 'onBackground' : 'accent'
}
</script>
