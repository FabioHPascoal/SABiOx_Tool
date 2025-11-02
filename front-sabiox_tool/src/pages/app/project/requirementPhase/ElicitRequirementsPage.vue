<template>
  <page-title title="Elicit Requirements" />

  <div class="q-pa-md">
    <div class="q-mb-md flex justify-between">
      <q-btn
        class="outlined-btn"
        label="Add Requirement"
        icon="add"
        padding="sm"
        no-caps
        @click="showDialog = true"
      />
      <q-btn-toggle
        class="custom-card"
        v-model="filterMode"
        no-caps
        toggle-color="primary"
        color="white"
        text-color="primary"
        padding="sm md"
        :options="[
          { label: 'All', value: FILTER_MODE_ALL},
          { label: 'Funcional', value: FILTER_MODE_FUNCTIONAL },
          { label: 'Non-funcional', value: FILTER_MODE_NON_FUNCTIONAL },
          { label: 'Domain', value: FILTER_MODE_DOMAIN }
        ]"
      />
    </div>

    <transition-group name="fade-move" tag="div" class="column q-gutter-y-md q-mx-none">
      <expansible-card
        v-for="requirement in filteredRequirements"
        :key="requirement.id"
        :title="requirement.description"
        :badge-label="getBadgeLabel(requirement.requirementType)"
        :badge-color="getBadgeColor(requirement.requirementType)"
        :user="requirement.user"
        :creation-date="requirement.creationDate"
      >
        <q-card
          flat
          bordered
          v-for="comment in requirement.comments"
          :key="comment.id"
          class="q-pa-sm q-mb-md bg-surface"
        >
          <div class="row q-gutter-sm">
            <!-- COLUNA ESQUERDA -->
            <div class="col-auto text-center comment-sidebar">
              <q-avatar size="42px" class="q-mb-xs">
                <img :src="comment.user.avatarUrl" :alt="comment.user.name" />
              </q-avatar>
              <div class="text-caption text-weight-medium text-onBackground">{{ comment.user.name }}</div>
              <q-badge
                :color="isOwner(comment.user) ? 'amber-14' : 'grey-5'"
                :label="isOwner(comment.user) ? 'Project Owner' : 'Project Member'"
              />
            </div>

            <!-- COLUNA DIREITA -->
            <div class="col comment-body column justify-between">
              <div class="text-body2 text-onBackground q-mb-sm">
                {{ comment.body }}
              </div>
              <div class="comment-footer row items-center justify-between q-pt-xs q-mt-xs">
                <div class="row items-center q-gutter-xs">
                  <q-btn
                    flat
                    dense
                    icon="thumb_up"
                    size="sm"
                    :color="hasUserRated(comment, 'LIKE') ? 'primary' : 'grey'"
                    @click="rateComment(requirement.id, comment.id, 'LIKE')"
                  />
                  <span class="text-caption text-grey-8">{{ comment.likeCount }}</span>

                  <q-btn
                    flat
                    dense
                    icon="thumb_down"
                    size="sm"
                    :color="hasUserRated(comment, 'DISLIKE') ? 'negative' : 'grey'"
                    @click="rateComment(requirement.id, comment.id, 'DISLIKE')"
                  />
                  <span class="text-caption text-grey-8">{{ comment.dislikeCount }}</span>
                </div>

                <div class="text-caption text-grey-7">
                  {{ formatDate(comment.creationDate) }}
                </div>
              </div>
            </div>
          </div>
        </q-card>

        <q-input
          v-model="commentInputs[requirement.id]"
          outlined
          autogrow
          placeholder="Add Comment"
          dense
        >
          <template v-slot:after>
            <q-btn
              round
              dense
              flat
              icon="send"
              @click="addComment(requirement.id)"
            />
          </template>
          <template v-slot:append>
            <q-icon
              name="close"
              @click="commentInputs[requirement.id] = ''"
              class="cursor-pointer"
            />
          </template>
        </q-input>

        
      </expansible-card>
    </transition-group>

    <q-dialog v-model="showDialog" persistent>
      <q-card style="min-width: 400px">
        <q-card-section class="text-h6">
          Add Requirement
        </q-card-section>

        <q-separator />

        <q-card-section class="q-gutter-md">
          <q-input
            v-model="form.description"
            label="Description"
            type="textarea"
            outlined
            autogrow
            dense
          />

        <q-select
          v-model="form.requirementType"
          label="Requirement Type"
          outlined
          dense
          emit-value
          map-options
          :options="[
            { label: 'Functional', value: 'FUNCTIONAL' },
            { label: 'Non-functional', value: 'NON_FUNCTIONAL' },
            { label: 'Domain', value: 'DOMAIN' }
          ]"
        />
        </q-card-section>

        <q-separator />

        <q-card-actions align="right">
          <q-btn flat label="Cancel" color="grey" v-close-popup />
          <q-btn
            label="Add"
            color="positive"
            :loading="saving"
            @click="addRequirement"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>

  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useProjectStore } from 'src/stores/project'
