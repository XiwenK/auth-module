<template>
    <form-page>
      <template #title>
        {{$t('appName')}}
      </template>
      <template #form>
        <q-card class="column"
                style="width: 360px"
                square
                v-model="showLoginForm">
          <q-card-section class="row items-center self-center">
            <div class="text-h6">{{ t('loginPage.fields.formTitle') }}</div>
          </q-card-section>
          <q-card-section>
            <q-form ref="form" @keyup.enter="onLoginClick()">
              <q-input
                v-model="login"
                :label="$t('loginPage.fields.login')"
                type="email"
                :rules="[ val => !!val || $t('loginPage.validations.loginMustPresent')]"
                lazy-rules="ondemand"
              />
              <q-input
                v-model="password"
                :label="$t('loginPage.fields.password')"
                type="password"
                lazy-rules="ondemand"
                :rules="[ val => !!val || $t('loginPage.validations.passwordMustPresent')]"
              />
              <q-btn class="q-mt-lg full-width" @click="onLoginClick()" color="primary" label="Login"/>
              <q-btn class="q-mt-lg full-width" @click="loginWithGoogle()" color="primary" label="Login with Google"/>
              <div class="row justify-center q-mt-md items-center">
                <div class="text-caption">{{ t('loginPage.registration.message') }}</div>
                <q-btn flat dense color="primary" :label="$t('loginPage.registration.button')"  @click="onRegisterClick()"/>
              </div>
            </q-form>
          </q-card-section>
        </q-card>
      </template>
    </form-page>
</template>

<script setup>

import {ref, onMounted} from 'vue'
import {useRouter, useRoute} from 'vue-router';
import {useI18n} from 'vue-i18n';
import authService from "pages/Login/service/authService";
import { useUserStore} from "stores/user";
import { useQuasar } from 'quasar';
import FormPage from "components/page/FormPage.vue";

const loggedIn = ref(false)
const showLoginForm = ref(false)
const login = ref('user')
const password = ref('Password1)')

const router = useRouter();
const route = useRoute();
const {t} = useI18n();
const $q = useQuasar();

const store = useUserStore();

const logout = () => {
  loggedIn.value = false
}

const rules = [
  val => val.length >= 8 && val.length <= 20 || 'Password must be between 8 and 20 characters'
]

const form = ref(null);

function loginWithGoogle() {
  window.location.href = `http://localhost:9090/api/v1/oauth2/authorization/google`;
}

function onLoginClick(isGoogle = false) {
  form.value.validate()
    .then(success => {
      if (success) {
        const request = isGoogle ? authService.loginWithGoogle() : authService.login({login: login.value, password: password.value})
        request
          .then((response) => {
            store.login(response);
            router.push(`/users`);
        }).catch((err) => {
          if (err.response?.data) {
            $q.notify({
              message: t(err.response.data),
              color: 'negative',
              position: 'top',
              timeout: 2000
            })
            return
          }
          $q.notify({
            message: t('loginPage.validations.invalidCredentials'),
            color: 'negative',
            position: 'top',
            timeout: 2000
          })
        })
      } else {
      }
    })
}

function onRegisterClick() {
  router.push(`/register`);
}

onMounted(() => {
  if (route.query?.expired) {
    $q.notify({
      message: t('loginPage.validations.sessionExpired'),
      color: 'negative',
      position: 'top',
      timeout: 2000
    })
  }
  // check if query already contains accessToken and refreshToken, then save it to store and redirect to users page
  if (route.query?.accessToken && route.query?.refreshToken) {
    store.login({token: route.query.accessToken, refreshToken: route.query.refreshToken});
    router.push(`/users`);
  }
  // check request body, if contains accessToken and refreshToken, then save it to store and redirect to users page

})
</script>
