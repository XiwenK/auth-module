<template>
  <div>
    <div class="relative-position">
      <resources-list @select-resource="resource = $event"/>
      <resource-details-pane v-if="!isMobile" :group="group"/>
    </div>
  </div>
</template>

<script setup>
import {useQuasar} from "quasar";
import {defineComponent, ref, computed} from 'vue';
import {useUserStore} from "stores/user";
import ResourcesList from "pages/Resources/components/ResourcesList.vue";
import ResourceDetailsPane from "pages/Resources/components/ResourceDetailsPane.vue";

defineComponent({ResourcesList, ResourceDetailsPane});

const store = useUserStore();
const $q = useQuasar();

const resource = ref({});
const isMobile = computed(() => $q.screen.lt.sm);

</script>
