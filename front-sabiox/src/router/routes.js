const routes = [
  {
    path: '/',
    component: () => import('layouts/BlankLayout.vue'),
    children: [
      {
        path: '',
        name: 'Login',
        component: () => import('src/pages/LoginPage.vue'),
        meta: {
          guestOnly: true
        }
      },
      {
        path: 'register',
        name: 'Register',
        component: () => import('src/pages/RegisterPage.vue'),
        meta: {
          guestOnly: true
        }
      }
    ]
  },
  {
    path: '/app',
    component: () => import('layouts/MainLayout.vue'),
    meta: {
      authOnly: true
    },
    children: [
      {
        path: '',
        name: 'App.Home',
        component: () => import('pages/app/HomePage.vue')
      }
    ]
  },

  // Error 404
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes