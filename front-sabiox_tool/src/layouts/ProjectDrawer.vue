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
              @click="showDialog = true"
            >
              <q-icon name="settings" color="onBackground" />
            </q-btn>
          </q-item-section>
        </q-item>

        <q-separator color="grey" inset/>

        <q-item>
          <q-item-label class="text-onBackground break-word-justify">
            {{ projectStore.project?.description || 'Project Description' }}
          </q-item-label>
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
      v-if="showDialog === false"
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

    <q-dialog v-model="showDialog" persistent>
      <q-card style="min-width: 500px">
        <q-card-section class="text-h6">
          Project Settings
        </q-card-section>

        <q-separator />

        <q-card-section class="q-gutter-md">
          <q-item-label class="text-subtitle1">
            Edit project details
          </q-item-label>

          <q-input
            v-model="projectForm.title"
            label="Title"
            type="textarea"
            outlined
            autogrow
            dense
            :disable="!isOwner"
          />
          <q-input
            v-model="projectForm.description"
            label="Description"
            type="textarea"
            outlined
            autogrow
            dense
            :disable="!isOwner"
          />

          <div class="row justify-end">
            <q-btn
              v-if="isOwner"
              no-caps
              label="Save"
              color="primary"
              @click="saveProjectChanges"
              :loading="saving"
            />
          </div>
        </q-card-section>

        <q-separator />

        <q-card-section class="q-gutter-md">
          <q-item-label class="text-subtitle1">
            Project Members
          </q-item-label>

          <div v-for="member in projectStore.project.members" :key="member.id" class="q-mb-sm flex items-center">
            <q-avatar size="36px">
              <img :src="member.avatarUrl" alt="avatar" />
            </q-avatar>
            <span class="q-ml-sm">{{ member.name }}</span>
          </div>
        </q-card-section>

        <q-separator />

        <q-card-section class="q-gutter-md">
          <q-item-label class="text-subtitle1">Add project member</q-item-label>

          <div class="row justify-between q-gutter-sm">
            <q-input
              v-model="newMemberEmail"
              label="Member email"
              type="email"
              outlined
              dense
              class="q-ml-none col"
              :disable="!isOwner"
            />
            <q-btn
              v-if="isOwner"
              no-caps
              label="Add"
              color="positive"
              @click="addMemberToProject"
              :disable="!newMemberEmail || addingMember"
              :loading="addingMember"
            />
          </div>
        </q-card-section>

        <q-separator />

        <q-card-actions align="right">
          <q-btn no-caps label="Close" color="grey" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>

  </div>
</template>

<script>
import { computed, defineComponent, ref, watch } from 'vue'
import { symOutlinedCycle } from '@quasar/extras/material-symbols-outlined'

import drawerOpen from 'src/assets/drawer-open-light.svg'
import drawerClose from 'src/assets/drawer-close-light.svg'

export default defineComponent({
  components: { ProjectDrawerLinks },
  name: 'ProjectDrawer'
})
</script>

<script setup>
import { api } from 'src/boot/axios'
import { useQuasar } from 'quasar'
import { useProjectStore } from 'src/stores/project'
import { useAuthStore } from 'src/stores/auth'
import ProjectDrawerLinks from './ProjectDrawerLinks.vue'
import DrawerLink from 'src/components/DrawerLink.vue'

const $q = useQuasar()
const authStore = useAuthStore()
const projectStore = useProjectStore()
const projectDrawerVal = ref(true)
const saving = ref(false)
const showDialog = ref(false)
const projectForm = ref({ title: '', description: '' })
const newMemberEmail = ref('')
const addingMember = ref(false)

const selectedLifeCycle = computed(() => projectStore.getSelectedLifeCycle)
const selectedActivities = computed(() => {
  return selectedLifeCycle.value?.activities || {}
})

watch(showDialog, (val) => {
  if (val && projectStore.project) {
    projectForm.value.title = projectStore.project.title
    projectForm.value.description = projectStore.project.description
  }
})

const isOwner = computed(() => {
  return authStore.user?.id === projectStore.project?.ownerId
})

const addMemberToProject = async () => {
  if (!newMemberEmail.value || !isOwner.value) return

  const existingMember = projectStore.project.members.find(
    (member) => member.email?.toLowerCase() === newMemberEmail.value.toLowerCase()
  )

  if (existingMember) {
    $q.notify({
      type: 'warning',
      message: 'This user is already a member of the project.'
    })
    return
  }

  try {
    addingMember.value = true
    await api.post(`/project/${projectStore.project.projectId}`, { email: newMemberEmail.value })

    await projectStore.fetchProject(projectStore.project.projectId)
    newMemberEmail.value = ''

    $q.notify({
      type: 'positive',
      message: 'Member added successfully!'
    })
  } catch (err) {
    console.error(err)

    if (err.response) {
      const status = err.response.status
      const message = err.response.data?.message || 'Error adding member.'

      if (status === 401 || status === 403) {
        $q.notify({
          type: 'negative',
          message: 'You are not authorized to add members to this project.'
        })
      } else if (status === 404) {
        $q.notify({
          type: 'warning',
          message: 'User not found.'
        })
      } else if (status === 409) {
        $q.notify({
          type: 'warning',
          message: 'This user is already a member of the project.'
        })
      } else {
        $q.notify({
          type: 'negative',
          message
        })
      }
    } else {
      $q.notify({
        type: 'negative',
        message: 'Unable to connect to the server.'
      })
    }
  } finally {
    addingMember.value = false
  }
}

const saveProjectChanges = async () => {
  if (!isOwner.value) return

  try {
    saving.value = true
    const payload = {
      title: projectForm.value.title,
      description: projectForm.value.description
    }
    const { data } = await api.put(`/project/${projectStore.project.projectId}`, payload)
    projectStore.project = data
    showDialog.value = false
  } catch (err) {
    console.error(err)
  } finally {
    saving.value = false
  }
}

const toggleProjectDrawer = () => {
  projectDrawerVal.value = !projectDrawerVal.value
}

const linksProject = computed(() => {
  const acts = selectedActivities.value

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

.break-word-justify {
  white-space: normal;
  word-break: break-word;
  overflow-wrap: anywhere;
  text-align: justify;
}
</style>
