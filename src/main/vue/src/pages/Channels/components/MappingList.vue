<template>
  <div id="mappings" class="column no-wrap">
    <crud-table
      style="min-height: 100px"
      ref="mappingTable"
      :can-add="canCreate"
      :can-modify="canModify"
      :can-delete="canDelete"
      :columns="columns"
      :filter-prop="filter"
      :visible-columns="visibleColumns"
      :data-loader-func="isResponse ? loadResMappings : loadReqMappings"
      :load-listener-func="selectMapping"
      @select="onSelect($event)"
      @go-add="goAdd"
      @go-modify="goModify"
      @delete="deleteMapping"
      :hide-pagination="true"
      :compact-buttons="false"
    >
      <template v-slot:field="{row}">
        <div class="text-body2">
          {{ formattedField(row.field) }}
        </div>
      </template>
      <template v-if="isMobile" v-slot:attribute="{row}">
        <div class="text-body2">
          {{ row.attribute }}
        </div>
      </template>
      <template v-if="!isMobile" v-slot:type="{row}">
        <div class="text-body2">
          {{ row.type }}
        </div>
        <div>
          {{ formattedDescription(row) }}
        </div>
      </template>
    </crud-table>
    <div class="q-pa-md" style="border-top: 1px solid rgba(0, 0, 0, 0.12)" v-if="canCreate && isMobile">
      <q-btn
        color="primary"
        :label="$t('mappingsList.buttons.add')"
        @click="goAdd()"
        class="full-width"
        rounded
        unelevated
        no-caps
      />
    </div>
    <dialog-modal :visible="isDialogVisible"
                  @submit="modify ? modifyMapping() : addMapping()"
                  @hide="onHide"
                  :title="modify ? $t('mappingsList.dialog.header.modify') : $t('mappingsList.dialog.header.add')">
      <q-list dense>
        <dialog-item :label="$t('mappingsList.fields.field')"
                     type="multiselectWithAdd"
                     :options="attributes"
                     v-model="model['field']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.fieldMustPresent')]">
          <q-btn round dense flat icon="add" />
        </dialog-item>
        <dialog-item :label="$t('mappingsList.fields.attribute')"
                     type="string"
                     v-model="model['attribute']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.attributeMustPresent'),
                                val => val.length >= 3 || $t('mappingsList.dialog.validations.attributeLength'),
                                val => val.length <= 50 || $t('mappingsList.dialog.validations.attributeLengthMax') ]"/>
        <dialog-item :label="$t('mappingsList.fields.description')"
                     type="string"
                     left-label
                     v-model="model['description']"
                     :rules="[ val => !val || (val && val.length < 200) || $t('mappingsList.dialog.validations.descriptionLengthMax')  ]"/>
        <dialog-item :label="$t('mappingsList.fields.isMandatory')"
                     type="select"
                     :options="['MANDATORY', 'OPTIONAL']"
                     left-label
                     v-model="model['isMandatory']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.dateFormatMustPresent') ]"/>
        <dialog-item :label="$t('mappingsList.fields.type')"
                     type="select"
                     :options="types"
                     left-label
                     v-model="model['type']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.typeMustPresent')]"/>
        <dialog-item :label="$t('mappingsList.fields.dateFormat')"
                     v-if="model['type'] === 'DATE'"
                     type="string"
                     left-label
                     v-model="model['dateFormat']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.dateFormatMustPresent') ]"/>
        <dialog-item :label="$t('mappingsList.fields.stringFunctions')"
                     v-if="model['type'] === 'STRING'"
                     type="select"
                     :options="stringFunctions"
                     left-label
                     v-model="model['stringFunction']"
                     :rules="[  val => mustStringFunctionPresent(val) || $t('mappingsList.dialog.validations.stringFunctionsMustPresent')]"/>
        <dialog-item :label="$t('mappingsList.fields.concatDelimiter')"
                     v-if="model['type'] === 'STRING' && model['stringFunction'] === 'CONCAT'"
                     type="string"
                     left-label
                     v-model="model['concatDelimiter']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.concatDelimiterMustPresent'),
                                val => val.length <= 3 || $t('mappingsList.dialog.validations.concatDelimiterLengthMax')]"/>
        <dialog-item :label="$t('mappingsList.fields.fromValue')"
                     v-if="model['type'] === 'STRING' && model['stringFunction'] === 'REPLACE'"
                     type="string"
                     left-label
                     v-model="model['fromValue']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.fromValueMustPresent'),
                                val => val.length <= 50 || $t('mappingsList.dialog.validations.fromValueLengthMax')]"/>
        <dialog-item :label="$t('mappingsList.fields.toValue')"
                     v-if="model['type'] === 'STRING' && model['stringFunction'] === 'REPLACE'"
                     type="string"
                     left-label
                     v-model="model['toValue']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.toValueMustPresent'),
                                val => val.length <= 50 || $t('mappingsList.dialog.validations.toValueLengthMax') ]"/>
        <dialog-item :label="$t('mappingsList.fields.startIndex')"
                     v-if="model['type'] === 'STRING' && model['stringFunction'] === 'SUBSTRING'"
                     type="number"
                     left-label
                     v-model="model['startIndex']"
                     :rules="[  val => !!val || $t('mappingsList.dialog.validations.startIndexMustPresent'),
                              val => !isNaN(val) || $t('mappingsList.dialog.validations.startIndexMustBeNumber')]"/>
        <dialog-item :label="$t('mappingsList.fields.endIndex')"
                     v-if="model['type'] === 'STRING' && model['stringFunction'] === 'SUBSTRING'"
                     type="number"
                     left-label
                     v-model="model['endIndex']"
                     :rules="[  val => (!val || !isNaN(val)) || $t('mappingsList.dialog.validations.endIndexMustBeNumber'),
                                val => (!val || val > model['startIndex']) || $t('mappingsList.dialog.validations.endIndexMustBeGreaterThanStartIndex')]"/>
        <template v-if="model['type'] === 'AMOUNT'">
          <dialog-item :label="$t('mappingsList.fields.isNominal')"
                       type="select"
                       :options="['true', 'false']"
                       left-label
                       v-model="model['isNominal']"
                       :rules="[  val => !!val || $t('mappingsList.dialog.validations.isNominalMustPresent') ]"/>
          <template v-if="model['isNominal'] === 'false'">
            <dialog-item :label="$t('mappingsList.fields.currencyFrom')"
                         type="select"
                         :options="['FIELD', 'REGISTER']"
                         left-label
                         v-model="model['currencyFrom']"
                         :rules="[  val => !!val || $t('mappingsList.dialog.validations.currencyFromMustPresent') ]"/>
            <dialog-item :label="$t('mappingsList.fields.currencyField')"
                         v-if="model['currencyFrom'] === 'FIELD'"
                         type="select"
                         :options="stringMappings"
                         left-label
                         v-model="model['currencyField']"
                         :rules="[  val => !!val || $t('mappingsList.dialog.validations.currencyFieldMustPresent') ]"/>
            <dialog-item :label="$t('mappingsList.fields.currencyFromRegister')"
                         v-if="model['currencyFrom'] === 'REGISTER'"
                         type="select"
                         :options="['USD', 'EUR', 'RUB']"
                         left-label
                         v-model="model['currencyFromRegister']"
                         :rules="[  val => !!val || $t('mappingsList.dialog.validations.currencyFromRegisterMustPresent') ]"/>
            <dialog-item :label="$t('mappingsList.fields.currencyToRegister')"
                         type="select"
                         :options="['USD', 'EUR', 'RUB']"
                         left-label
                         v-model="model['currencyToRegister']"/>
          </template>
        </template>
        <dialog-item :label="$t('mappingsList.fields.isActive')"
                     type="toggler"
                     left-label
                     v-model="model['isActive']"/>
      </q-list>
    </dialog-modal>
  </div>
