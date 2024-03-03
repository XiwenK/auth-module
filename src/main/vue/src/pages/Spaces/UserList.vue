<template>
  <page-layout class="page-desktop-right single">
    <page-header>
      <template #title>
        {{$t('userList.title')}}
      </template>
    </page-header>
    <page-body>
        <div id="user" class="fit absolute">
          <crud-table
            ref="userTable"
            :can-add="canCreate"
            :can-modify="canModify"
            :can-delete="canDelete"
            :columns="columns"
            :visible-columns="visibleColumns"
            :data-loader-func="userService.loadGroups"
            :data-loader-count-func="userService.loadGroupsCount"
            @select="onSelect($event)"
            @go-add="goAdd"
            @go-modify="goModify"
            @delete="deleteGroup"
          >
            <template v-slot:name="{row}">
              <div class="text-body2">
                {{row.name}}
              </div>
              <div class="flex justify-start">
                <div>
                  <q-badge color="blue" :label="row.desc"/>
                </div>
              </div>
            </template>
          </crud-table>
          <dialog-modal :visible="isDialogVisible"
                        @submit="modify ? modifyGroup() : addGroup()"
                        @hide="onHide"
                        :title="modify ? $t('userList.dialog.header.modify') : $t('userList.dialog.header.add')">
            <q-list dense>
              <dialog-item :label="$t('userList.fields.name')"
                           type="string"
                           v-model="model['name']"
                           :rules="[  val => !!val || $t('userList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('userList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('userList.dialog.validations.nameLengthMax') ]"/>
              <dialog-item :label="$t('userList.fields.desc')"
                           type="string"
                           v-model="model['desc']"
                           :rules="[  val => !!val || $t('userList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('userList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('userList.dialog.validations.nameLengthMax') ]"/>
            </q-list>
          </dialog-modal>
        </div>
    </page-body>
    <page-footer
      v-if="isMobile"
    >
      <div class="q-pa-md">
        <q-btn
          v-if="canCreate"
          color="primary"
          label="Add user"
          @click="goAdd()"
          class="full-width"
          rounded
          unelevated
          no-caps
        />
      </div>
    </page-footer>
  </page-layout>
</template>

<script setup>
import {QBtn, QTable, useQuasar} from 'quasar';
import {computed, defineComponent, onMounted, ref, defineEmits} from 'vue';

import moment from 'moment';
import {useI18n} from 'vue-i18n';

import DialogModal from 'components/dialogModal/DialogModal.vue';
import DialogItem from 'components/DialogItem.vue';

import dialogHelper from 'components/dialogModal/dialogHelper.js';

import userService from './services/userService.js';
import dictionaryService from "pages/Groups/services/dictionaryService";


import {
  ADD_GROUP_PRIVILEGE,
  ADMIN_PRIVILEGE,
  DATE_FORMAT,
  DELETE_GROUP_PRIVILEGE,
  MODIFY_GROUP_PRIVILEGE
} from './constants';

import {useRouter} from "vue-router";
import CrudTable from "components/crud/CrudTable.vue";


const {t} = useI18n();
const $q = useQuasar();
const router = useRouter();
const userTable = ref(null);

defineComponent({DialogModal, DialogItem, QTable, QBtn});

const emit = defineEmits(['selectGroup']);

//state
const model = ref({});
const modify = ref(false);
const schedules = ref([]);
const columns = computed(() => [
  {
    name: 'id',
    label: t('userList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'name',
    label: t('userList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'desc',
    label: t('userList.fields.desc'),
    align: 'left',
    field: 'desc',
    sortable: true,
  },
  {
    name: 'status',
    label: t('userList.fields.status'),
    align: 'left',
    field: 'status',
    sortable: true,
  }
]);

const loggedIn = computed(() => store.loggedIn);

//dialog
const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();


//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

const visibleColumns = computed(() => {
  // return columns.value.filter(column => !isMobile.value || column.name === 'name' || column.name === 'dateTime')?.map(column => column.name);
  return columns.value.filter(column => column.name === 'name' || column.name === 'dateTime')?.map(column => column.name);
});

const onSelect = (row) => {
  model.value = {...row};
  userStore.selectGroup(row);
  if (isMobile.value) {
    router.push(`/user/${row.id}`);
  } else {
    emit('selectGroup', row);
  }
};
const goAdd = () => {
  model.value = {};
  showDialog();
  modify.value = false;
};
const goModify = (selected) => {
  model.value = {...selected};
  modify.value = true;
  showDialog();
};
//CRUD
const addGroup = () => {
  userService.addGroup({...model.value, ...{date: moment(model.value.date, DATE_FORMAT).toDate()}})
    .then(() => {
      onSubmit();
      userTable.value.load();
      model.value = {};
    })
    .catch(res => {
      $q.notify({
        color: 'negative',
        message: t(res?.response?.data?.message) || '',
        icon: 'report_problem',
        position: 'top-right'
      })
    })
};
const modifyGroup = () => {
  userService.modifyGroup({...model.value, ...{date: moment(model.value.date, DATE_FORMAT).toDate()}})
    .then(() => {
      onSubmit();
      userTable.value.load();
      model.value = {};
      modify.value = false;
    })
    .catch(res => {
      $q.notify({
        color: 'negative',
        message: res?.response?.data?.message || '',
        icon: 'report_problem',
        position: 'top-right'
      })
    })
};
const deleteGroup = (id) => {
    userService.deleteGroup(id)
      .then(() => {
        model.value = {};
        userTable.value.load();
      })
};

const dateFormat = computed(() => {
  return DATE_FORMAT;
})

//privileges
const user = ref({
  roles: [ADMIN_PRIVILEGE]
});
const canCreate = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(ADD_GROUP_PRIVILEGE);
});
const canModify = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(MODIFY_GROUP_PRIVILEGE);
});
const canDelete = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(DELETE_GROUP_PRIVILEGE);
});

onMounted(() => {
  const promises = [];
  promises.push(dictionaryService.loadSchedules());
  Promise.all(promises)
    .then(([schedule]) => {
      schedules.value = schedule;
    })
    .catch(res => {
      $q.notify({
        color: 'negative',
        message: res?.response?.data?.message || '',
        icon: 'report_problem',
        position: 'top-right'
      })
    });
});

</script>
<style>
</style>
