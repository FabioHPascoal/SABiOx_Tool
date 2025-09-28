<template>
  <!-- <q-drawer
    v-model="modelVal"
    side="right"
    :behavior="isMobile ? 'mobile' : 'desktop'"
    :width="200"
    class="bg-Primary text-onPrimary desktop-drawer"
    elevated
  > -->
  <q-drawer
    v-model="modelVal"
    side="right"
    behavior="mobile"
    :width="200"
    class="bg-primary text-onPrimary"
    elevated
  >
    <div v-if="isMobile" class="q-pr-xs q-pt-xs absolute-top-right z-top">
      <q-btn @click="modelVal = false" icon="close" flat round />
    </div>
    <q-list>
      <q-item-label
        v-if="isMobile"
        header
        class="text-center"
      >
        <router-link :to="{ name: 'App.Home' }" class="flex flex-center">
          <app-logo dark style="width: 125px" />
        </router-link>
      </q-item-label>
      <drawer-link
        v-for="link in links"
        :key="link.name"
        v-bind="link"
      />
    </q-list>
  </q-drawer>
</template>

<script>
import { computed, defineComponent } from 'vue'
import { useQuasar } from 'quasar'

export default defineComponent({
  name: 'AppDrawer'
})
</script>

<script setup>
import DrawerLink from 'src/components/DrawerLink.vue'
import AppLogo from 'src/components/common/AppLogo.vue'

const props = defineProps({
  modelValue: Boolean,
  links: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['update:model-value'])

const $q = useQuasar()

const modelVal = computed({
  get: () => props.modelValue,
  set: (newValue) => emit('update:model-value', newValue)
})

const isMobile = computed(() => $q.screen.lt.md)

</script>
