<template>
  <div class="row no-wrap"
       :class="isMobile ? 'overflow-auto q-pb-sm' : '' ">
    <template v-for="widget in widgets" :key="widget.title">
      <percent-widget v-if="widget.type === 'percent'" :widget="widget"/>
    </template>
  </div>
</template>
<script setup>

import PercentWidget from "components/widgets/PercentWidget.vue";
import {useI18n} from "vue-i18n";
import {computed, defineComponent, defineProps} from "vue";
import {useQuasar} from "quasar";

defineComponent({PercentWidget});
defineProps(['widgets']);

const $q = useQuasar();
const isMobile = computed(() => $q.screen.lt.sm);
const {t} = useI18n();

</script>
