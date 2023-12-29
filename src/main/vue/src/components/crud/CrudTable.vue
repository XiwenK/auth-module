<template>
  <q-table
    class="sticky-header-table full-height crud-table"
    :class="{'mobile': isMobile}"
    flat
    :rows="rows"
    :columns="columns"
    :visible-columns="visibleColumns"
    :loading="loading"
    v-model:pagination="pagination"
    :rows-per-page-label="$t('recordsPerPage')"
    :filter="filter"
    dense
    :row-key="rowId"
    :selection="'none'"
    v-model:selected="selected"
    @request="load"
    :hide-pagination="hidePagination"
  >
    <template v-if="!isMobile && showCrudButtons" v-slot:top-left>
      <button-crud
        v-if="canAdd"
        icon="add"
        :compact="compactButtons"
        :label="$t('buttons.add')"
        @click="goAdd"
      />
      <button-crud
        v-if="canModify"
        :disabled="!isModifyEnabled"
        icon="edit"
        :compact="compactButtons"
        :label="$t('buttons.modify')"
        @click="goModify"
        class="q-ml-sm"
      />
      <button-crud
        v-if="canDelete"
        :disabled="!isDeleteEnabled"
        icon="delete"
        :compact="compactButtons"
        :label="$t('buttons.delete')"
        @click="deleteGroup(selected[0])"
        class="q-ml-sm"
      />
    </template>
    <template v-slot:body="props">
      <group-mobile-row
        :data="props"
        :selected="model.id"
        @select="onSelect(props.row)"
        @click="onClick(props.row)"
        @edit="goModify()"
        @delete="deleteGroup(props.row)"
      >
        <template v-slot:[getSlotName(col)]="{row, column}" v-for="col in props.cols">
          <slot :name="getSlotName(col)" :row="row" :column="column">
            {{row[column.name]}}
          </slot>
        </template>
      </group-mobile-row>
    </template>
  </q-table>
</template>

<script setup>
import {QBtn, QTable, useQuasar} from 'quasar';
import {computed, defineComponent, defineEmits, defineExpose, defineProps, onMounted, ref, unref, watch,} from 'vue';
import ConfirmationDialog from 'components/ConfirmationDialog.vue';
import GroupMobileRow from "pages/Groups/components/MobileRow.vue";
import ButtonCrud from "components/buttons/ButtonCrud.vue";
import {useI18n} from 'vue-i18n';
import crudHelper from 'src/rest/crudHelper.js';
import {useViewSettings} from "stores/useViewSettings";

defineComponent({ConfirmationDialog, QTable, QBtn});
const props = defineProps({
  dataLoaderFunc: {
    type: Function,
    default: () => {
    }
  },
  loadListenerFunc: {
    type: Function,
    default: () => {}
  },
  dataLoaderCountFunc: {
    type: Function,
    default: null
  },
  columns: {
    type: Array,
    default: () => []
  },
  visibleColumns: {
    type: Array,
    default: () => []
  },
  canAdd: {
    type: Boolean,
    default: true
  },
  canModify: {
    type: Boolean,
    default: true
  },
  canDelete: {
    type: Boolean,
    default: true
  },
  filterProp: {
    type: String,
    default: null
  },
  rowId: {
    type: String,
    default: 'id'
  },
  hidePagination: {
    type: Boolean,
    default: false
  },
  deleteMessage: {
    type: String,
    default: null
  },
  compactButtons: {
    type: Boolean,
    default: true
  },
  showCrudButtons: {
    type: Boolean,
    default: true
  }
});
const emit = defineEmits(['select', 'goAdd', 'goModify', 'delete'])
const {t} = useI18n();
const $q = useQuasar();

watch(() => props.filterProp, () => {
  // if (props.filterProp) {
  console.log('filterProp', props.filterProp)
    filter.value = props.filterProp;
    selected.value = [];
  // }
});

//state
const selected = ref([]);

//Mobile mode
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

//Height
const store = useViewSettings();
const height = computed(() => {
  return store.pageHeight + 'px';
});

const isModifyEnabled = computed(() => {
  return props.canAdd && selected.value[0];
});

const isDeleteEnabled = computed(() => {
  return props.canDelete && selected.value[0];
});
const getSlotName = (col) => {
  return col.name ? col.name : col.field;
};

const onSelect = (row) => {
  selected.value[0] = {...row}
};

const onClick = (row) => {
  selected.value[0] = {...row}
  emit('select', selected.value[0]);
};

const goAdd = () => {
  emit('goAdd')
};
const goModify = () => {
  emit('goModify', selected.value[0]);
};
//data loader
const {loading, load, pagination, filter, rows, dataLoader, dataCountLoader, model, loadListeners} = crudHelper();
dataLoader.value = unref(props.dataLoaderFunc);
dataCountLoader.value = unref(props.dataLoaderCountFunc);
loadListeners.value.push(unref(props.loadListenerFunc));

const deleteGroup = (row) => {
  $q.dialog({
    component: ConfirmationDialog,
    componentProps: {
      text: props.deleteMessage ? props.deleteMessage : t('deleteConfirmation')
    }
  }).onOk(() => {
    emit('delete', row[props.rowId]);
  }).onCancel(() => {
  }).onDismiss(() => {
  })
};

defineExpose({
  load
});

onMounted(() => {
  load();
});

</script>
<style lang="sass">
.add-button
  position: fixed
  bottom: 48px
  right: 20px

.sticky-header-table
  /* height or max-height is important */
  //height: 610px

  .q-table__top,
  .q-table__bottom,
  thead tr:first-child th
    /* bg color is important for th; just specify one */
    background-color: white

  thead tr th
    position: sticky

  thead tr:first-child th
    top: -1px
    z-index: 4

  /* this is when the loading indicator appears */
  &.q-table--loading thead tr:last-child th
    /* height of all previous header rows */
    top: 48px

  /* prevent scrolling behind sticky top row on focus */
  tbody
    /* height of all previous header rows */
    scroll-margin-top: 48px

  .q-table
    position: relative
</style>
