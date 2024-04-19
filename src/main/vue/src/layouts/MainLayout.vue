<template>
  <q-layout view="lHh lpR fFf"
            class="main-layout">
    <q-header class="header">
      <q-toolbar class="q-px-md">
        <div class="main-layout__manu-button">
          <q-btn
            v-if="$route.path !== '/login'"
            flat
            dense
            round
            icon="menu"
            aria-label="Menu"
            @click="toggleLeftDrawer()"
          />
        </div>
        <q-toolbar-title v-if="loggedIn">
          {{ $t('appName') }}
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
        <div v-for="link in essentialLinks" :key="link.title">
          <essential-link
            v-if="link.showOnLoggedOut || loggedIn"
            @click="selected=link.title; $router.push(link.link)"
            v-bind="link"
          />
        </div>
      </q-list>
    </q-drawer>
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

<script setup>
import {ref, computed} from 'vue'
import {useRouter} from "vue-router";
import {useQuasar} from "quasar";
import {useI18n} from 'vue-i18n';
import {useUserStore} from "stores/user";
import {useRouterConfig} from "stores/useRouterConfig";
import authService from "pages/Login/service/authService";
import EssentialLink from '../components/EssentialLink.vue';

const {t} = useI18n();

const store = useUserStore();
const loggedIn = computed(() => store.getToken);
const logout = () => {
  authService.logout().then(() => {
    store.logout();
    router.push('/login');
  })
}

const leftDrawerOpen = ref(false);
const router = useRouter();
const routerConfig = useRouterConfig();
const essentialLinks = computed(() => routerConfig.essentialLinks);
const miniState = ref(false);

const selected = ref('Groups');

const $q = useQuasar();
const toggleLeftDrawer = () => {
  if ($q.screen.gt.sm) {
    miniState.value = !miniState.value;
  } else {
    leftDrawerOpen.value = !leftDrawerOpen.value
  }
}
</script>
<style scoped lang="scss">
.header {
  background-color: white;
  color: black;
}
</style>
