<template>
  <q-field
    ref="field"
    :model-value="profilePicturePreview"
    :rules="rules"
    class="profile-picture-field"
    no-error-icon
    borderless
  >
    <template #control>
      <div class="text-center q-mx-auto">
        <q-avatar
          @drop.prevent="onFileDrop"
          @dragover.prevent
          @click="$refs.profilePictureInput.click()"
          :size="avatarSize"
          color="dark"
          :class="avatarClasses"
        >
          <template v-if="profilePicturePreview">
            <q-img
              :src="profilePicturePreview"
              class="change-profile-picture"
            />
            <div class="absolute fit flex flex-center change-profile-picture-display" />
          </template>
          <div v-else class="column items-center text-accent">
            <q-icon :name="icon" size="0.6em" />
            <div v-if="!disable" class="text-caption">
              {{ addLabel }}
            </div>
          </div>
        </q-avatar>
        <input
          ref="profilePictureInput"
          v-show="false"
          @change="onFileUpload"
          :accept="acceptedImageFileTypes"
          type="file"
        />
      </div>
    </template>
  </q-field>
</template>

<script>
import { computed, defineComponent } from 'vue'

import useProfilePicture, { profilePictureProps, profilePictureEmits } from 'src/composables/profilePicture'

export default defineComponent({
  name: 'ProfilePictureUpload',
  props: {
    ...profilePictureProps,
    outline: Boolean,
    addLabel: {
      type: String,
      default: 'Add profile picture'
    }
  },
  emits: [...profilePictureEmits],
  setup (props, { emit }) {
    const avatarClasses = computed(() => {
      const classes = []

      if (props.disable) {
        classes.push('cursor-not-allowed no-pointer-events')
      } else {
        classes.push('cursor-pointer change-profile-picture')
      }

      if (props.outline) {
        classes.push('outline-avatar')
      }

      return classes
    })

    return {
      avatarClasses,
      ...useProfilePicture(props, { emit })
    }
  }
})
</script>

<style lang="scss" scoped>
.change-profile-picture:hover .change-profile-picture-display {
  visibility: visible;
  opacity: 1;
}

.change-profile-picture-display {
  visibility: hidden;
  opacity: 0;
  transition: visibility 0s, opacity 0.4s;
  background-color: rgba($color: #000, $alpha: 0.6);
  border-radius: 50%;
  z-index: 100;
  color: white;
}

.change-profile-picture-display:after {
  font-size: 12px;
  text-decoration: underline;
  content: 'Alterar Imagem';
}

.profile-picture-field:deep .q-field__messages > div {
  text-align: center;
}

.outline-avatar {
  outline: 2px solid var(--q-primary);
}
</style>