import { useAuthStore } from 'src/stores/auth'
import { api } from 'boot/axios'
import { useQuasar } from 'quasar'

const $q = useQuasar()
const authStore = useAuthStore()
const projectStore = useProjectStore()

const requirements = ref([])
const loading = ref(false)
const saving = ref(false)
const showDialog = ref(false)
const form = ref({ description: '', requirementType: '' })
const commentInputs = ref({})

const lifeCycle = computed(() => projectStore.getSelectedLifeCycle)

const fetchRequirements = async () => {
  if (!lifeCycle.value?.id) return
  loading.value = true
  try {
    const { data } = await api.get(`/project/life_cycle/${lifeCycle.value.id}/elicit_requirements`)
    requirements.value = data?.requirements || []
  } catch (err) {
    console.error('Failed to load requirements:', err)
    $q.notify({ type: 'negative', message: 'Failed to load requirements' })
  } finally {
    loading.value = false
  }
}

watch(() => lifeCycle.value?.id, (id) => {
  if (id) fetchRequirements()
})
onMounted(() => fetchRequirements())

const addRequirement = async () => {
  if (!form.value.description?.trim()) {
    $q.notify({ type: 'warning', message: 'Description is required' })
    return
  }

  if (!lifeCycle.value?.id) return

  saving.value = true
  try {
    const payload = { ...form.value }
    await api.post(`/project/life_cycle/${lifeCycle.value.id}/requirement`, payload)
    await fetchRequirements()
    await projectStore.fetchPhases()

    $q.notify({ type: 'positive', message: 'Requirement added successfully' })
    showDialog.value = false
    form.value = { title: '', description: '' }
  } catch (err) {
    console.error('Erro ao adicionar subdomínio:', err)
    $q.notify({ type: 'negative', message: 'Failed to add requirement' })
  } finally {
    saving.value = false
  }
}

const addComment = async (requirementId) => {
  const body = commentInputs.value[requirementId]?.trim()
  if (!body) {
    $q.notify({ type: 'warning', message: 'Comment cannot be empty' })
    return
  }

  try {
    const payload = { body }

    const { data } = await api.post(
      `/project/requirement/${requirementId}/comment`,
      payload
    )

    const req = requirements.value.find(r => r.id === requirementId)
    if (req) {
      // Adiciona o novo comentário ao topo (ou final, como preferir)
      req.comments.push({
        id: data.id,
        body: data.body,
        creationDate: data.creationDate,
        likeCount: data.likeCount,
        dislikeCount: data.dislikeCount,
        userHasRated: data.userHasRated,
        user: {
          id: authStore.user?.id,
          name: authStore.user?.name || 'You',
          avatarUrl: authStore.user?.avatarUrl || ''
        }
      })
    }

    commentInputs.value[requirementId] = '' // limpa campo
    $q.notify({ type: 'positive', message: 'Comment added successfully' })
  } catch (err) {
    console.error('Erro ao adicionar comentário:', err)
    $q.notify({ type: 'negative', message: 'Failed to add comment' })
  }
}


