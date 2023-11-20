<template>
  <div id="students" class="column no-wrap">
    <crud-table
      style="min-height: 100px"
      ref="studentTable"
      row-id="studentId"
      :can-add="canCreate"
      :can-modify="canModify"
      :can-delete="canDelete"
      :columns="columns"
      :filter-prop="filter"
      :visible-columns="visibleColumns"
      :data-loader-func="studentsService.loadStudents"
      :load-listener-func="selectStudentList"
      @select="onSelect($event)"
      @go-add="goAdd"
      @go-modify="goModify"
      @delete="deleteStudent"
      :hide-pagination="true"
    >
      <template v-if="isMobile" v-slot:name="{row}">
        <div class="text-body2">
          {{ row.name }}
        </div>
      </template>
      <template v-if="isMobile" v-slot:amount="{row}">
        <div class="text-body2">
          {{ row.amount }}
        </div>
        <div class="flex justify-start">
          <div class="text-caption">
            <q-badge
              :color="row.paid ? 'green' : 'red'"
              :label="row.paid ? 'paid' : 'not paid' "/>
          </div>
        </div>
      </template>
    </crud-table>
    <div class="q-pa-md" style="border-top: 1px solid rgba(0, 0, 0, 0.12)" v-if="canCreate && isMobile">
      <q-btn
        color="primary"
        :label="$t('studentsList.buttons.add')"
        @click="goAdd()"
        class="full-width"
        rounded
        unelevated
        no-caps
      />
    </div>
    <dialog-modal :visible="isDialogVisible"
                  @submit="modify ? modifyStudent() : addStudent()"
                  @hide="onHide"
                  :title="modify ? $t('studentsList.dialog.header.modify') : $t('studentsList.dialog.header.add')">
      <q-list dense>
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
        <dialog-item :label="$t('studentsList.fields.amount')"
                     type="number"
                     left-label
                     v-model="model['amount']"
                     :rules="[  val => !!val || $t('studentsList.dialog.validations.amountMustPresent'),
                                val => !isNaN(val) || $t('studentsList.dialog.validations.amountMustBeNumber')]"/>
        <dialog-item :label="$t('studentsList.fields.comment')"
                     type="textarea"
                     left-label
                     v-model="model['comment']"
                     :rules="[ val => !val || (val && val.length < 200) || $t('studentsList.dialog.validations.commentLengthMax')  ]"/>
        <dialog-item :label="$t('studentsList.fields.paid')"
                     type="toggler"
                     left-label
                     v-model="model['paid']"/>
      </q-list>
    </dialog-modal>
  </div>
</template>

<script setup>
import {useQuasar} from 'quasar';
import {useRoute, useRouter} from "vue-router";
import {computed, defineComponent, defineProps, onActivated, ref, watch} from 'vue';
import {useI18n} from 'vue-i18n';

import DialogModal from 'components/dialogModal/DialogModal.vue';
import DialogItem from 'components/DialogItem.vue';
import dialogHelper from 'components/dialogModal/dialogHelper.js';
import CrudTable from "components/crud/CrudTable.vue";

import studentsService from 'pages/Students/services/studentService';

import {useUserStore} from "stores/user";
import {useGroupStore} from "stores/group";

import {
  ADD_STUDENT_TO_GROUP_PRIVILEGE,
  ADMIN_PRIVILEGE,
  DELETE_STUDENT_FROM_GROUP_PRIVILEGE,
  MODIFY_STUDENT_IN_GROUP_PRIVILEGE
} from './constants';

defineComponent({DialogModal, DialogItem});

const {t} = useI18n();
const $q = useQuasar();
const store = useUserStore();
const router = useRouter();
const route = useRoute();
const studentTable = ref(null);

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
    name: 'name',
    label: t('studentsList.fields.name'),
    align: 'left',
    field: 'name',
    sortable: true,
  },
  {
    name: 'phone',
    label: t('studentsList.fields.phone'),
    align: 'left',
    field: 'phone',
    sortable: true,
  },
  {
    name: 'amount',
    label: t('studentsList.fields.amount'),
    align: 'left',
    field: 'amount',
    sortable: true,
  },
  {
    name: 'comment',
    label: t('studentsList.fields.comment'),
    align: 'left',
    field: 'comment',
    sortable: true,
  },
  {
    name: 'paid',
    label: t('studentsList.fields.paid'),
    align: 'left',
    field: 'paid',
    sortable: true,
  },
  {
    name: 'studentId',
    label: t('studentsList.fields.paid'),
    align: 'left',
    field: 'studentId',
    sortable: true,
  },
  {
    name: 'groupId',
    label: t('studentsList.fields.paid'),
    align: 'left',
    field: 'groupId',
    sortable: true,
  }
]);
const visibleColumns = computed(() => {
  if (isMobile.value) {
    return ['name', 'amount', 'phone'];
  } else {
    return ['name', 'amount', 'phone', 'comment', 'paid'];
  }
});

//dialog
const {isDialogVisible, showDialog, onHide, onSubmit} = dialogHelper();

const onSelect = (row) => {
  model.value = {...row};
};

const groupsStore = useGroupStore();
const selectStudentList = (data) => {
  console.log(data)
  groupsStore.selectStudentList(data)
}

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
const addStudent = () => {
  studentsService.addStudent({...model.value, ...{groupId: filter.value}})
    .then(() => {
      onSubmit();
      studentTable.value.load();
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
const modifyStudent = () => {
  studentsService.modifyStudent({...model.value})
    .then(() => {
      onSubmit();
      studentTable.value.load();
      model.value = {};
      modify.value = false;
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
const deleteStudent = (id) => {
  studentsService.deleteStudent(id, filter.value)
    .then(() => {
      model.value = {};
      studentTable.value.load();
    })
};

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
</style>
