import axios from 'axios';
import { clearAuthCache } from 'src/utils/auth';

function authHeader() {
  const user = JSON.parse(localStorage.getItem('user'));
  if (user && user.token) {
    return { Authorization: 'Bearer ' + user.token };
  } else {
    return {};
  }
}

const apiClient = axios.create({
  baseURL: process.env.API_URL
});

apiClient.interceptors.request.use(function (config) {
  config.headers = authHeader();
  return config;
});

apiClient.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  if (error.response.status === 401) {
    clearAuthCache();
    window.location.href = '/login?expired=true';
  }
  return Promise.reject(error);
});

export default apiClient;
