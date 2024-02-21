<template>
  <div>
    <div class="relative-position">
      <GroupsList @select-group="group = $event"/>
<!--      <RulesList v-if="!isMobile" :group="group"/>-->
      <GroupDetailsPane v-if="!isMobile" :group="group"/>
    </div>
  </div>
</template>

<script setup>
import GroupsList from "pages/Groups/GroupsList.vue";
import RulesList from "pages/Groups/components/RulesList.vue";
import GroupDetailsPane from "pages/Groups/components/GroupsDetailsPane.vue";
import {useQuasar} from "quasar";
import {defineComponent, ref, computed} from 'vue';
import {useUserStore} from "stores/user";

defineComponent({GroupsList, RulesList, GroupDetailsPane});

const store = useUserStore();
const $q = useQuasar();

const group = ref({});
const isMobile = computed(() => $q.screen.lt.sm);

</script>
