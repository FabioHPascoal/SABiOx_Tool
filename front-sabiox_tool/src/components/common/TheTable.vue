<template>
  <q-table
    ref="tableRef"
    @request="onRequest"
    v-model:pagination="innerPagination"
    :title="title"
    :columns="columns"
    :rows="rows"
    :grid="isResponsive"
    :loading="loading"
    :row-key="rowKey"
    :hide-pagination="hidePagination"
    class="the-table"
  >
    <template v-if="isResponsive" #item="props">
      <q-card bordered class="fit q-mb-md">
        <q-list dense class="q-gutter-y-xs q-py-sm">
          <q-item v-for="col in propsCols(props)" :key="col.name" class="justify-between">
            <q-item-section side>
              <q-item-label>{{ col.label }}</q-item-label>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-right break-word" caption>
                <slot v-if="mobileSlots.includes(col.name)" :name="`name-${col.name}`" :props="{ ...props, ...col, isResponsive }" />
                <span v-else>{{ (col.value === null || col.value === '' || typeof col.value === 'undefined') ? '-' : col.value }}</span>
              </q-item-label>
            </q-item-section>
          </q-item>
        </q-list>
      </q-card>
    </template>
    <template v-for="slot in tableSlots" #[slot.originalName]="props" :key="slot.name">
      <component :is="slot.is" :class="getColClasses({ col: props.col, row: props.row })" :style="props.col.style">
        <slot :name="slot.name" :props="{ ...props, isResponsive }" />
      </component>
    </template>
    <template #body-cell="{ value, col, row }">
      <q-td :class="getColClasses({ col, row })" :style="col.style">
        {{ (value === null || value === '' || typeof value === 'undefined') ? '-' : value }}
        <q-tooltip
          v-if="showColTooltip(col, 'body')"
          class="text-subtitle2"
          v-bind="getColTooltipPosition(col)"
        >
          <div v-html="getColTooltipText({ col, value, row })" />
        </q-tooltip>
      </q-td>
    </template>
  </q-table>
</template>

<script>
import { defineComponent, ref, toRef, computed, watch, useSlots } from 'vue'
import { useQuasar, QTh as QTableHeader, QTd as QTableData } from 'quasar'

const formatDesktopName = (slot) => slot.replace('body-cell-', 'name-')
const formatDesktopHeaderName = (slot) => slot.replace('header-cell-', 'header-')

const defaultPagination = {
  page: 1,
  count: 50
}

export default defineComponent({
  name: 'TheTable'
})
</script>

<script setup>

const props = defineProps({
  title: String,
  columns: Array,
  rows: Array,
  loading: Boolean,
  noResponsive: {
    type: Boolean,
    default: false
  },
  pagination: Object,
  breakpoint: {
    type: String,
    default: 'sm',
    validator: (val) => ['xs', 'sm', 'md', 'lg', 'xl'].includes(val)
  },
  rowKey: [String, Function],
  hidePagination: Boolean
})

const emit = defineEmits(['request'])

const slots = useSlots()

const $q = useQuasar()

const tableRef = ref(null)

const innerPagination = ref({})
const paginationRef = toRef(props, 'pagination')

Object.assign(innerPagination.value, props.pagination ?? defaultPagination)

watch(
  () => paginationRef.value,
  newValue => {
    Object.assign(innerPagination.value, newValue)
  },
  { deep: true }
)

const onRequest = (props) => emit('request', props)

const isResponsive = computed(() => !props.noResponsive && !$q.screen.gt[props.breakpoint])

const _slots = computed(() => [...Object.keys(slots)])

const headerSlots = computed(() => {
  return _slots.value
    .filter(slot => slot.startsWith('header-'))
    .map(slot => slot.replace('header-', 'header-cell-'))
})

const desktopSlots = computed(() => {
  return _slots.value
    .filter(slot => slot.startsWith('name-') && !slot.endsWith('-mobile'))
    .map(slot => slot.replace('name-', 'body-cell-'))
})

const tableSlots = computed(() => {
  return [
    ...headerSlots.value.map(slot => ({
      is: QTableHeader,
      name: formatDesktopHeaderName(slot),
      originalName: slot
    })),
    ...desktopSlots.value.map(slot => ({
      is: QTableData,
      name: formatDesktopName(slot),
      originalName: slot
    }))
  ]
})

const mobileSlots = computed(() => {
  const slots = []
  _slots.value
    .filter(slot => slot.startsWith('name-'))
    .forEach(slot => {
      if (!Object.prototype.hasOwnProperty.call(slots, `${slot}-mobile`)) {
        slots.push(slot.replace('name-', ''))
      }
    })

  return slots.map(slot => slot.replace('name-', ''))
})

const bottomSlots = computed(() => {
  return _slots.value
    .filter(slot => slot.endsWith('-mobile'))
    .map(slot => slot.replace('-mobile', ''))
})

const propsCols = (props, mobile = false) => {
  return props.cols.filter(col => bottomSlots.value.includes('name-' + col.name) === mobile)
}

const showColTooltip = (col, type) => {
  switch (type) {
    case 'header':
      return 'headerTooltip' in col
    case 'body':
      return 'tooltip' in col
  }
}

const getColTooltipText = ({ col, value, row }) => {
  value ||= col.value
  let result = null

  switch (typeof col.tooltip) {
    case 'boolean':
      if (col.tooltip === true) result = value
      break
    case 'string':
      result = col.tooltip
      break
    case 'function':
      result = col.tooltip(value, row)
      break
  }

  return result !== null && typeof result !== 'undefined' && result !== ''
    ? String(result)
    : '-'
}

const getColClasses = ({ col, row }) => {
  return typeof col.classes === 'function'
    ? col.classes(row)
    : col.classes
}

const getColTooltipPosition = (col) => {
  if (!['left', 'right'].includes(col.align)) {
    return {
      offset: [0, -15]
    }
  }

  return {
    left: {
      anchor: 'bottom start',
      self: 'top start',
      offset: [-15, -15]
    },
    right: {
      anchor: 'bottom end',
      self: 'top end',
      offset: [-15, -15]
    }
  }[col.align]
}

</script>
