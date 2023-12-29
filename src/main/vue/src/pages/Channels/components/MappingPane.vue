<template>
  <div
    class="q-pa-sm flex-column mapping-pane"
    :class="isMobile ? 'mobile' : ''"
  >
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
      <q-tab name="request" label="Request" />
      <q-tab name="response" label="Response" />
    </q-tabs>
    <q-separator />
    <q-tab-panels
      v-model="tab"
      keep-alive
    >
      <q-tab-panel name="request">
        <mapping-list :channel="channel"/>
      </q-tab-panel>

      <q-tab-panel name="response">
        <mapping-list :channel="channel" :is-response="true"/>
      </q-tab-panel>

    </q-tab-panels>
  </div>
</template>

<script setup>
import {onMounted, watch, defineProps, computed} from "vue";
import {ref} from "vue";
import {useQuasar} from "quasar";
import MappingList from "pages/Channels/components/MappingList.vue";

const props = defineProps(['channel']);
const places = ref([]);
const $q = useQuasar();

const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

const tab = ref('request');
watch(() => tab.value, () => {
  console.log(tab.value)
  console.log(props.channel)
});

</script>

<style scoped>
.container {
  display: grid;
  grid-template-columns: repeat(20, 1fr); /* Создаем 3 столбца равной ширины */
  grid-gap: 10px; /* Расстояние между ячейками */
}

.chart {
  width: 700px;
  margin: 0 auto;
}

.item {
  background-color: rgba(52, 152, 219, 0.13);
  color: white;
  padding: 20px;
  text-align: center;
  font-size: 12px;
}

.item.selected {
  background-color: rgba(52, 152, 219, 0.5);
}
</style>
