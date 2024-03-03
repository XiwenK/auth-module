<template>
    <div class="relative-position">
      <user-list/>
    </div>
</template>

<script setup>

import SpaceList from "pages/Spaces/SpaceList.vue";
import {useQuasar} from "quasar";
import {defineComponent, ref, computed} from 'vue';

defineComponent({SpaceList});

const $q = useQuasar();

const group = ref({});
const isMobile = computed(() => $q.screen.lt.sm);

</script>
