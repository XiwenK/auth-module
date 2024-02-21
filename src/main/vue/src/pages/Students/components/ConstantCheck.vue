<template>
  <div class="flex items-center row">
    <attribute-operand id="attribute" class="col-5"/>
    <div id="operation" class="col-2">
      <dialog-item :label="$t('constantCheck.operation')"
                   dense
                   type="select"
                    :options="['=', '<>', '>', '<', '>=', '<=', 'STARTS_WITH', 'ENDS_WITH', 'CONTAINS', 'NOT_CONTAINS']"
                   left-label/>
    </div>
    <div id="constant" class="col-5">
      <dialog-item :label="$t('constantCheck.constant')"
                   type="string"
                   left-label/>
    </div>

  </div>
</template>

<script setup>

import {ref} from 'vue';
import DialogItem from "components/DialogItem.vue";
import AttributeOperand from "pages/Students/components/AttributeOperand.vue";

const model = ref({});
</script>

<style scoped>

</style>
