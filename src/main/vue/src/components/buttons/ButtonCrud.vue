<template>
  <q-btn
    @click="click()"
    :label="props.label"
    :disabled="props.disabled"
    no-caps
    :icon="props.icon"
    dense
    unelevated
    style="padding-left: 10px; padding-right: 10px"
    color="primary"
  />
</template>

<script setup>
import {defineProps, defineEmits} from 'vue';
const emit = defineEmits(['click']);
const props = defineProps(['label', 'disabled', 'icon']);

const click = () => {
  emit('click')
}

</script>

<style scoped>

</style>
