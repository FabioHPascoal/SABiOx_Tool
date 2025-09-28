<template>
  <q-card
    v-ripple
    @click="onClick(project)"
    bordered
    class="q-mb-md cursor-pointer q-hoverable"
  >
    <span class="q-focus-helper"></span>
    <q-list dense class="q-gutter-y-xs q-py-sm">
      <q-item class="justify-between">
        <q-item-section>
          <q-item-label lines="1" class="text-subtitle2 primary">
            {{ project.title }}
          </q-item-label>
        </q-item-section>
        <q-item-section side>
          <the-avatar :avatar-url="project.owner.avatarUrl" />
        </q-item-section>
      </q-item>

      <!-- <q-separator inset /> -->

      <!-- <q-item class="q-mt-md q-mb-sm">
        <q-item-section>
          <q-item-label v-if="!manifestation?.isPublic" caption lines="2">
            <q-icon name="vpn_lock" size="xs" color="primary" />
            Manifestação Privada
            <template v-if="!isOmbudsman">
              (somente você e nossos ouvidores podem visualizá-la).
            </template>
          </q-item-label>
          <q-item-label
            :class="[
              'text-subtitle2',
              'text-bold',
              `text-${getTypeChipProps.color}`
            ]"
          >
            <div class="row items-center">
              <q-icon
                :name="getTypeChipProps.icon"
                :color="getTypeChipProps.color"
                size="xs"
                class="q-mr-xs"
              />
              {{ getTypeChipProps.label }}
            </div>
          </q-item-label>
          <q-item-label lines="2" class="text-subtitle1 text-weight-medium text-uppercase">
            {{ manifestation.title }}
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-item class="q-mt-none">
        <q-item-section v-if="!hideStatus" side class="q-pr-none">
          <q-item-label>
            <the-chip v-bind="getStatusChipProps" dense />
          </q-item-label>
        </q-item-section>
        <q-item-section :class="{ 'q-ml-xs': !hideStatus }">
          <q-item-label>
            <the-chip v-bind="getPriorityChipProps" dense />
          </q-item-label>
        </q-item-section>
        <q-item-section side>
          <q-item-label>
            {{ manifestation.commentsAmount }}
            <q-icon name="forum" size="xs" />
          </q-item-label>
        </q-item-section>
      </q-item> -->
    </q-list>
  </q-card>
</template>

<script>
import { defineComponent } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  name: 'ManifestationCardSmall'
})
</script>

<script setup>

const props = defineProps({
  project: {
    type: Object,
    required: true
  }
})

// const authStore = useAuthStore()
// const { getUser } = storeToRefs(authStore)

// const isOwner = computed(() => {
//   const userID = getUser.value?.id

//   if (userID === props.project.owner.id) return true

//   return false
// })

const $router = useRouter()

const onClick = () => {
  $router.push({
    name: 'App.Project.RequirementPhase.DefinePurpose',
    params: { id: props.project?.projectId }
  })
}

</script>
