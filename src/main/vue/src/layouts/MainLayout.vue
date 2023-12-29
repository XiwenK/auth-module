<template>
  <q-layout view="lHh lpR fFf" style="height: 100vh" class="main-layout">
    <q-header
      class="header"
      v-if="!isMobile"
    >
      <q-toolbar class="q-px-md">
        <div class="main-layout__manu-button">
          <q-btn
            v-if="$route.path !== '/login' && !isMobile"
            flat
            dense
            round
            icon="menu"
            aria-label="Menu"
            @click="toggleLeftDrawer()"
          />
        </div>
        <q-toolbar-title v-if="loggedIn">
          {{$t('appName')}}
        </q-toolbar-title>

        <q-btn v-if="loggedIn"
               flat
               :label="$t('buttons.logout')"
               @click="logout"/>
        <q-btn v-if="!loggedIn && $route.path !== '/login'"
               flat
               :label="$t('buttons.login')"
               @click="$router.push('/login')"/>
      </q-toolbar>
    </q-header>
    <q-drawer
      v-if="$route.path !== '/login'"
      v-model="leftDrawerOpen"
      side="left"
      :mini="miniState"
      show-if-above
      width="200"
    >
      <q-list>
        <div v-for="link in essentialLinks"
             :key="link.title">
          <essential-link
            v-if="link.showOnLoggedOut || loggedIn"
            @click="selected=link.title; $router.push(link.link)"
            v-bind="link"
          />
        </div>
      </q-list>
    </q-drawer>
    <q-footer
      v-if="isMobile && $route.path !== '/login'"
      bordered
      class="bg-white text-primary"
    >
      <q-tabs
        no-caps
        active-color="primary"
        indicator-color="transparent"
        class="text-grey"
      >
        <q-route-tab
          v-for="link in essentialLinks"
          :key="link.title"
          :to="link.link"
          :label="link.title"
          :icon="link.icon"
        />
      </q-tabs>
    </q-footer>
    <q-page-container>
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component
            :is="Component"
          />
        </keep-alive>
      </router-view>
    </q-page-container>
  </q-layout>
</template>

<script>
import {defineComponent, ref, computed} from 'vue'
import EssentialLink from '../components/EssentialLink.vue';
import {useI18n} from 'vue-i18n';
import {useUserStore} from "stores/user";
import authService from "pages/Login/service/authService";
import {useRouter, useRoute} from "vue-router";
import {useRouterConfig} from "stores/useRouterConfig";
import {useQuasar} from "quasar";

export default defineComponent({
  name: 'MainLayout',

  components: {
    EssentialLink
  },

  setup() {

    const store = useUserStore();
    const {t} = useI18n();
    const leftDrawerOpen = ref(false);
    const router = useRouter();
    const route = useRoute();


    const loggedIn = computed(() => store.getToken);
    const routerConfig = useRouterConfig();
    const essentialLinks = computed(() => routerConfig.essentialLinks);
    const miniState = ref(false);
    const selected = ref('Groups');

    const $q = useQuasar();
    const isMobile = computed(() => $q.screen.lt.sm);

    //todo how to be with browser refresh? user state is lost
    const logout = () => {
      authService.logout().then(() => {
        store.logout();
        router.push('/login');
      })
    }

    const goTo = (link) => {
      router.push(link);
    }

    return {
      store,
      loggedIn,
      logout,
      goTo,
      route,
      essentialLinks,
      leftDrawerOpen,
      toggleLeftDrawer() {
        if ($q.screen.gt.sm) {
          miniState.value = !miniState.value;
        } else {
          leftDrawerOpen.value = !leftDrawerOpen.value
        }
      },
      t,
      miniState,
      isMobile,
      selected
    }
  }
})
</script>
<style scoped lang="scss">
.header {
  background-color: white;
  color: black;
}
</style>
