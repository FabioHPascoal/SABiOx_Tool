import { boot } from 'quasar/wrappers'

import TheTable from 'src/components/common/TheTable.vue'
import TheTableActions from 'src/components/common/TheTableActions.vue'
import TheAvatar from 'src/components/common/TheAvatar.vue'
import PageTitle from 'src/components/common/PageTitle.vue'
import DialogHeader from 'src/components/dialogs/common/DialogHeader.vue'
import StatusIcon from 'src/components/projects/StatusIcon.vue'
import ExpansibleCard from 'src/components/common/ExpansibleCard.vue'
import KanbanCard from 'src/components/projects/KanbanCard.vue'

export default boot(async ({ app }) => {
  app.component('the-table', TheTable)
  app.component('the-table-actions', TheTableActions)
  app.component('the-avatar', TheAvatar)
  app.component('page-title', PageTitle)
  app.component('dialog-header', DialogHeader)
  app.component('status-icon', StatusIcon)
  app.component('expansible-card', ExpansibleCard)
  app.component('kanban-card', KanbanCard)
})
