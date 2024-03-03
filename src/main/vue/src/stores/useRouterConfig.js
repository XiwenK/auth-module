import { defineStore } from 'pinia'

export const useRouterConfig = defineStore('routerConfig', {
  state: () => ({
    usePageTransition: false,
    essentialLinks: [
      // {
      //   title: 'Groups',
      //   caption: 'Groups list',
      //   icon: 'http',
      //   showOnLoggedOut: false,
      //   link: '/groups',
      //   root: '/groups'
      // },
      {
        title: 'Users',
        caption: 'Users list',
        icon: 'receipt_long',
        showOnLoggedOut: false,
        link: '/users',
        root: '/users'
      },
      // {
      //   title: 'Spaces',
      //   caption: 'Spaces List',
      //   icon: 'calculate',
      //   showOnLoggedOut: false,
      //   link: '/spaces',
      //   root: '/spaces'
      // },
      // {
      //   title: 'Analytics',
      //   caption: 'Groups analytics',
      //   icon: 'analytics',
      //   showOnLoggedOut: false,
      //   link: '/analytics',
      //   root: '/analytics'
      // },
      // {
      //   title: 'Resources',
      //   caption: 'Resources',
      //   icon: 'trolley',
      //   showOnLoggedOut: false,
      //   link: '/resources',
      //   root: '/resources'
      // },
      // {
      //   title: 'Settings',
      //   caption: 'Settings',
      //   icon: 'settings',
      //   showOnLoggedOut: false,
      //   link: '/settings',
      //   root: '/settings'
      // }
    ]
  }),
  getters: {

  }
})
