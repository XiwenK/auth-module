<template>
  <div>
    <dialog-item :label="$t('constantCheck.attribute')"
                 type="string"
                 left-label/>
    <dialog-item :label="$t('constantCheck.index')"
                 type="select"
                 :options="['original', '0', '1', '2', '3']"
                 v-model="model['index']"
                 left-label/>
    <dialog-item :label="$t('constantCheck.historyBy')"
                 v-if="model['index'] && model['index'] !== 'original' && model['index'] !== '0'"
                 type="select"
                 :options="['HPAN', 'CUSTOMER', 'MERCHANT_ID', 'TERMINAL']"
                 left-label/>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import DialogItem from "components/DialogItem.vue";

const model = ref({});
</script>

<style scoped>

</style>
