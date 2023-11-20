import { defineStore } from 'pinia'

export const useRouterConfig = defineStore('routerConfig', {
  state: () => ({
    usePageTransition: false,
    essentialLinks: [
      {
        title: 'Groups',
        caption: 'Groups List',
        icon: 'groups',
        showOnLoggedOut: false,
        link: '/groups',
        root: '/groups'
      },
      {
        title: 'Analytics',
        caption: 'Groups analytics',
        icon: 'analytics',
        showOnLoggedOut: false,
        link: '/analytics',
        root: '/analytics'
      },
      {
        title: 'Resources',
        caption: 'Resources',
        icon: 'trolley',
        showOnLoggedOut: false,
        link: '/resources',
        root: '/resources'
      },
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
