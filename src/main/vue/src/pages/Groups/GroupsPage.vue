<template>
    <div class="relative-position">
      <GroupsList @select-group="group = $event"/>
      <GroupDetailsPane v-if="!isMobile" :group="group"/>
    </div>
</template>

<script setup>

import GroupsList from "pages/Groups/GroupsList.vue";
import GroupDetailsPane from "pages/Groups/components/GroupsDetailsPane.vue";
import {useQuasar} from "quasar";
import {defineComponent, ref, computed} from 'vue';

defineComponent({GroupsList, GroupDetailsPane});

const $q = useQuasar();

const group = ref({});
const isMobile = computed(() => $q.screen.lt.sm);

</script>
