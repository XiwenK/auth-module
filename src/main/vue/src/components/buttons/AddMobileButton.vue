<template>
  <div class="q-pa-md mobile-button">
    <q-btn
      color="primary"
      :label="label"
      @click="emit('add')"
      class="full-width"
      rounded
      unelevated
      no-caps
    />
  </div>
</template>

<script setup>
import {useI18n} from 'vue-i18n';
import {defineEmits, defineProps} from 'vue';

const {t} = useI18n();
const props = defineProps(['label']);
const emit = defineEmits(['add']);
</script>

<style scoped>
.mobile-button {
  border-top: 1px solid rgba(0, 0, 0, 0.12);
}
</style>
