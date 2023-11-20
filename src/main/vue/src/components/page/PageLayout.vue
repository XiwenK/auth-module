<template>
  <div
    id="page-layout"
    class="page bg-white"
    :style="{height: height}"
    :class="{'absolute-top': $q.screen.lt.md, 'desktop': !$q.screen.lt.md}">
    <div
      class="flex column justify-between page-nudger full-height no-wrap"
      :class="{'nudge-left': hasActiveChildPage}"
      v-touch-swipe.mouse.right=""
    >
      <slot/>
    </div>
    <router-view v-slot="{ Component }">
      <transition
        appear
        enter-active-class="animated slideInRight"
        leave-active-class="animated slideOutRight"
        :css="true"
      >
        <keep-alive>
          <component
            @pageActivated="hasActiveChildPage=true"
            @pageDeactivated="hasActiveChildPage=false"
            :class="{'active': hasActiveChildPage}"
            :is="Component"
          />
        </keep-alive>
      </transition>
    </router-view>
  </div>
</template>

<script setup>

import {computed, onActivated, onDeactivated, defineEmits, ref} from "vue";
import {useQuasar} from "quasar";

const emit = defineEmits(['pageActivated', 'pageDeactivated']);
const $q = useQuasar();


//mobile
const isMobile = computed(() => {
  return $q.screen.lt.sm;
});

const height = computed(() => {
  return isMobile.value ? 'calc(100vh - 73px)' : 'calc(100vh - 100px)';
});

const hasActiveChildPage = ref(false);

onActivated(() => {
  emit('pageActivated');
})

onDeactivated(() => {
  emit('pageDeactivated');
})

</script>

<style scoped lang="scss">
</style>
