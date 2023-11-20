import { defineStore } from 'pinia'

export const useViewSettings = defineStore('viewSettings', {
  state: () => ({
    pageHeight: 100
  })
})
