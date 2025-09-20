import { boot } from 'quasar/wrappers'

import TheTable from 'src/components/common/TheTable.vue'
import TheTableActions from 'src/components/common/TheTableActions.vue'
import TheAvatar from 'src/components/common/TheAvatar.vue'
import PageTitle from 'src/components/common/PageTitle.vue'
import DialogHeader from 'src/components/dialogs/common/DialogHeader.vue'

export default boot(async ({ app }) => {
  app.component('the-table', TheTable)
  app.component('the-table-actions', TheTableActions)
  app.component('the-avatar', TheAvatar)
  app.component('page-title', PageTitle)
  app.component('dialog-header', DialogHeader)
})
