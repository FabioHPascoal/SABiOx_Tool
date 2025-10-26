const routes = [
  {
    path: '/',
    component: () => import('layouts/BlankLayout.vue'),
    children: [
      {
        path: '',
        name: 'Login',
        component: () => import('src/pages/LoginPage.vue'),
        meta: { guestOnly: true }
      },
      {
        path: 'register',
        name: 'Register',
        component: () => import('src/pages/RegisterPage.vue'),
        meta: { guestOnly: true }
      }
    ]
  },
  {
    path: '/app',
    component: () => import('layouts/MainLayout.vue'),
    meta: { authOnly: true },
    children: [
      {
        path: '',
        name: 'App.Home',
        component: () => import('src/pages/app/ProjectManagerPage.vue')
      },
      {
        path: 'project/:id/lifeCycleManager',
        name: 'App.Project.LifeCycleManager',
        component: () => import('src/pages/app/project/LifeCycleManagerPage.vue')
      },
      {
        path: 'project/:id/definePurpose',
        name: 'App.Project.RequirementPhase.DefinePurpose',
        component: () => import('src/pages/app/project/requirementPhase/DefinePurposePage.vue')
      },
      {
        path: 'project/:id/identifyDomain',
        name: 'App.Project.RequirementPhase.IdentifyDomain',
        component: () => import('src/pages/app/project/requirementPhase/IdentifyDomainPage.vue')
      },
      {
        path: 'project/:id/elicitRequirements',
        name: 'App.Project.RequirementPhase.ElicitRequirements',
        component: () => import('src/pages/app/project/requirementPhase/ElicitRequirementsPage.vue')
      },
      {
        path: 'project/:id/identifySubdomains',
        name: 'App.Project.RequirementPhase.IdentifySubdomains',
        component: () => import('src/pages/app/project/requirementPhase/IdentifySubdomainsPage.vue')
      }
    ]
  },

  // 404
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
