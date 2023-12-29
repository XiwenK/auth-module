const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path: '/channels',
        component: () => import('pages/Channels/ChannelPage.vue'),
        children: [
          {
            path: '/channels/:channelId',
            component: () => import('pages/Channels/components/ChannelDetailsPane.vue'),
          }
        ]
      },
      {
        path: '/transactions',
        component: () => import('pages/Transactions/TransactionPage.vue'),
      },
      {
        path: '/groups',
        component: () => import('pages/Groups/GroupsPage.vue'),
        children: [
          {
            path: '/groups/:groupId',
            component: () => import('pages/Students/GroupDetailsPane.vue'),
          }
        ]
      },
      {
        path: '/resources',
        component: () => import('pages/Resources/ResourcesPage.vue'),
      },
      { path: '/login', component: () => import('pages/Login/LoginPage.vue') },
      { path: '/register', component: () => import('pages/Register/RegisterPage.vue') },
      // { path: '/goods', component: () => import('pages/Goods/GoodsPage.vue')},
      // { path: '/orders', component: () => import('pages/Orders/OrdersPage.vue')},
    ]
  },
  { path: '/reg/:shortUrl', component: () => import('pages/Groups/RegForm.vue') },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
