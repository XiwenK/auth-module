const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path: '/users',
        component: () => import('pages/Users/UserPage.vue'),
      },
      { path: '/login', component: () => import('pages/Login/LoginPage.vue') },
      { path: '/register', component: () => import('pages/Register/RegisterPage.vue') }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
