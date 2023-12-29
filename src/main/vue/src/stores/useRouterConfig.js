import { defineStore } from 'pinia'

export const useRouterConfig = defineStore('routerConfig', {
  state: () => ({
    usePageTransition: false,
    essentialLinks: [
      {
        title: 'Channels',
        caption: 'Channel list',
        icon: 'http',
        showOnLoggedOut: false,
        link: '/channels',
        root: '/channels'
      },
      {
        title: 'Transactions',
        caption: 'Transactions list',
        icon: 'receipt_long',
        showOnLoggedOut: false,
        link: '/transactions',
        root: '/transactions'
      },
      {
        title: 'Rules Engine',
        caption: 'Groups List',
        icon: 'calculate',
        showOnLoggedOut: false,
        link: '/groups',
        root: '/groups'
      },
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
      {
        title: 'Settings',
        caption: 'Settings',
        icon: 'settings',
        showOnLoggedOut: false,
        link: '/settings',
        root: '/settings'
      }
    ]
  }),
  getters: {

  }
})
