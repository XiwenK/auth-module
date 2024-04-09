<template>
  <q-item
    clickable
    :class="{'bg-primary': isSelected}"
  >
    <q-item-section
      v-if="icon"
      avatar
    >
      <q-icon :name="icon"/>
    </q-item-section>

    <q-item-section>
      <q-item-label>{{ title }}</q-item-label>
    </q-item-section>
  </q-item>
</template>

<script setup>
import {defineProps, computed} from 'vue';
import {useRoute} from "vue-router";

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  caption: {
    type: String,
    default: ''
  },
  link: {
    type: String,
    default: '#'
  },
  icon: {
    type: String,
    default: ''
  },
  miniState: {
    type: Boolean,
    default: false
  },
  selected: {
    type: String,
    default: 'Groups'
  }
});

const route = useRoute();
const isSelected = computed(() => {
  return props.link === route.path;
});

</script>
<style scoped lang="scss">
</style>
