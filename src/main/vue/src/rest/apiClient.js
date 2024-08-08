import axios from 'axios';
import {clearAuthCache} from 'src/utils/auth';
import {JWS_TOKEN_EXPIRED} from "src/rest/constants";
import authService from "pages/Login/service/authService";
import {useUserStore} from "stores/user";
import endpoints from "src/rest/endpoints";

const userStore = useUserStore();

const apiClient = axios.create({
  baseURL: process.env.API_URL
});

function isTokenExpired(error) {
  return error.response?.status === 401 && error.response?.data?.code === JWS_TOKEN_EXPIRED;
}

function isRefreshTokenRequest(config) {
  return config.url === endpoints.refreshToken;
}

function authHeader() {
  let token = userStore.getToken;
  if (token) {
    return {Authorization: 'Bearer ' + token};
  } else {
    return {};
  }
}

apiClient.interceptors.request.use(function (config) {
  config.headers = authHeader();
  return config;
});

apiClient.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  const req = error.config;
  if (isTokenExpired(error)) {
    if (isRefreshTokenRequest(req)) {
      clearAuthCache();
      window.location.href = '/login?expired=true';
    }
    return authService.refreshToken(userStore.getRefreshToken).then(response => {
      userStore.login(response);
      return apiClient.request(req);
    });
  }
  if (error.response?.status === 401) {
    clearAuthCache();
  }
  return Promise.reject(error);
});

export default apiClient;
