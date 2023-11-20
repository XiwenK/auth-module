<template>
  <q-dialog v-model="localVisible" @hide="onHide">
    <q-card style="width: 400px">

      <q-card-section>
        <div class="text-h6">
          {{ title }}
        </div>
      </q-card-section>

      <q-form ref="form" class="q-gutter-md">
        <q-card-section class="q-pt-none">
          <slot></slot>
        </q-card-section>
      </q-form>

      <q-card-actions align="right">
        <q-btn flat :label="$t('buttons.cancel')" color="primary" @click="onCancelClick"/>
        <q-btn flat :label="$t('buttons.ok')" color="primary" @click="onOKClick"/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import {useI18n} from 'vue-i18n';
import {ref, defineProps, defineEmits, watch} from 'vue';

//localization
const {t} = useI18n();

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: 'Dialog title'
  }
});

const emit = defineEmits(['submit', 'hide']);
const form = ref(null);

const localVisible = ref(props.visible);
watch(() => props.visible, (newVal) => {
  localVisible.value = newVal;
});


function onOKClick() {
  form.value.validate()
    .then(success => {
      if (success) {
        emit('submit');
      } else {

      }
    })
}

function onHide() {
  emit('hide');
}

function onCancelClick() {
  emit('hide');
}

</script>

<style scoped>

</style>
