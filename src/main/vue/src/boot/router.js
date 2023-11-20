import { boot } from 'quasar/wrappers';
import {useRouterConfig} from 'src/stores/useRouterConfig.js';

let routerInstance = null;
const routerConfig = useRouterConfig();
export default boot(({ router }) => {
  routerInstance = router;
  router.afterEach((to, from) => {
    let fromRootPath = `/${from.path.split('/')[1]}`;
    let toRootPath = `/${to.path.split('/')[1]}`;

    if (fromRootPath === toRootPath) {
      routerConfig.usePageTransition = true;
      if (from.path === to.path && to.path !== toRootPath){
        router.push(toRootPath)
      }
    } else {
      routerConfig.usePageTransition = false;
    }


    updateNavItem();
    function updateNavItem(){
      const navItemIndex = routerConfig.essentialLinks.findIndex(item => item.root === toRootPath);
      if (navItemIndex === -1) return;
      routerConfig.essentialLinks[navItemIndex].link = to.path;
    }

  })
})

export { routerInstance };
