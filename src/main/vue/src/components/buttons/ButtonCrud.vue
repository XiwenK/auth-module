<template>
  <q-btn
    v-if="compact"
    @click="click()"
    :disabled="props.disabled"
    no-caps
    :icon="props.icon"
    dense
    unelevated
    style="padding-left: 10px; padding-right: 10px;"
    color="primary"
  />
  <q-btn
    v-else
    @click="click()"
    :label="props.label"
    :disabled="props.disabled"
    no-caps
    :icon="props.icon"
    dense
    unelevated
    style="padding-left: 10px; padding-right: 10px; min-width: 100px"
    color="primary"
  />
</template>

<script setup>
import {defineProps, defineEmits} from 'vue';
const emit = defineEmits(['click']);
// const props = defineProps(['label', 'disabled', 'icon', 'compact']);
const props = defineProps({
  label: {
    type: String,
    default: ''
  },
  disabled: {
    type: Boolean,
    default: false
  },
  icon: {
    type: String,
    default: ''
  },
  compact: {
    type: Boolean,
    default: true
  }
})

const click = () => {
  emit('click')
}

</script>

<style scoped>

</style>
