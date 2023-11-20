<template>
  <q-tab
        class="text-size-12 rounded-borders"
        :class="color ? `text-${color}` : 'text-dark'"
  />
</template>

<script setup>
import {defineProps} from 'vue';
const props = defineProps({
  color: {
    type: String,
    default: null
  }
});
</script>

<style scoped>

</style>
