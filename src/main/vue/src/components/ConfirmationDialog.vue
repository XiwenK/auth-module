<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide">
    <q-card>
      <q-card-section
        class="row items-center no-wrap"
      >
        <q-avatar icon="delete" color="primary" text-color="white"/>
        <span
          class="q-ml-sm"
        >
          {{ text }}
        </span>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat :label="$t('buttons.cancel')" color="primary" v-close-popup @click="onDialogHide"/>
        <q-btn flat :label="$t('buttons.ok')" color="primary" v-close-popup @click="onConfirm"/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import {useDialogPluginComponent} from 'quasar';
import {defineEmits, defineProps} from 'vue';
import {useI18n} from 'vue-i18n';

//localization
const {t} = useI18n();

const emit = defineEmits([
  ...useDialogPluginComponent.emits,
  'confirm'
]);
const props = defineProps(['text']);

const {dialogRef, onDialogHide, onDialogOK, onDialogCancel} = useDialogPluginComponent();

const onConfirm = () => {
  emit('confirm');
  onDialogOK()
};

</script>

<style scoped>

</style>
