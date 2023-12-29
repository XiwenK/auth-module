<template>
  <page-layout class="page-desktop-left">
    <page-header>
      <template #title>
        {{$t('channelsList.title')}}
      </template>
    </page-header>
    <page-body>
      <div id="channels" class="full-height full-width absolute">
        <crud-table
          ref="channelTable"
          :can-add="canCreate"
          :can-modify="canModify"
          :can-delete="canDelete"
          :columns="columns"
          :visible-columns="visibleColumns"
          :data-loader-func="channelService.loadChannels"
          :data-loader-count-func="channelService.loadChannelsCount"
          @select="onSelect($event)"
          @go-add="goAdd"
          @go-modify="goModify"
          @delete="deleteChannel"
        >
          <template v-slot:name="{row}">
            <div class="text-body2">
              {{row.name}}
            </div>
          </template>
        </crud-table>
        <dialog-modal :visible="isDialogVisible"
                      @submit="modify ? modifyChannel() : addChannel()"
                      @hide="onHide"
                      :title="modify ? $t('channelsList.dialog.header.modify') : $t('channelsList.dialog.header.add')">
          <q-list dense>
            <dialog-item :label="$t('channelsList.fields.name')"
                         type="string"
                         v-model="model['name']"
                         :rules="[  val => !!val || $t('channelsList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('channelsList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('channelsList.dialog.validations.nameLengthMax') ]"/>
            <dialog-item :label="$t('channelsList.fields.description')"
                         type="textarea"
                         left-label
                         v-model="model['description']"
                         :rules="[  val => !!val || $t('channelsList.dialog.validations.descriptionMustPresent')]"/>
            <dialog-item :label="$t('channelsList.fields.isActive')"
                         type="toggler"
                         left-label
                         v-model="model['isActive']"/>
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

import channelService from '../services/channelService.js';

import {useUserStore} from "stores/user";
import {useChannelStore} from "stores/channel";


import {
  ADD_CHANNEL_PRIVILEGE,
  ADMIN_PRIVILEGE,
  DELETE_CHANNEL_PRIVILEGE,
  MODIFY_CHANNEL_PRIVILEGE
} from '../constants';

import {useRouter} from "vue-router";
import CrudTable from "components/crud/CrudTable.vue";


const {t} = useI18n();
const $q = useQuasar();
const store = useUserStore();
const channelStore = useChannelStore();
const router = useRouter();
const channelTable = ref(null);

defineComponent({DialogModal, DialogItem, QTable, QBtn});

const emit = defineEmits(['selectChannel']);

//state
const model = ref({});
const modify = ref(false);
const schedules = ref([]);
const columns = computed(() => [
  {
    name: 'id',
    label: t('channelsList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'name',
    label: t('channelsList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'description',
    label: t('channelsList.fields.description'),
    align: 'left',
    field: 'description',
    sortable: true,
  },
  {
    name: 'isActive',
    label: t('channelsList.fields.isActive'),
    align: 'left',
    field: 'isActive',
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
  return columns.value.filter(column => column.name === 'name' || column.name === 'isActive')?.map(column => column.name);
});


const onSelect = (row) => {
  model.value = {...row};
  channelStore.selectChannel(row);
  if (isMobile.value) {
    router.push(`/channels/${row.id}`);
  } else {
    emit('selectChannel', row);
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
const addChannel = () => {
  channelService.addChannel({...model.value})
    .then(() => {
      onSubmit();
      channelTable.value.load();
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
const modifyChannel = () => {
  channelService.modifyChannel({...model.value})
    .then(() => {
      onSubmit();
      channelTable.value.load();
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
const deleteChannel = (id) => {
  channelService.deleteChannel(id)
    .then(() => {
      model.value = {};
      groupTable.value.load();
    })
};

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

});

</script>
<style>
</style>
