<template>
  <div id="rules" class="column no-wrap">
    <crud-table
      style="min-height: 100px"
      ref="ruleTable"
      row-id="id"
      :can-add="canCreate"
      :can-modify="canModify"
      :can-delete="canDelete"
      :columns="columns"
      :filter-prop="filter"
      :visible-columns="visibleColumns"
      :data-loader-func="ruleService.loadRules"
      :data-loader-count-func="ruleService.loadRulesCount"
      @select="onSelect($event)"
      @go-add="goAdd"
      :compact-buttons="false"
      @dblclick="goModify"
    >
      <template v-if="!isMobile"
                v-slot:id="{row}">
        <div class="text-body2 clickable"
             @click="goModify">
          {{ row.id }}
        </div>
      </template>
      <template v-if="isMobile"
                v-slot:name="{row}">
        <div class="text-body2 clickable"
             @click="goModify">
          {{ row.name }}
        </div>
      </template>
      <template v-if="isMobile" v-slot:fp="{row}">
        <div class="text-body2">
          {{ row.fp }}
        </div>
      </template>

    </crud-table>
    <div class="q-pa-md" style="border-top: 1px solid rgba(0, 0, 0, 0.12)" v-if="canCreate && isMobile">
      <q-btn
        color="primary"
        :label="$t('rulesList.buttons.add')"
        @click="goAdd()"
        class="full-width"
        rounded
        unelevated
        no-caps
      />
    </div>
  </div>
</template>

<script setup>
import {useQuasar} from 'quasar';
import {useRoute, useRouter} from "vue-router";
import {computed, defineComponent, defineProps, onActivated, ref, watch, defineEmits } from 'vue';
import {useI18n} from 'vue-i18n';

import DialogModal from 'components/dialogModal/DialogModal.vue';
import DialogItem from 'components/DialogItem.vue';
import dialogHelper from 'components/dialogModal/dialogHelper.js';
import CrudTable from "components/crud/CrudTable.vue";

import ruleService from 'pages/Groups/services/ruleService';

import {useUserStore} from "stores/user";
import {useGroupStore} from "stores/group";

import {
  ADD_RULE,
  ADMIN_PRIVILEGE,
  MODIFY_RULE,
  DELETE_RULE
} from '../constants';

defineComponent({DialogModal, DialogItem});
const emit = defineEmits(['modifyRule']);

const {t} = useI18n();
const $q = useQuasar();
const store = useUserStore();
const router = useRouter();
const route = useRoute();
const ruleTable = ref(null);

const model = ref({});

const filter = ref(null);
onActivated(() => {
  filter.value = route.params.groupId;
});

const props = defineProps(['group']);
watch(() => props.group, () => {
  filter.value = props.group.id;
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
    name: 'id',
    label: t('rulesList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'name',
    label: t('rulesList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'fp',
    label: t('rulesList.fields.fp'),
    align: 'left',
    field: 'fp',
    sortable: true,
  }
]);
const visibleColumns = computed(() => {
  if (isMobile.value) {
    return ['name', 'fp'];
  } else {
    return ['id', 'name', 'fp'];
  }
});

const onSelect = (row) => {
  model.value = {...row};
};

const goAdd = () => {
  model.value = {};
  modify.value = false;
};
const goModify = (selected) => {
  model.value = {...selected};
  modify.value = true;
  emit('modifyRule', model.value);
};
//CRUD
// const addStudent = () => {
//   ruleService.addStudent({...model.value, ...{groupId: filter.value}})
//     .then(() => {
//       onSubmit();
//       ruleTable.value.load();
//       model.value = {};
//     })
//     .catch(res => {
//       $q.notify({
//         color: 'negative',
//         message: t(res?.response?.data?.message) || '',
//         icon: 'report_problem',
//         position: 'top-right'
//       })
//     })
// };
//
// //todo after modification there is a unpredictable sorting, it's required to add sorting by id
// const modifyStudent = () => {
//   ruleService.modifyStudent({...model.value})
//     .then(() => {
//       onSubmit();
//       ruleTable.value.load();
//       model.value = {};
//       modify.value = false;
//     })
//     .catch(res => {
//       $q.notify({
//         color: 'negative',
//         message: t(res?.response?.data?.message) || '',
//         icon: 'report_problem',
//         position: 'top-right'
//       })
//     })
// };
// const deleteRule = (id) => {
//   ruleService.deleteRule(id, filter.value)
//     .then(() => {
//       model.value = {};
//       ruleTable.value.load();
//     })
// };

//privileges
const user = ref({
  roles: [ADMIN_PRIVILEGE]
});
const canCreate = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(ADD_STUDENT_TO_GROUP_PRIVILEGE);
});
const canModify = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(MODIFY_STUDENT_IN_GROUP_PRIVILEGE);
});
const canDelete = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(DELETE_STUDENT_FROM_GROUP_PRIVILEGE);
});

</script>
<style>
.clickable{
  cursor: pointer;
  text-decoration: underline;
}
</style>
