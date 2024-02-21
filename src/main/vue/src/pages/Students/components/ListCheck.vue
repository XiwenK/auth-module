<template>
  <div class="flex items-start row">
    <attribute-operand id="attribute" class="col-5"/>
    <div id="operation" class="col-3">
      <dialog-item :label="$t('constantCheck.operation')"
                   dense
                   type="select"
                    :options="['IN_LIST', 'NOT_IN_LIST']"
                   left-label/>
      <dialog-item :label="$t('constantCheck.likeOption')"
                   dense
                   type="select"
                   :options="['STARTS_WITH', 'ENDS_WITH', 'CONTAINS', 'FUZZY_MATCH']"
                   left-label/>
      <case-sensetive/>
    </div>
    <div id="constant" class="col-4">
      <dialog-item :label="$t('constantCheck.list')"
                   type="select"
                    :options="['OFAC Addresses', 'Suspisious cards', 'Fraud cards']"
                   left-label/>
    </div>

  </div>
</template>

<script setup>

import {ref} from 'vue';
import DialogItem from "components/DialogItem.vue";
import AttributeOperand from "pages/Students/components/AttributeOperand.vue";
import CaseSensetive from "pages/Students/components/CaseSensetive.vue";

const model = ref({});
</script>

<style scoped>

</style>
