<template>
  <div>
    <q-drawer
      style="border-right: 3px solid var(--q-border)"
      v-model="projectDrawerVal"
      side="left"
      behavior="desktop"
      :width="300"
      class="bg-onPrimary text-onPrimary desktop-drawer"
    >
      <q-item class="q-px-md q-pt-sm q-pb-sm items-center justify-between">
        <q-item-section>
          <q-item-label class="text-onBackground text-h6">
            {{ projectStore.project?.title || 'Project' }}
          </q-item-label>
        </q-item-section>
        <q-item-section side>
          <q-btn
            flat
            dense
            round
            size="md"
            color="background"
          >
            <q-icon name="settings" color="onBackground" />
          </q-btn>
        </q-item-section>
      </q-item>

      <q-separator style="height: 3px; background-color: var(--q-border)" />

      <drawer-link
        v-for="link in linksProject"
        :key="link.name"
        color='onBackground'
        v-bind="link"
      />
    </q-drawer>

    <q-btn
      class="fixed z-top p-0"
      :ripple="false"
      :style="{
        top: '50%',
        left: projectDrawerVal ? `calc(300px - 7px)` : '-7px',
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
import { useProjectStore } from 'src/stores/project'
import DrawerLink from 'src/components/DrawerLink.vue'

const projectStore = useProjectStore()

const projectDrawerVal = ref(true)

const toggleProjectDrawer = () => {
  projectDrawerVal.value = !projectDrawerVal.value
}

const linksProject = computed(() => [
  {
    name: 'define purpose',
    label: 'Define Purpose',
    to: { name: 'App.Project.RequirementPhase.DefinePurpose' },
    exact: true
  },
  {
    name: 'identify and size domain',
    label: 'Identify and Size Domain',
    to: { name: 'App.Project.RequirementPhase.IdentifyDomain' },
    exact: true
  },
  {
    name: 'elicit requirements',
    label: 'Elicit Requirements',
    to: { name: 'App.Project.RequirementPhase.ElicitRequirements' },
    exact: true
  },
  {
    name: 'identify subdomains',
    label: 'Identify Subdomains',
    to: { name: 'App.Project.RequirementPhase.IdentifySubdomains' },
    exact: true
  }
])

</script>

<style lang="scss">
  body.desktop .q-hoverable:hover .q-focus-helper {
    background: inherit;
    opacity: 0;
  }
</style>
