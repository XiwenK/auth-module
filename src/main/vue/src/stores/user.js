import { defineStore } from 'pinia';
import {TOKEN_KEY} from "src/enums/cacheEnums";
import { getAuthCache, setAuthCache } from 'src/utils/auth';

export const useUserStore = defineStore('user', {
  state: () => ({
      token: undefined
  }),
  actions: {
    login (data) {
      this.setToken(data?.accessToken)
    },
    logout () {
      this.setToken(undefined);
    },
    setToken(info) {
      this.token = info ? info : ''; // for null or undefined value
      setAuthCache(TOKEN_KEY, info);
    },
  },
  getters: {
    getToken: (state) => {
      return state.token || getAuthCache(TOKEN_KEY)
    }
  }
})
