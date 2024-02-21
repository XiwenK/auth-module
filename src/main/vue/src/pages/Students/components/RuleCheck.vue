<template>
  <dialog-modal :visible="isDialogVisible"
                width="800px"
                @submit="modify ? modifyCheck() : addCheck()"
                @hide="onHide"
                class="condition-dialog"
                :title="modify ? $t('studentsList.dialog.header.modify') : $t('studentsList.dialog.header.add')">
    <q-tabs
      v-model="tab"
      dense
      class="text-grey"
      active-color="primary"
      indicator-color="primary"
      align="justify"
      narrow-indicator
      no-caps
    >
      <q-tab name="constant" label="Constant" />
      <q-tab name="list" label="List" />
<!--      <q-tab name="arithmetic" label="Arithmetic" />-->
    </q-tabs>
    <q-separator />
    <q-tab-panels
      v-model="tab"
      keep-alive
    >
      <q-tab-panel name="constant">
        <constant-check/>
      </q-tab-panel>
      <q-tab-panel name="list">
        <list-check/>
      </q-tab-panel>

    </q-tab-panels>
  </dialog-modal>
</template>

<script setup>
import DialogModal from "components/dialogModal/DialogModal.vue";
import {defineComponent, defineProps, defineEmits, ref, watch, computed} from 'vue';
import ConstantCheck from "pages/Students/components/ConstantCheck.vue";
import ListCheck from "pages/Students/components/ListCheck.vue";
import ArithmeticCheck from "pages/Students/components/ArithmeticCheck.vue";

defineComponent({DialogModal});
const emit = defineEmits(['close']);
const props = defineProps(['isDialogVisible', 'modify']);

const tab = ref('constant');

const modifyCheck = () => {
  console.log('modifyCheck')
}

const addCheck = () => {
  console.log('addCheck')
}

const onHide = () => {
  emit('close');
}

</script>

<style scoped>

</style>
