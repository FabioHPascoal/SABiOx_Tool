<!-- <template>
  <div>
    <q-drawer
      v-model="projectDrawerVal"
      side="left"
      :width="200"
      class="bg-Primary text-onPrimary desktop-drawer"
      elevated
    >
    <q-drawer
      v-model="modelVal"
      side="left"
      :behavior="isMobile ? 'mobile' : 'desktop'"
      :width="200"
      class="bg-Primary text-onPrimary desktop-drawer"
      elevated
    >
      <div v-if="isMobile" class="q-pr-xs q-pt-xs absolute-top-right z-top">
        <q-btn @click="modelVal = false" icon="close" flat round />
      </div>

      <drawer-link
      v-for="link in links"
      :key="link.name"
      v-bind="link"
      />

    </q-drawer>

    <q-btn
      class="fixed z-top"
      :style="{
        top: '50%',
        left: projectDrawerVal ? '200px' : '50px',
        transform: 'translateY(-50%)'
      }"
      dense
      round
      unelevated
      color="accent"
      :icon="projectDrawerVal ? 'chevron_left' : 'chevron_right'"
      @click="toggleProjectDrawer"
    />

  </div>

</template> -->

<!-- <q-header style="
    border-bottom: 2px solid var(--q-border);
    background-color: var(--q-background); 
    color: var(--q-onBackground)"> -->

<template>

  <div>
    <q-drawer
      style="border-right: 3px solid var(--q-border)"
      v-model="projectDrawerVal"
      side="left"
      :width="280"
      class="bg-onPrimary text-onPrimary desktop-drawer"
    >
      <!-- <drawer-link
      v-for="link in links"
      :key="link.name"
      v-bind="link"
      /> -->
    </q-drawer>

  </div>

  <div>
    <q-btn
      class="fixed z-top p-0"
      :ripple="false"
      :style="{
        top: '50%',
        left: projectDrawerVal ? `calc(280px - 7px)` : '-7px',
        transform: 'translateY(-50%)'
      }"
      dense
      round
      unelevated
      color="transparent"
      @click="toggleProjectDrawer"
    >
      <img
        :src="projectDrawerVal ? drawerClose : drawerOpen"
        alt="Toggle Drawer"
        width="20"
      />
    </q-btn>
  </div>

</template>

<script>
import { computed, defineComponent, ref } from 'vue'
import { useQuasar } from 'quasar'

import drawerOpen from 'src/assets/drawer-open-light.svg'
import drawerClose from 'src/assets/drawer-close-light.svg'

export default defineComponent({
  name: 'ProjectDrawer'
})
</script>

<script setup>
import DrawerLink from 'src/components/DrawerLink.vue'

const projectDrawerVal = ref(true)

const toggleProjectDrawer = () => {
  projectDrawerVal.value = !projectDrawerVal.value
}

</script>

<style lang="scss">
  body.desktop .q-hoverable:hover .q-focus-helper {
    background: inherit;
    opacity: 0;
  }
</style>
