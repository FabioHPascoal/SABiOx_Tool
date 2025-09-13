import { Dialog } from 'quasar'
import ConfirmationDialog from 'src/components/dialogs/common/ConfirmationDialog.vue'

export const ConfirmationDialogExitConfirmation = ({
  title = 'Alterações não salvas',
  message = 'Existem alterações que não foram salvas, deseja sair mesmo assim?'
} = {}) => {
  return new Promise((resolve) => {
    Dialog.create({
      component: ConfirmationDialog,
      componentProps: {
        title,
        message,
        okLabel: 'Voltar à edição',
        cancelLabel: 'Sair',
        persistent: true,
        reverse: true
      }
    })
      .onOk(() => {
        resolve(false)
      })
      .onCancel(() => {
        resolve(true)
      })
  })
}
