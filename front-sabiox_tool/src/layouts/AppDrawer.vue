

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'AppDrawer'
})
</script>

<script setup>

// const links = computed(() => {
//   const allDrawerLinks = [
//     {
//       name: 'requirements',
//       label: 'Requirements',
//       to: { name: 'App.Project.Requirements' },
//     },
//     {
//       name: 'setup',
//       label: 'Setup',
//       to: { name: 'App.Project.Setup' }
//     },
//     {
//       name: 'capture',
//       label: 'Capture',
//       to: { name: 'App.Project.Capture' }
//     },
//     {
//       name: 'design',
//       label: 'Design',
//       to: { name: 'App.Project.Design' }
//     },
//     {
//       name: 'implementation',
//       label: 'Implementation',
//       to: { name: 'App.Project.Implementation' },
//     }
//   ]

//   return allDrawerLinks.filter((link) => {
//     return typeof link.showIf === 'function'
//       ? link.showIf()
//       : typeof link.showIf === 'undefined' || !!link.showIf
//   })
// })

</script>
