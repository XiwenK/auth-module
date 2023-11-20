import {loadDataWithPost} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';

const login = ({ login, password }) => {
  return loadDataWithPost(endpoints.login, {
    username: login,
    password: password
  }).then(response => {
    if (response.accessToken) {
      localStorage.setItem('user', JSON.stringify(response));
      return response;
    }
  });
}

const register = ({ login, email, password }) => {
  return loadDataWithPost(endpoints.register, {
    username: login,
    email: email,
    password: password
  });
}

const logout = () => {
  // userStore.logout();
  return Promise.resolve();
}

export default { login, logout, register };
