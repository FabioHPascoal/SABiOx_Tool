import { useQuasar } from 'quasar'
import { computed, reactive } from 'vue'
import isEqual from 'lodash.isequal'
import { ConfirmationDialogExitConfirmation as confirmExit } from 'src/utils/dialogs'

//  * @param {function} emits.onOKClick

/**
 *
 * @param {object} emits
 * @param {function} emits.onDialogHide

 * @param {function} emits.onCancelClick
 * @param {function} [onClose]
 * @returns
 */

export const useDialog = ({ onDialogHide, onCancelClick }, onClose) => {
  const $q = useQuasar()

  const dialogProps = reactive({
    position: computed(() => $q.screen.gt.xs ? 'standard' : 'bottom'),
    noEscDismiss: !!onClose,
    noBackdropDismiss: !!onClose
  })

  const onCloseDialog = typeof onClose === 'function'
    ? () => onClose({ confirmExit, isEqual })
    : onCancelClick

  const dialogListeners = {
    hide: onDialogHide,
    shake: onCloseDialog
  }

  return {
    dialogProps,
    dialogListeners,
    onCloseDialog
  }
}
