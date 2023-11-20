<template>
  <div ref="pageBodyRef" style="flex-grow: 1">
    <q-scroll-area
      class="full-height"
      :vertical-thumb-style="{zIndex: 3}"
    >
      <slot/>
    </q-scroll-area>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted, nextTick, computed} from "vue";
import {useViewSettings} from "stores/useViewSettings";

//height
const viewSettings = useViewSettings();
const pageBodyRef = ref(null);
const updateHeight = () => {
  if (pageBodyRef.value.offsetHeight) {
    viewSettings.pageHeight = pageBodyRef.value.offsetHeight;
  }
};

const resizeHandler = () => {
  requestAnimationFrame(updateHeight);
};

const height = computed(() => viewSettings.pageHeight + 'px');

onMounted(() => {
  window.addEventListener('resize', resizeHandler);
  resizeHandler();
});

onUnmounted(() => {
  window.removeEventListener('resize', resizeHandler);
});


</script>

<style scoped lang="sass">
.page-body
  &__spacer
    &-header
      height: 50px
    &-footer
      height: 50px
</style>
