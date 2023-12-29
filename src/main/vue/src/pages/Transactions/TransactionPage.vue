<template>
  <div>
    <div class="relative-position">
      <div class="q-ma-lg bg-white q-pa-md flex items-center justify-between" id="filter-panel">
        <div class="flex">
          <q-input
            v-model="filter"
            outlined
            dense
            placeholder="Filter"
            class="q-mb-md"
          />
          <q-input
            v-model="filter"
            outlined
            dense
            placeholder="Filter"
            class="q-mb-md q-ml-md"
          />
          <q-input
            v-model="filter"
            outlined
            dense
            placeholder="Filter"
            class="q-mb-md q-ml-md"
          />
        </div>
        <q-btn
          color="primary"
          label="Filter"
          class="q-mb-md"
        />
      </div>
      <transactions-list v-if="!isMobile" :channel="channel"/>
    </div>
  </div>
</template>

<script setup>
import ChannelsList from "pages/Channels/components/ChannelsList.vue";
import ChannelDetailsPane from "pages/Channels/components/ChannelDetailsPane.vue";

import {defineComponent, ref, computed} from 'vue';
import {useQuasar} from "quasar";
import TransactionsList from "pages/Transactions/components/TransactionsList.vue";

defineComponent({ChannelsList, ChannelDetailsPane});

const channel = ref({});

const $q = useQuasar();
const isMobile = computed(() => $q.screen.lt.sm);

</script>

<style scoped>

</style>
