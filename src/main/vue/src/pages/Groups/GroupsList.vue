<template>
  <page-layout class="page-desktop-left">
    <page-header>
      <template #title>
        {{$t('groupsList.title')}}
      </template>
    </page-header>
    <page-body>
        <div id="groups" class="full-height full-width absolute">
          <crud-table
            ref="groupTable"
            :can-add="canCreate"
            :can-modify="canModify"
            :can-delete="canDelete"
            :columns="columns"
            :visible-columns="visibleColumns"
            :data-loader-func="groupService.loadGroups"
            :data-loader-count-func="groupService.loadGroupsCount"
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
                <div class="text-caption">
                  <q-badge
                    :color="bookedColor(row)"
                    :label="bookedToTotalString(row)"/>
                </div>
                <div class="q-pl-md">
                  <q-badge color="blue" :label="scheduleLabel(row)"/>
                </div>
              </div>
            </template>
          </crud-table>
          <dialog-modal :visible="isDialogVisible"
                        @submit="modify ? modifyGroup() : addGroup()"
                        @hide="onHide"
                        :title="modify ? $t('groupsList.dialog.header.modify') : $t('groupsList.dialog.header.add')">
            <q-list dense>
              <dialog-item :label="$t('groupsList.fields.name')"
                           type="string"
                           v-model="model['name']"
                           :rules="[  val => !!val || $t('groupsList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('groupsList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('groupsList.dialog.validations.nameLengthMax') ]"/>
              <dialog-item :label="$t('groupsList.fields.date')"
                           type="date"
                           :date-format="dateFormat"
                           v-model="model['dateTime']"
                           :rules="[  val => !!val || $t('groupsList.dialog.validations.dateMustPresent'),
                                val => modify || moment(val, dateFormat).isAfter(moment()) || $t('groupsList.dialog.validations.dateMustBeInFuture')]"/>
              <dialog-item :label="$t('groupsList.fields.placesCount')"
                           type="number"
                           left-label
                           v-model="model['placesCount']"
                           :rules="[  val => !!val || $t('groupsList.dialog.validations.placesCountMustPresent'),
                                val => val > 0 || $t('groupsList.dialog.validations.placesCountMin'),
                                val => val <= 100 || $t('groupsList.dialog.validations.placesCountMax')  ]"/>
              <dialog-item :label="$t('groupsList.fields.booked')"
                           type="number"
                           left-label
                           v-model="model['booked']"
                           :rules="[  val => val <= model['placesCount'] || $t('groupsList.dialog.validations.bookedCannotBeMoreThanPlacesCount')]"/>
              <dialog-item :label="$t('groupsList.fields.placePrice')"
                           type="number"
                           left-label
                           v-model="model['placePrice']"/>
              <dialog-item :label="$t('groupsList.fields.schedule')"
                           :options="schedules"
                           type="select"
                           left-label
                           v-model="model['schedule']"
                           :rules="[  val => !!val || $t('groupsList.dialog.validations.scheduleMustPresent')]"/>
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
          label="Add group"
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

import groupService from './services/groupService.js';
import dictionaryService from "pages/Groups/services/dictionaryService";

import {useUserStore} from "stores/user";
import {useGroupStore} from "stores/group";


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
const store = useUserStore();
const groupStore = useGroupStore();
const router = useRouter();
const groupTable = ref(null);

defineComponent({DialogModal, DialogItem, QTable, QBtn});

const emit = defineEmits(['selectGroup']);

//state
const model = ref({});
const modify = ref(false);
const schedules = ref([]);
const columns = computed(() => [
  {
    name: 'id',
    label: t('groupsList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'name',
    label: t('groupsList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'dateTime',
    label: t('groupsList.fields.date'),
    align: 'left',
    field: 'dateTime',
    sortable: true,
  },
  {
    name: 'placesCount',
    label: t('groupsList.fields.placesCount'),
    align: 'left',
    field: 'placesCount',
    sortable: true,
  },
  {
    name: 'booked',
    label: t('groupsList.fields.booked'),
    align: 'left',
    field: 'booked',
    sortable: true,
  },
  {
    name: 'placePrice',
    label: t('groupsList.fields.placePrice'),
    align: 'left',
    field: 'placePrice',
    sortable: true,
  },
  {
    name: 'schedule',
    label: t('groupsList.fields.schedule'),
    align: 'left',
    field: 'scheduleLabel',
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


const scheduleLabel = (row) => row.schedule?.label || '';
const bookedToTotalString = (row) => `${row.booked}/${row.placesCount}`;
const bookedToTotalPercent = (row) => row.placesCount === 0 ? 0 : row.booked / row.placesCount * 100;
const bookedColor = (row) => bookedToTotalPercent(row) > 80 ? 'positive' : bookedToTotalPercent(row) > 40 ? 'info' : 'negative';


const onSelect = (row) => {
  model.value = {...row};
  groupStore.selectGroup(row);
  if (isMobile.value) {
    router.push(`/groups/${row.id}`);
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
  groupService.addGroup({...model.value, ...{date: moment(model.value.date, DATE_FORMAT).toDate()}})
    .then(() => {
      onSubmit();
      groupTable.value.load();
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
  groupService.modifyGroup({...model.value, ...{date: moment(model.value.date, DATE_FORMAT).toDate()}})
    .then(() => {
      onSubmit();
      groupTable.value.load();
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
    groupService.deleteGroup(id)
      .then(() => {
        model.value = {};
        groupTable.value.load();
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
