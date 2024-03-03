<template>
  <page-layout>
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
            :data-loader-func="userService.loadUsers"
            :data-loader-count-func="userService.loadUsersCount"
            @select="onSelect($event)"
            @go-add="goAdd"
            @go-modify="goModify"
            @delete="deleteItem"
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
                        @submit="isModifyMode ? modify() : addGroup()"
                        @hide="onHide"
                        :title="isModifyMode ? $t('userList.dialog.header.modify') : $t('userList.dialog.header.add')">
            <q-list dense>
              <dialog-item :label="$t('userList.fields.username')"
                           v-if="!isModifyMode"
                           type="string"
                           v-model="model['username']"
                           :rules="[  val => !!val || $t('userList.fields.validations.usernameMustPresent'),
                                val => val.length >= 3 || $t('userList.fields.validations.usernameMinLength'),
                                val => val.length <= 20 || $t('userList.fields.validations.usernameMaxLength') ]"/>
              <dialog-item :label="$t('userList.fields.firstName')"
                            type="string"
                            v-model="model['firstName']"
                            :rules="[  val => !!val || $t('userList.fields.validations.firstNameMustPresent'),
                                  val => val.length >= 3 || $t('userList.fields.validations.firstNameMinLength'),
                                  val => val.length <= 50 || $t('userList.fields.validations.firstNameMaxLength') ]"/>
              <dialog-item :label="$t('userList.fields.lastName')"
                            type="string"
                            v-model="model['lastName']"
                            :rules="[  val => !!val || $t('userList.fields.validations.lastNameMustPresent'),
                                  val => val.length >= 3 || $t('userList.fields.validations.lastNameMinLength'),
                                  val => val.length <= 50 || $t('userList.fields.validations.lastNameMaxLength') ]"/>
              <dialog-item :label="$t('userList.fields.email')"
                            type="email"
                            v-model="model['email']"
                            :rules="[  val => !!val || $t('userList.fields.validations.emailMustPresent'),
                                  val => val.length >= 3 || $t('userList.fields.validations.emailMinLength'),
                                  val => val.length <= 50 || $t('userList.fields.validations.emailMaxLength'),
                                  val => val.match(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/) || $t('userList.fields.validations.emailMustBeValid')]"/>
              <dialog-item :label="$t('userList.fields.password')"
                            v-if="!isModifyMode"
                            type="password"
                            v-model="model['password']"
                            :rules="[  val => !!val || $t('userList.fields.validations.passwordMustPresent'),
                                  val => val.length >= 8 || $t('userList.fields.validations.passwordMinLength') ]"/>
              <dialog-item :label="$t('userList.fields.repeatPassword')"
                            v-if="!isModifyMode"
                            type="password"
                            v-model="model['repeatPassword']"
                            :rules="[ val => val === model['password'] || $t('userList.fields.validations.passwordMustBeTheSame') ]"/>
              <dialog-item :label="$t('userList.fields.isLocked')"
                            type="toggler"
                            v-model="model['isLocked']"/>
            </q-list>
          </dialog-modal>
        </div>
    </page-body>
    <page-footer
      v-if="isMobile"
    >
      <add-mobile-button
        v-if="canCreate && isMobile"
        :label="$t('roleList.buttons.add')"
        @add="goAdd"/>
    </page-footer>
  </page-layout>
</template>

<script setup>

//todo - fix dialog width
//todo - fix is locked field
//todo - fix rerender selected item after modify
//todo - fix popup message after modify

import {QBtn, QTable, useQuasar} from 'quasar';
import {computed, defineComponent, onMounted, ref, defineEmits} from 'vue';

import moment from 'moment';
import {useI18n} from 'vue-i18n';

import DialogModal from 'components/dialogModal/DialogModal.vue';
import DialogItem from 'components/DialogItem.vue';

import dialogHelper from 'components/dialogModal/dialogHelper.js';
import AddMobileButton from "components/buttons/AddMobileButton.vue";

import userService from './services/userService.js';

import {
  ADMIN_PRIVILEGE,
  ADD_USER_PRIVILEGE,
  MODIFY_USER_PRIVILEGE,
  DELETE_USER_PRIVILEGE
} from './constants';

import {useRouter} from "vue-router";
import CrudTable from "components/crud/CrudTable.vue";


const {t} = useI18n();
const $q = useQuasar();
const router = useRouter();
const userTable = ref(null);

defineComponent({DialogModal, DialogItem, QTable, QBtn});

//state
const model = ref({});
const isModifyMode = ref(false);
const columns = computed(() => [
  {
    name: 'id',
    label: t('userList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'username',
    label: t('userList.fields.username'),
    align: 'left',
    field: 'username',
    sortable: true,
  },
  {
    name: 'firstName',
    label: t('userList.fields.firstName'),
    align: 'left',
    field: 'firstName',
    sortable: true,
  },
  {
    name: 'lastName',
    label: t('userList.fields.lastName'),
    align: 'left',
    field: 'lastName',
    sortable: true,
  },
  {
    name: 'email',
    label: t('userList.fields.email'),
    align: 'left',
    field: 'email',
    sortable: true
  },
  {
    name: 'isLocked',
    label: t('userList.fields.isLocked'),
    align: 'left',
    field: 'isLocked',
  },
  {
    password: 'password',
    label: t('userList.fields.password'),
    align: 'left',
    field: 'password'
  },
  {
    repeatPassword: 'repeatPassword',
    label: t('userList.fields.repeatPassword'),
    align: 'left',
    field: 'repeatPassword'
  }
]);

//dialog
const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();

//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

const visibleColumns = computed(() => {
  // return columns.value.filter(column => !isMobile.value || column.name === 'name' || column.name === 'dateTime')?.map(column => column.name);
  if (!isMobile.value) {
    return columns.value.filter(column => column.name === 'username' || column.name === 'firstName' || column.name === 'lastName' || column.name === 'email' )?.map(column => column.name);
  } else {
    return columns.value.filter(column => column.name === 'username' || column.name === 'lastName' )?.map(column => column.name);
  }
});

const onSelect = (row) => {
  model.value = {...row};
  if (isMobile.value) {
    router.push(`/user/${row.id}`);
  } else {
    emit('selectGroup', row);
  }
};
const goAdd = () => {
  model.value = {};
  showDialog();
  isModifyMode.value = false;
};
const goModify = (selected) => {
  model.value = {...selected};
  isModifyMode.value = true;
  showDialog();
};
//CRUD
const addGroup = () => {
  userService.add(model.value)
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
const modify = () => {
  userService.modifyUser(model.value)
    .then(() => {
      onSubmit();
      userTable.value.load();
      model.value = {};
      isModifyMode.value = false;
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
const deleteItem = (id) => {
    userService.deleteUser(id)
      .then(() => {
        model.value = {};
        userTable.value.load();
      })
};

//privileges
const user = ref({
  roles: [ADMIN_PRIVILEGE]
});
const canCreate = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(ADD_USER_PRIVILEGE);
});
const canModify = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(MODIFY_USER_PRIVILEGE);
});
const canDelete = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(DELETE_USER_PRIVILEGE);
});

onMounted(() => {

});

</script>
<style>
</style>
