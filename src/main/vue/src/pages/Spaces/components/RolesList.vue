<template>
  <div id="roles" class="column no-wrap">
    <crud-table
      style="min-height: 100px"
      ref="roleTable"
      row-id="id"
      :can-add="canCreate"
      :can-modify="canModify"
      :can-delete="canDelete"
      :columns="columns"
      :filter-prop="filter"
      :visible-columns="visibleColumns"
      :data-loader-func="roleService.loadRoles"
      :data-loader-count-func="roleService.loadRolesCount"
      @select="onSelect($event)"
      @go-add="goAdd"
      :compact-buttons="false"
      @go-modify="goModify"
      @delete="deleteItem"
      @dblclick="goModify"
    >
      <template v-if="!isMobile"
                v-slot:id="{row}">
        <div class="text-body2">
          {{ row.id }}
        </div>
      </template>
      <template v-if="isMobile"
                v-slot:name="{row}">
        <div class="text-body2">
          {{ row.name }}
        </div>
      </template>
      <template v-if="isMobile" v-slot:desc="{row}">
        <div class="text-body2">
          {{ row.desc }}
        </div>
      </template>
    </crud-table>
    <dialog-modal :visible="isDialogVisible"
                  @submit="modifyMode ? modify() : add()"
                  @hide="onHide"
                  :title="modify ? $t('roleList.dialog.header.modify') : $t('roleList.dialog.header.add')">
      <q-list dense>
        <dialog-item :label="$t('roleList.fields.name')"
                     type="string"
                     v-model="model['name']"
                     :rules="[  val => !!val || $t('roleList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('roleList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('roleList.dialog.validations.nameLengthMax') ]"/>
        <dialog-item :label="$t('roleList.fields.desc')"
                     type="string"
                     v-model="model['desc']"
                     :rules="[  val => !!val || $t('roleList.dialog.validations.nameMustPresent'),
                                val => val.length >= 3 || $t('roleList.dialog.validations.nameLength'),
                                val => val.length <= 50 || $t('roleList.dialog.validations.nameLengthMax') ]"/>
      </q-list>
    </dialog-modal>
    <add-mobile-button
      v-if="canCreate && isMobile"
      :label="$t('roleList.buttons.add')"
      @add="goAdd"/>
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
import AddMobileButton from "components/buttons/AddMobileButton.vue";

import roleService from 'pages/Groups/services/roleService';

import {useUserStore} from "stores/user";

import {
  ADD_ROLE_PRIVILEGE,
  ADMIN_PRIVILEGE,
  DELETE_ROLE_PRIVILEGE,
  MODIFY_ROLE_PRIVILEGE
} from '../constants';

defineComponent({DialogModal, DialogItem, CrudTable, AddMobileButton});
const emit = defineEmits(['modifyRule']);

const {t} = useI18n();
const $q = useQuasar();
const store = useUserStore();
const router = useRouter();
const route = useRoute();
const roleTable = ref(null);

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

const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();

//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

//state
const modifyMode = ref(false);
const columns = computed(() => [
  {
    name: 'id',
    label: t('roleList.fields.id'),
    align: 'left',
    field: 'id',
    sortable: true,
  },
  {
    name: 'name',
    label: t('roleList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'desc',
    label: t('roleList.fields.desc'),
    align: 'left',
    field: 'desc',
    sortable: true,
  }
]);
const visibleColumns = computed(() => {
  if (isMobile.value) {
    return ['name', 'desc'];
  } else {
    return ['id', 'name', 'desc'];
  }
});

const onSelect = (row) => {
  model.value = {...row};
};

const goAdd = () => {
  model.value = {};
  modifyMode.value = false;
  showDialog();
};
const goModify = (selected) => {
  model.value = {...selected};
  modifyMode.value = true;
  showDialog();
};
//CRUD
const add = () => {
  roleService.addROle({...model.value, ...{groupId: filter.value}})
    .then(() => {
      onSubmit();
      roleTable.value.load();
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
//
// //todo after modification there is a unpredictable sorting, it's required to add sorting by id
const modify = () => {
  roleService.modifyStudent({...model.value})
    .then(() => {
      onSubmit();
      roleTable.value.load();
      model.value = {};
      modifyMode.value = false;
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
const deleteItem = (id) => {
  roleService.deleteRole(id, filter.value)
    .then(() => {
      model.value = {};
      roleTable.value.load();
    })
};

//privileges
const user = ref({
  roles: [ADMIN_PRIVILEGE]
});
const canCreate = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(ADD_ROLE_PRIVILEGE);
});
const canModify = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(MODIFY_ROLE_PRIVILEGE);
});
const canDelete = computed(() => {
  return user.value?.roles?.includes(ADMIN_PRIVILEGE) || user.value?.roles?.includes(DELETE_ROLE_PRIVILEGE);
});

</script>
<style>
.clickable{
  cursor: pointer;
  text-decoration: underline;
}
</style>