</template>

<script setup>

//todo fix 100 rows performance issue
import {useQuasar} from 'quasar';
import {useRoute, useRouter} from "vue-router";
import {computed, defineComponent, defineProps, onActivated, onMounted, reactive, ref, watch} from 'vue';
import {useI18n} from 'vue-i18n';

import DialogModal from 'components/dialogModal/DialogModal.vue';
import DialogItem from 'components/DialogItem.vue';
import dialogHelper from 'components/dialogModal/dialogHelper.js';
import CrudTable from "components/crud/CrudTable.vue";

import channelService from 'pages/Channels/services/channelService.js';

import {useUserStore} from "stores/user";
import {useChannelStore} from "stores/channel";
import {useMappingsStore} from "stores/mappings";

import {
  ADD_MAPPING_PRIVILEGE,
  ADMIN_PRIVILEGE,
  DELETE_MAPPING_PRIVILEGE,
  MODIFY_MAPPING_PRIVILEGE,
} from '../constants';
import {storeToRefs} from "pinia";

defineComponent({DialogModal, DialogItem});
// const emit = defineEmits(['updateMappings']);

const {t} = useI18n();
const $q = useQuasar();
const store = useUserStore();
const router = useRouter();
const route = useRoute();


const model = ref({});
const filter = ref(null);

onActivated(() => {
  if (route.params.channelId) {
    filter.value = route.params.channelId;
  }
});

const props = defineProps(['channel', 'isResponse']);
watch(() => props.channel, () => {
  filter.value = props.channel.id;
  model.value = {};

});

//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

//state
const modify = ref(false);

const columns = computed(() => [
  {
    name: 'field',
    label: t('mappingsList.fields.field'),
    align: 'left',
    field: 'field',
    sortable: true,
  },
  {
    name: 'attribute',
    label: t('mappingsList.fields.attribute'),
    align: 'left',
    field: 'attribute',
    sortable: true,
  },
  {
    name: 'type',
    label: t('mappingsList.fields.type'),
    align: 'left',
    field: 'type',
    sortable: true,
  },
  {
    name: 'description',
    label: t('mappingsList.fields.description'),
    align: 'left',
    field: 'description',
    sortable: true,
  },
  {
    name: 'isActive',
    label: t('mappingsList.fields.isActive'),
    align: 'left',
    field: 'isActive',
    sortable: true,
  }
]);

