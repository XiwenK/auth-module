<template>
  <div class="flex items-center full-width justify-center">
    <div class="q-pr-sm"
        :class="isMobile ? 'q-pb-sm':''">
      {{ $t('linkPanel.invite') }}
    </div>
    <div class="q-pa-sm flex items-center bg-grey-3 rounded-borders">
      <div>
        {{ fullUrl }}
      </div>
      <q-btn
        color="primary"
        icon="content_copy"
        size="sm"
        no-caps
        unelevated
        class="q-ml-sm"
        @click="copy"
      />
    </div>
  </div>
</template>

<script setup>
import {copyToClipboard, useQuasar} from "quasar";
import {useRoute} from "vue-router";
import {useI18n} from "vue-i18n";
import {computed, defineProps, onActivated, ref, watch} from 'vue';

import linkService from "components/linkPanel/services/linkService";

const $q = useQuasar();
const route = useRoute();
const {t} = useI18n();

const props = defineProps(['group']);
watch(() => props.group, () => {
  loadUrl(props.group.id);
});

onActivated(() => {
  loadUrl(route.params.groupId);
});

const isMobile = computed(() => $q.screen.lt.sm);

const url = ref(null);
const loadUrl = (id) => {
  if (!id) return;
  linkService.getUrl(id)
    .then((response) => { url.value = response.data;})
    .catch((err)=> {
      if (err.response?.status !== 404) {
        $q.notify({
          message: err.message,
          color: 'negative',
          position: 'top'
        });
      } else {
        url.value = null;
      }
    });
};

const fullUrl = computed(() => {
  if (url.value === null) return t('linkPanel.noUrl')
  return `${process.env.REG_URL}${url.value}`;
});

const copy = () => {
  copyToClipboard(fullUrl.value);
  $q.notify({message: t('linkPanel.urlCopied'), position: 'top'});
};

</script>

<style scoped>

</style>
