<template>
  <div class="column items-center">
    <dialog-item :label="$t('constantCheck.operation')"
                 dense
                 type="multiselectWithAdd"
                 v-model="model['formula']"
                  :options="['var1', 'var2', '(', ')', '-', '+', '*', '/', '>', '<', '>=', '<=', '==', '!=', '&&', '||']"
                 class="full-width"
                 left-label/>
    <q-list padding bordered class="q-mt-md rounded-borders full-width">
      <q-expansion-item v-for="operand in operands"
                        :key="operand.id"
                        dense
                        dense-toggle
                        expand-separator
                        icon="perm_identity"
                        :label="operand.variable + ' ' + operand.attribute + ' ' + operand.index + ' ' + operand.historyBy">
        <div class="flex row full-width">
          <dialog-item
            :label="$t('constantCheck.variable')"
            type="string"
            class="col-4"
            left-label/>
          <attribute-operand id="attribute" class="col-8"/>
        </div>
      </q-expansion-item>
    </q-list>
    <q-btn icon="add"
           unelevated
           flat
           class="self-end q-mt-md"/>
  </div>
</template>

<script setup>

import {ref} from 'vue';
import AttributeOperand from "pages/Students/components/AttributeOperand.vue";
import DialogItem from "components/DialogItem.vue";
import ButtonIcon from "components/buttons/ButtonIcon.vue";

const model = ref([{name: "DIV", key: 1}, {name: "Time between 10:00 and 12:00", key: 2}, {name: "Country = France", key: 3}]);
const expanded = ref(null);

const operands = ref([
  {id: 1, variable: "variable1", extpanded: true, attribute: 'TERMINAL_ID', index: '2', historyBy: 'HPAN'},
  {id: 2, variable: "variable2", extpanded: false, attribute: 'IP', index: '2', historyBy: 'HPAN'},
  {id: 3, variable: "variable3", extpanded: false, attribute: 'MCC', index: '2', historyBy: 'HPAN'},
]);
</script>

<style scoped>

</style>