const rateComment = async (requirementId, commentId, type) => {
  try {
    const req = requirements.value.find(r => r.id === requirementId)
    if (!req) return
    const comment = req.comments.find(c => c.id === commentId)
    if (!comment) return

    const previous = comment.userHasRated

    if (previous === type) {
      if (type === 'LIKE') comment.likeCount--
      else comment.dislikeCount--
      comment.userHasRated = undefined
    } else {
      if (previous === 'LIKE') comment.likeCount--
      if (previous === 'DISLIKE') comment.dislikeCount--

      if (type === 'LIKE') comment.likeCount++
      if (type === 'DISLIKE') comment.dislikeCount++

      comment.userHasRated = type
    }

    const payload = comment.userHasRated
      ? { commentRatingType: comment.userHasRated }
      : { commentRatingType: null }
    await api.post(
      `/project/requirement/${requirementId}/comment/${commentId}`,
      payload
    )

  } catch (err) {
    console.error('Erro ao avaliar comentário:', err)
    $q.notify({ type: 'negative', message: 'Failed to rate comment' })
  }
}

const hasUserRated = (comment, type) => comment.userHasRated === type


const getBadgeLabel = (type) => {
  switch (type) {
    case 'FUNCTIONAL': return 'Functional'
    case 'NON_FUNCTIONAL': return 'Non-functional'
    case 'DOMAIN': return 'Domain'
    default: return 'Unknown'
  }
}

const getBadgeColor = (type) => {
  switch (type) {
    case 'FUNCTIONAL': return 'green'
    case 'NON_FUNCTIONAL': return 'teal'
    case 'DOMAIN': return 'blue'
    default: return 'grey'
  }
}

const FILTER_MODE_ALL = 'all'
const FILTER_MODE_FUNCTIONAL = 'functional'
const FILTER_MODE_NON_FUNCTIONAL = 'non_functional'
const FILTER_MODE_DOMAIN = 'domain'

const filterMode = ref(FILTER_MODE_ALL)

const filteredRequirements = computed(() => {
  if (filterMode.value === FILTER_MODE_ALL) {
    return requirements.value
  }
  if (filterMode.value === FILTER_MODE_FUNCTIONAL) {
    return requirements.value.filter(r => r.requirementType === 'FUNCTIONAL')
  }
  if (filterMode.value === FILTER_MODE_NON_FUNCTIONAL) {
    return requirements.value.filter(r => r.requirementType === 'NON_FUNCTIONAL')
  }
  if (filterMode.value === FILTER_MODE_DOMAIN) {
    return requirements.value.filter(r => r.requirementType === 'DOMAIN')
  }
  return null
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

const isOwner = (user) => user?.id === projectStore.project?.ownerId

</script>


<style scoped>
.outlined-btn {
border: 2px solid var(--q-border);
border-radius: 8px;
}
.fade-move-enter-active,
.fade-move-leave-active {
  transition: all 0.4s ease;
}

.fade-move-enter-from,
.fade-move-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

.fade-move-move {
  transition: transform 0.4s ease;
}

.comment-section {
  border: 1px solid var(--q-border);
  border-radius: 8px;
}

.comment-sidebar {
  width: 120px;
  min-width: 120px;
  background-color: #f9f9f9;
  border-right: 1px solid grey;
  padding: 8px;
  border-radius: 8px 0 0 8px;
}

.comment-body {
  white-space: pre-wrap;
  word-break: break-word;
}

.q-btn[disabled] {
  opacity: 0.7;
  cursor: default !important;
}

.bg-surface {
  background-color: var(--q-surface);
}
</style>
