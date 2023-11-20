<template>
  <form-page>
    <template v-slot:title>
      {{ $t('appName') }}
    </template>
    <template v-slot:form>
      <q-card class="column" style="width: 360px" square>
        <q-card-section class="row items-center self-center">
          <div class="text-h6">{{ t('registerPage.fields.formTitle') }}</div>
        </q-card-section>
        <q-card-section>
          <q-form ref="form" @keyup.enter="onRegisterClick()">
            <q-input
              v-model="login"
              :label="$t('registerPage.fields.login')"
              type="text"
              :rules="[ val => !!val || $t('registerPage.validations.loginMustPresent'),
                    val => val.length >= 3 || $t('registerPage.validations.loginMinLength') ]"
              lazy-rules="ondemand"
            />
            <q-input
              v-model="email"
              :label="$t('registerPage.fields.email')"
              type="email"
              lazy-rules="ondemand"
              :rules="[ val => !!val || $t('registerPage.validations.emailMustPresent'),
                  val => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val) || $t('registerPage.validations.emailMustBeValid') ]"
            />
            <q-input
              v-model="password"
              :label="$t('registerPage.fields.password')"
              type="password"
              lazy-rules="ondemand"
              :rules="[ val => /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_]).{8,}$/.test(val) || $t('registerPage.validations.passwordComplexity')]"
            />
            <q-input
              v-model="repeatPassword"
              :label="$t('registerPage.fields.repeatPassword')"
              type="password"
              lazy-rules="ondemand"
              :rules="[ val => val === password || $t('registerPage.validations.passwordMustBeTheSame')]"
            />
            <q-checkbox
              class="q-mt-sm"
              v-model="isAccept"
              :label="$t('registerPage.fields.policy')"
            />
            <q-btn class="q-mt-lg full-width"
                   @click="onRegisterClick()"
                   color="primary"
                   :disabled="!isAccept"
                   :label="$t('registerPage.button')"/>
          </q-form>
        </q-card-section>
      </q-card>
    </template>
  </form-page>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router';
import {useI18n} from 'vue-i18n';
import {useQuasar} from 'quasar';
import authService from "pages/Login/service/authService";
import FormPage from "components/page/FormPage.vue";

const router = useRouter();
const {t} = useI18n();
const $q = useQuasar();

const login = ref('');
const password = ref('');
const repeatPassword = ref('');
const email = ref('');
const isAccept = ref(false);
const form = ref(null);

function onRegisterClick() {
  form.value.validate()
    .then(success => {
      if (success) {
        authService.register({login: login.value, email: email.value, password: password.value})
          .then(() => {
            router.push(`/login`);
            $q.notify({
              message: t('registerPage.successMessage'),
              color: 'positive',
              position: 'top',
              timeout: 2000
            })
          }).catch((err) => {
          if (err.response?.data) {
            $q.notify({
              message: t(err.response.data.message),
              color: 'negative',
              position: 'top',
              timeout: 2000
            })
          }
        })
      }
    })
}
</script>
