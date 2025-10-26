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
      <div class="column fit">
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

        <div>
          <project-drawer-links
            v-for="section in linksProject"
            :key="section.title"
            v-bind="section"
          />
        </div>

        <div class="q-mt-auto">
          <q-separator color="border" />

          <DrawerLink
            label="Life Cyle Manager"
            :to="{ name:'App.Project.LifeCycleManager' }"
            :icon="symOutlinedCycle"
            color="onBackground"
            active-class="bg-btnPressed text-onBackground"
          />
        </div>
      </div>
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
import { computed, defineComponent, ref, onMounted } from 'vue'
import { symOutlinedCycle } from '@quasar/extras/material-symbols-outlined'

import drawerOpen from 'src/assets/drawer-open-light.svg'
import drawerClose from 'src/assets/drawer-close-light.svg'

export default defineComponent({
  components: { ProjectDrawerLinks },
  name: 'ProjectDrawer'
})
</script>

<script setup>
import { useProjectStore } from 'src/stores/project'
import ProjectDrawerLinks from './ProjectDrawerLinks.vue'
import DrawerLink from 'src/components/DrawerLink.vue'

const projectStore = useProjectStore()

const projectDrawerVal = ref(true)

const toggleProjectDrawer = () => {
  projectDrawerVal.value = !projectDrawerVal.value
}

const selectedLifeCycle = computed(() => projectStore.getSelectedLifeCycle)

const latestActivities = computed(() => {
  return selectedLifeCycle.value?.activities || {}
})

const linksProject = computed(() => {
  const acts = latestActivities.value

  return [
    {
      title: 'Requirements',
      color: 'onBackground',
      links: [
        {
          name: 'define purpose',
          label: 'Define Purpose',
          to: { name: 'App.Project.RequirementPhase.DefinePurpose' },
          exact: true,
          stage: acts.REQ_PURP?.stage || 'NOT_STARTED'
        },
        {
          name: 'identify and size domain',
          label: 'Identify and Size Domain',
          to: { name: 'App.Project.RequirementPhase.IdentifyDomain' },
          exact: true,
          stage: acts.REQ_DOMN?.stage || 'NOT_STARTED'
        },
        {
          name: 'elicit requirements',
          label: 'Elicit Requirements',
          to: { name: 'App.Project.RequirementPhase.ElicitRequirements' },
          exact: true,
          stage: acts.REQ_ELIC?.stage || 'NOT_STARTED'
        },
        {
          name: 'identify subdomains',
          label: 'Identify Subdomains',
          to: { name: 'App.Project.RequirementPhase.IdentifySubdomains' },
          exact: true,
          stage: acts.REQ_SUBD?.stage || 'NOT_STARTED'
        }
      ]
    }
  ]
})
</script>


<style lang="scss" scoped>
body.desktop .q-hoverable:hover .q-focus-helper {
  background: inherit;
  opacity: 0;
}
</style>
