import { defineStore } from 'pinia'

export const useRouterConfig = defineStore('routerConfig', {
  state: () => ({
    usePageTransition: false,
    essentialLinks: [
      {
        title: 'Users',
        caption: 'Users list',
        icon: 'receipt_long',
        showOnLoggedOut: false,
        link: '/users',
        root: '/users'
      }
    ]
  })
})
