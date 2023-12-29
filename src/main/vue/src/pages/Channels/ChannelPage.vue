<template>
  <div>
    <div class="relative-position">
      <channels-list @select-channel="channel = $event"/>
      <channel-details-pane v-if="!isMobile" :channel="channel"/>
    </div>
  </div>
</template>

<script setup>
import ChannelsList from "pages/Channels/components/ChannelsList.vue";
import ChannelDetailsPane from "pages/Channels/components/ChannelDetailsPane.vue";

import {defineComponent, ref, computed} from 'vue';
import {useQuasar} from "quasar";

defineComponent({ChannelsList, ChannelDetailsPane});

const channel = ref({});

const $q = useQuasar();
const isMobile = computed(() => $q.screen.lt.sm);

</script>

<style scoped>

</style>
