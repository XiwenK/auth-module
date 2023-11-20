<template>
  <div class="q-pa-md">
    <div class="column text-white q-pa-sm rounded-borders"
        :class="valuePercent > props.widget.threshold ? 'bg-positive': 'bg-negative'">
      <div class="q-pl-sm">
        {{props.widget.title}}
      </div>
      <div class="row no-wrap items-center">
        <div class="text-h6 text-no-wrap q-pl-sm">
          {{`${value} ${props.widget.unit ? props.widget.unit : ''}`}}
        </div>
        <div class="text-h6">
          {{`(${valuePercent}%)`}}
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {computed, defineProps} from "vue";

const props = defineProps(['widget']);

const value = computed(() => props.widget.value + '/' + props.widget.total);
const valuePercent = computed(() => (props.widget.value/props.widget.total * 100).toFixed(2));

</script>