const visibleColumns = computed(() => {
  if (isMobile.value) {
    return ['field', 'attribute'];
  } else {
    return ['field', 'attribute', 'type', 'description', 'isActive'];
  }
});

//dialog
const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();

const onSelect = (row) => {
  // model.value = {...row};
};

const channelsStore = useChannelStore();

const selectMapping = (data) => {
  channelsStore.selectMapping(data)
}

const formattedField = (field) => {
  if (field instanceof Array && field.length > 1) {
    return field.map(item => {
      if (item instanceof Object) {
        return item.label;
      } else {
        return item;
      }
    }).join(', ');
  } else if (field instanceof Array && field.length === 1) {
    if (field[0] instanceof Object) {
      return field[0].label;
    } else {
      return field[0];
    }
  } else {
    return field;
  }
};

const mustStringFunctionPresent = (val) => {
  if (model.value['field'] instanceof Array && model.value['field'].length > 1 && model.value['type'] === 'STRING') {
    return !!val;
  } else {
    return true;
  }
};

const arrayValLessFour = (val) => {
  if (val instanceof Array && val > 1) {
    return val.length < 4;
  } else {
    return true;
  }
};

const stringMappings = computed(() => {
  return mappings.value?.map(item => ({value: item.id, label: item.field}));
});

const stringAttributes = computed(() => {
  if (props.isResponse) {
    return channelsStore.getResStringMappings?.map(item => ({value: item.id, label: item.attribute}));
  } else {
    return channelsStore.getReqStringMappings?.map(item => ({value: item.id, label: item.attribute}));
  }
});

// const mappings = computed(() => {
//   return channelsStore.reqMappings.map(item => item.attribute);
// });

const types = computed(() => {
  if (model.value['field'] instanceof Array && model.value['field'].length > 1) {
    return ['STRING'];
  } else {
    return ['STRING', 'NUMBER', 'DATE','BOOLEAN', 'AMOUNT'];
  }
});

const stringFunctions = computed(() => {
  if (model.value['field'] instanceof Array && model.value['field'].length > 1) {
    return ['CONCAT'];
  } else {
    return ['TRIM', 'REPLACE', 'SUBSTRING', 'CONCAT'];
  }
});



const formattedDescription = (row) => {
  if (row.type === 'DATE') {
    return `${row.dateFormat}`;
  } else if (row.type === 'STRING' && row.stringFunction === 'REPLACE') {
    return `Replace '${row.fromValue}' with '${row.toValue}'`;
  } else if (row.type === 'STRING' && row.stringFunction === 'SUBSTRING') {
    return `Substring from ${row.startIndex} to ${row.endIndex}`;
  } else if (row.type === 'STRING' && row.stringFunction === 'CONCAT') {
    return `Fields concated within '${row.concatDelimiter}'`;
  } else if (row.type === 'AMOUNT' && row.isNominal === 'false') {
    if (row.currencyFrom === 'FIELD') {
      return `Convert from '${row.currencyField.label}' field to ${row.currencyToRegister}`;
    } else if (row.currencyFrom === 'REGISTER') {
      return `Convert from ${row.currencyFromRegister} to ${row.currencyToRegister}`;
    }
  } else {
    return '';
  }
};

const mappingsStore = useMappingsStore();


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


//mappings
const mappings = ref([]);
const loadReqMappings = (channelId) => {
  return channelService.loadReqMappings(channelId).then(res => {
    mappings.value.splice(0, mappings.value.length, ...res);
    return res;
  });
};

const loadResMappings = (channelId) => {
  return channelService.loadResMappings(channelId).then(res => {
    mappings.value.splice(0, mappings.value.length, ...res);
    return res;
  });
};

const attributes = computed(() => {
  return mappings.value?.map(item => ({value: item.attribute, label: item.attribute}));
});

//CRUD operations
const mappingTable = ref(null);

const addMapping = () => {
  channelsStore.addMapping(filter.value, {...model.value}, props.isResponse)
    .then((res) => {
      onSubmit();
      mappingTable.value.load();
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
//todo after modification there is a unpredictable sorting, it's required to add sorting by id
const modifyMapping = () => {
  channelsStore.modifyMapping(filter.value, {...model.value}, props.isResponse)
    .then(() => {
      onSubmit();
      mappingTable.value.load();
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
const deleteMapping = (id) => {
  channelService.deleteMapping(filter.value, id, props.isResponse)
    .then(() => {
      model.value = {};
      mappingTable.value.load();
    })
};

//privileges
const user = ref({
  roles: [ADMIN_PRIVILEGE]
});
const canCreate = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(ADD_MAPPING_PRIVILEGE);
});
const canModify = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(MODIFY_MAPPING_PRIVILEGE);
});
const canDelete = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(DELETE_MAPPING_PRIVILEGE);
});

onMounted(() => {
  console.log('mappingList mounted');
});

</script>
<style>
</style>
