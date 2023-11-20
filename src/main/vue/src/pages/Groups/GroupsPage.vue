<template>
  <div>
    <div class="relative-position">
      <GroupsList @select-group="group = $event"/>
      <GroupDetailsPane v-if="!isMobile" :group="group"/>
    </div>
  </div>
</template>

<script setup>
import GroupsList from "pages/Groups/GroupsList.vue";
import GroupDetailsPane from "pages/Students/GroupDetailsPane.vue";
import {useQuasar} from "quasar";
import {defineComponent, ref, computed} from 'vue';
import {useUserStore} from "stores/user";

defineComponent({GroupsList, GroupDetailsPane});

const store = useUserStore();
const $q = useQuasar();

const group = ref({});
const isMobile = computed(() => $q.screen.lt.sm);

</script>
