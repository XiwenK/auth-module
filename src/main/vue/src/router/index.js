import { route } from 'quasar/wrappers'
import { createRouter, createMemoryHistory, createWebHistory, createWebHashHistory } from 'vue-router'
import routes from './routes'
import { useUserStore } from 'stores/user';

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

const LOGIN_PATH = '/login';
const REGISTER_PATH = '/register';
const HOME_PATH = '/groups';
const REG_PATH = '/reg';

const WHITE_PATH_LIST = [LOGIN_PATH, REGISTER_PATH, REG_PATH];


export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory)

  console.log(process.env.SERVER);
  console.log(process.env.VUE_ROUTER_MODE);
  console.log(process.env.VUE_ROUTER_BASE);

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath

    history: createHistory(process.env.VUE_ROUTER_BASE)
  })

  Router.beforeEach((to, from, next) => {
    const userStore = useUserStore();
    const token = userStore.getToken;
    if (token) {
      if ([LOGIN_PATH, REGISTER_PATH].includes(to.path)) {
        next(HOME_PATH);
        return;
      }
      next();
    } else {
      WHITE_PATH_LIST.forEach((path) => {
        if (to.path.startsWith(path)) {
          next();
          return;
        }
      })
      next(LOGIN_PATH);
    }
  });

  return Router
})
