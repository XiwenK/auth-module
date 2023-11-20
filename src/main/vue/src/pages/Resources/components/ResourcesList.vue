<template>
  <page-layout class="page-desktop-left">
    <page-header>
      <template #title>
        {{$t('resourcesList.title')}}
      </template>
    </page-header>
    <page-body>
        <div id="resources" class="full-height full-width absolute">
          <crud-table
            ref="resourceTable"
            :can-add="canCreate"
            :can-modify="canModify"
            :can-delete="canDelete"
            :columns="columns"
            :visible-columns="visibleColumns"
            :data-loader-func="resourcesService.loadResources"
            :data-loader-count-func="resourcesService.loadResourcesCount"
            :delete-message="$t('resourcesList.deleteConfirmation')"
            @select="onSelect($event)"
            @go-add="goAdd"
            @go-modify="goModify"
            @delete="deleteResource"
          >
            <template v-slot:name="{row}">
              <div class="text-body2">
                {{row.name}}
              </div>
              <div class="flex justify-start">
                <div class="text-caption">
                  <q-badge
                    color="primary"
                    :label="typeLabel(row)"/>
                </div>
              </div>
            </template>
            <template v-slot:active="{row}">
              <div class="text-body2">
                {{row.active}}
              </div>
            </template>
          </crud-table>
          <dialog-modal :visible="isDialogVisible"
                        @submit="modify ? modifyResource() : addResource()"
                        @hide="onHide"
                        :title="modify ? $t('resourcesList.dialog.header.modify') : $t('resourcesList.dialog.header.add')">
            <q-list dense>
              <dialog-item :label="$t('resourcesList.fields.name')"
                           type="string"
                           v-model="model['name']"
                           :rules="[  val => !!val || $t('resourcesList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('resourcesList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('resourcesList.dialog.validations.nameLengthMax') ]"/>
              <dialog-item :label="$t('resourcesList.fields.type')"
                           :options="types"
                           type="select"
                           :disable="modify"
                           v-model="model['type']"
                           :rules="[  val => !!val || $t('resourcesList.dialog.validations.typeMustPresent')]"/>
              <dialog-item :label="$t('resourcesList.fields.description')"
                           type="string"
                           left-label
                           v-model="model['description']"
                           :rules="[  val => !!val || $t('resourcesList.dialog.validations.descriptionMustPresent'),
                                val => val.length >= 3 || $t('resourcesList.dialog.validations.descriptionLengthMin'),
                                val => val.length <= 256 || $t('resourcesList.dialog.validations.descriptionLengthMax')  ]"/>
              <dialog-item :label="$t('resourcesList.fields.calendar')"
                           :options="calendars"
                           type="select"
                           v-model="model['calendar']"
                           :rules="[  val => !!val || $t('resourcesList.dialog.validations.calendarMustPresent')]"/>
              <dialog-item :label="$t('resourcesList.fields.active')"
                           type="toggler"
                           left-label
                           v-model="model['active']"/>
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
          :label="$t('resourcesList.buttons.add')"
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

import {useI18n} from 'vue-i18n';

import DialogModal from 'components/dialogModal/DialogModal.vue';
import DialogItem from 'components/DialogItem.vue';

import dialogHelper from 'components/dialogModal/dialogHelper.js';

import resourcesService from '../services/resourceService.js';
import dictionaryService from "pages/Groups/services/dictionaryService";

import {useUserStore} from "stores/user";
// import {useGroupStore} from "stores/group";


import {
  ADD_RESOURCE_PRIVILEGE,
  ADMIN_PRIVILEGE,
  DELETE_RESOURCE_PRIVILEGE,
  MODIFY_RESOURCE_PRIVILEGE
} from '../constants';

import {useRouter} from "vue-router";
import CrudTable from "components/crud/CrudTable.vue";


const {t} = useI18n();
const $q = useQuasar();
const store = useUserStore();
// const groupStore = useGroupStore();
const router = useRouter();
const resourceTable = ref(null);

defineComponent({DialogModal, DialogItem, QTable, QBtn});

const emit = defineEmits(['selectResource']);

//state
const model = ref({});
const modify = ref(false);

const types = ref([]);
const calendars = ref([]);

const columns = computed(() => [
  {
    name: 'id',
    label: t('resourcesList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'name',
    label: t('resourcesList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'description',
    label: t('resourcesList.fields.description'),
    align: 'left',
    field: 'description',
    sortable: true,
  },
  {
    name: 'type',
    label: t('resourcesList.fields.type'),
    align: 'left',
    field: 'type',
    sortable: true,
  },
  {
    name: 'calendar',
    label: t('resourcesList.fields.calendar'),
    align: 'left',
    field: 'calendar',
    sortable: true,
  },
  {
    name: 'active',
    label: t('resourcesList.fields.active'),
    align: 'left',
    field: 'active',
    sortable: true,
  },
]);

//dialog
const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();

//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

const visibleColumns = computed(() => {
  // return columns.value.filter(column => !isMobile.value || column.name === 'name' || column.name === 'dateTime')?.map(column => column.name);
  return columns.value.filter(column => column.name === 'name' || column.name === 'active')?.map(column => column.name);
});

const typeLabel = (row) => {
  return row.type;
};

const onSelect = (row) => {
  model.value = {...row};
  // groupStore.selectGroup(row);
  if (isMobile.value) {
    router.push(`/resources/${row.id}`);
  } else {
    emit('selectResource', row);
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
const addResource = () => {
  resourcesService.addResource({...model.value})
    .then(() => {
      onSubmit();
      resourceTable.value.load();
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
const modifyResource = () => {
  resourcesService.modifyResource({...model.value})
    .then(() => {
      onSubmit();
      resourceTable.value.load();
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
const deleteResource = (id) => {
    resourcesService.deleteResource(id)
      .then(() => {
        model.value = {};
        resourceTable.value.load();
      })
};

//privileges
const user = ref({
  roles: [ADMIN_PRIVILEGE]
});
const canCreate = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(ADD_RESOURCE_PRIVILEGE);
});
const canModify = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(MODIFY_RESOURCE_PRIVILEGE);
});
const canDelete = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(DELETE_RESOURCE_PRIVILEGE);
});

onMounted(() => {
  const promises = [];
  promises.push(dictionaryService.loadResourceTypes());
  promises.push(dictionaryService.loadCalendars());
  Promise.all(promises)
    .then(([typesResult, calendarsResult]) => {
      types.value = typesResult;
      calendars.value = calendarsResult;
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
