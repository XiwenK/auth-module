<template>
  <page-layout class="one-pane">
<!--    <page-header>-->
<!--      <template #title>-->
<!--        {{$t('transactionsList.title')}}-->
<!--      </template>-->
<!--    </page-header>-->
    <page-body>
      <div id="channels" class="full-height full-width absolute">
        <crud-table
          ref="channelTable"
          :columns="columns"
          :visible-columns="visibleColumns"
          :data-loader-func="transactionService.loadTransactions"
          :data-loader-count-func="transactionService.loadTransactionsCount"
          @select="onSelect($event)"
          :show-crud-buttons="false"
        >
          <template v-slot:date="{row}">
            <div class="text-body2">
              {{ formattedDate(row.date) }}
            </div>
          </template>
          <template v-slot:amount="{row}">
            <div class="text-body2">
              {{ formattedAmount(row.amount) }}
            </div>
          </template>
        </crud-table>
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

import transactionService from '../services/transactionService.js';

import {useUserStore} from "stores/user";
import {useChannelStore} from "stores/channel";


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
    id: 'id',
    name: 'id',
    label: t('transactionsList.id'),
    field: 'id',
    align: 'left',
    sortable: true
  },
  {
    id: 'amount',
    name: 'amount',
    label: t('transactionsList.amount'),
    field: 'amount',
    align: 'left',
    sortable: true
  },
  {
    id: 'date',
    name: 'date',
    label: t('transactionsList.date'),
    field: 'date',
    align: 'left',
    sortable: true,
  },
  {
    id: 'comment',
    name: 'comment',
    label: t('transactionsList.comment'),
    field: 'comment',
    align: 'left',
    sortable: true,
  },
  {
    id: 'terminal',
    name: 'terminal',
    label: t('transactionsList.terminal'),
    field: 'terminal',
    align: 'left',
    sortable: true,
  },
  {
    id: 'card',
    name: 'card',
    label: t('transactionsList.card'),
    field: 'card',
    align: 'left',
    sortable: true,
  },
  {
    id: 'merchant',
    name: 'merchant',
    label: t('transactionsList.merchant'),
    field: 'merchant',
    align: 'left',
    sortable: true,
  },
  {
    id: 'mcc',
    name: 'mcc',
    label: t('transactionsList.mcc'),
    field: 'mcc',
    align: 'left',
    sortable: true,
  },
  {
    id: 'currency',
    name: 'currency',
    label: t('transactionsList.currency'),
    field: 'currency',
    align: 'left',
    sortable: true,
  },
  {
    id: 'respCode',
    name: 'respCode',
    label: t('transactionsList.respCode'),
    field: 'respCode',
    align: 'left',
    sortable: true,
  }
]);

const loggedIn = computed(() => store.loggedIn);

//dialog
const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();


const formattedDate = (date) => {
  return moment(date).format('DD.MM HH:mm:ss');
};

const formattedAmount = (amount) => {
  return amount.toFixed(2);
};

//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

const visibleColumns = computed(() => {
  return columns.value.map(column => column.name);
  // return columns.value.filter(column => column.name === 'name' || column.name === 'isActive')?.map(column => column.name);
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


onMounted(() => {

});

</script>
<style>
</style>
