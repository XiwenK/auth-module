<template>
  <page-layout class="page-desktop-right">
    <page-header>
      <template #title>
        {{$t('channelsList.channelDetailsTitle')}}
      </template>
      <template #buttons-left>
        <page-header-btn-back
          :label="$t('channelsList.backButton')"
        />
      </template>
    </page-header>
    <page-body>
      <mapping-pane :channel="channel"/>
    </page-body>
  </page-layout>
</template>

<script setup>
import MappingPane from "pages/Channels/components/MappingPane.vue";
import {defineProps} from 'vue';
const props = defineProps(['channel']);
</script>
