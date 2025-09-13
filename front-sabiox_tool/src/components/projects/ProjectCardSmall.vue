<!-- <template>
  <q-card
    v-ripple
    @click="onClick(manifestation)"
    bordered
    class="q-mb-md cursor-pointer q-hoverable"
  >
    <span class="q-focus-helper"></span>
    <q-list dense class="q-gutter-y-xs q-py-sm">
      <q-item>
        <q-item-section side>
          <the-avatar :avatar-url="manifestation.author?.avatarUrl" :is-anonymous="manifestation.isAnonymous" />
        </q-item-section>
        <q-item-section>
          <q-item-label lines="1" class="text-subtitle2">
            {{ formatAuthor }}
          </q-item-label>
          <q-item-label caption>
            {{ prettifyDate(manifestation.createdAt) }}
          </q-item-label>
        </q-item-section>
        <q-item-section v-if="!hideRating" side top>
          <q-item-label>
            <q-rating
              :model-value="getRatingAverage"
              size="xs"
              color="orange"
              icon="star_border"
              icon-selected="star"
              icon-half="star_half"
              readonly
            />
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-separator inset />
      <q-item class="q-mt-md q-mb-sm">
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
      </q-item>
    </q-list>
  </q-card>
</template>

<script>
import { defineComponent, computed } from 'vue'
import { useAuthStore } from 'src/stores/auth'
import { useRouter } from 'vue-router'

import { prettifyDate } from 'src/utils/formatters'
import {
  MANIFESTATION_TYPES_PROPS,
  STATUS_OPTIONS_PROPS,
  PRIORITIES_OPTIONS_PROPS
} from 'src/constants/manifestations'

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

const $router = useRouter()

const formatAuthor = computed(() => {
  return props.manifestation.isAnonymous
    ? 'Anônimo'
    : props.manifestation.author?.name
})

const getTypeChipProps = computed(() => MANIFESTATION_TYPES_PROPS[props.manifestation.type])
const getStatusChipProps = computed(() => STATUS_OPTIONS_PROPS[props.manifestation.status])
const getPriorityChipProps = computed(() => PRIORITIES_OPTIONS_PROPS[props.manifestation.priority])

const getRatingAverage = computed(() => {
  return props.manifestation?.rating?.average || 0
})

const onClick = () => {
  $router.push({
    name: 'App.Manifestation',
    params: { id: props.manifestation?.id }
  })
}

</script> -->

<template>
  <div>
    <h1>Hello, Vue!</h1>
  </div>
</template>

<script>
export default {
  name: 'MinimalComponent'
}
</script>

<style scoped>
h1 {
  color: blue;
}
</style>
