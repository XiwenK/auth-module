<template>
  <form-page>
   <template v-slot:title>
     {{$t('appName')}}
   </template>
   <template v-slot:form>
     <q-card class="column" :style="{'width': width, 'min-height': '372px'}">
       <q-card-section class="column items-center self-center">
         <div class="text-h6">{{ group.name }}</div>
         <div class="q-pt-sm text-grey">{{ group.dateTime }}</div>
       </q-card-section>
       <q-form ref="form" v-if="formVisible">
         <q-card-section style="max-height: 500px">
           <dialog-item :label="$t('studentsList.fields.name')"
                        type="string"
                        v-model="model['name']"
                        :rules="[  val => !!val || $t('studentsList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('studentsList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('studentsList.dialog.validations.nameLengthMax') ]"/>
           <dialog-item :label="$t('studentsList.fields.phone')"
                        type="string"
                        prefix="+"
                        v-model="model['phone']"
                        :rules="[  val => !!val || $t('studentsList.dialog.validations.phoneMustPresent'),
                                    val => !isNaN(val) || $t('studentsList.dialog.validations.phoneMustBeNumber'),
                                    val => val.length < 12 || $t('studentsList.dialog.validations.phoneLengthMax') ]"/>
           <dialog-item :label="$t('studentsList.fields.comment')"
                        type="textarea"
                        v-model="model['comment']"
                        :rules="[ val => !val || (val && val.length < 200) || $t('studentsList.dialog.validations.commentLengthMax')]"/>
         </q-card-section>
         <div class="q-px-lg q-pb-lg">
           <q-btn
             color="primary"
             :label="$t('regForm.buttons.submit')"
             class="full-width"
             @click="addStudent"
           />
         </div>
       </q-form>
       <q-card-section v-else class="row items-center self-center flex-grow-1">
         <div class="text-h5">{{ $t('regForm.thanksMessage') }}</div>
       </q-card-section>
     </q-card>
     <div class="q-pt-sm">
       {{ $t('footerInfo.powered') }}
     </div>
   </template>
  </form-page>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref, defineComponent, computed} from "vue";
import {postData} from "src/rest/utils";
import {useQuasar} from "quasar";
import {useI18n} from "vue-i18n";

import DialogItem from 'components/DialogItem.vue';

import studentsService from "pages/Students/services/studentService";
import endpoints from "src/rest/endpoints";
import FormPage from "components/page/FormPage.vue";

defineComponent({DialogItem});

const route = useRoute();
const router = useRouter();
const group = ref({});
const model = ref({});
const formVisible = ref(true);
const $q = useQuasar();
const form = ref(null);
const {t} = useI18n();

const addStudent = () => {
  form.value.validate().then((success) => {
    if (success) {
      studentsService.addStudentByUrl({...model.value, ...{url: route.params.shortUrl}})
        .then(() => {
          formVisible.value = false;
          model.value = {};
        })
        .catch(res => {
          //todo: extract to useNotify
          $q.notify({
            color: 'negative',
            message: res?.response?.data?.message || '',
            icon: 'report_problem',
            position: 'top-right'
          })
        })
    }
  })
};

const isMobile = computed(() => $q.screen.lt.sm);
const width = computed(() => isMobile.value ? '360px' : '400px');

onMounted( () => {
  postData(endpoints.shortUrlsGetGroupId, {url: route.params.shortUrl})
    .then(res => {
      if (res.data) {
        group.value.name = res.data.name;
        group.value.dateTime = res.data.dateTime;
      } else {
        router.push('/404');
      }
    })
    .catch(() => {
      router.push('/404');
    })
});

</script>

<style>

</style>
