import { defineStore } from 'pinia';
import {REFRESH_TOKEN_KEY, TOKEN_KEY} from "src/enums/cacheEnums";
import { getAuthCache, setAuthCache } from 'src/utils/auth';

export const useUserStore = defineStore('user', {
  state: () => ({
      token: undefined,
      refreshToken: undefined
  }),
  actions: {
    login (data) {
      this.setToken(data?.token)
      this.setRefreshToken(data?.refreshToken)
    },
    logout () {
      this.setToken(undefined);
    },
    setToken(info) {
      this.token = info ? info : ''; // for null or undefined value
      setAuthCache(TOKEN_KEY, info);
    },
    setRefreshToken(info) {
      this.refreshToken = info ? info : ''; // for null or undefined value
      setAuthCache(REFRESH_TOKEN_KEY, info);
    }
  },
  getters: {
    getToken: (state) => {
      return state.token || getAuthCache(TOKEN_KEY)
    },
    getRefreshToken: (state) => {
      return state.refreshToken || getAuthCache(REFRESH_TOKEN_KEY)
    }
  }
})
