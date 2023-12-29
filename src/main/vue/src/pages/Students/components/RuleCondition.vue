<template>
  <div class="q-pt-xs full-width">
    <template v-if="operation.expanded">
      <div v-if="['AND', 'OR', 'NOT'].includes(operation.name)"
           class="flex items-center q-pl-sm full-width"
           @dragover="dragEnter($event)"
           @dragleave="dropZoneActive = false"
           @drop="drop($event)">
        <div class="flex items-center justify-between full-width">
          <div>
            <q-btn
              unelevated
              dense
              icon="close"
              @click="removeCondition(0, true)"
              class="q-pr-sm text-grey"
              style="font-size: 8px"/>
            <q-btn-dropdown
              class="bg-primary text-white chips"
              style="width: unset; min-height: 35px"
              :label="operation.name"
              unelevated
              auto-close
              outline
              flat
              toggle-aria-label="Toggle"
              dropdown-icon="none"
              no-icon-animation
            >
              <q-list dense>
                <q-item v-if="operation.name !== 'AND'"
                        clickable
                        v-close-popup
                        @click="operation.name='AND'">
                  <q-item-section>
                    <q-item-label>AND</q-item-label>
                  </q-item-section>
                </q-item>
                <q-item v-if="operation.name !== 'NOT' && localVal.length <= 2"
                        clickable
                        v-close-popup
                        @click="operation.name='NOT'">
                  <q-item-section>
                    <q-item-label>NOT</q-item-label>
                  </q-item-section>
                </q-item>
                <q-item v-if="operation.name !== 'OR'"
                        clickable
                        v-close-popup
                        @click="operation.name='OR'">
                  <q-item-section>
                    <q-item-label>OR</q-item-label>
                  </q-item-section>
                </q-item>
              </q-list>
            </q-btn-dropdown>
          </div>
          <div class="flex items-center">
            <div class="q-ml-md">
              <q-btn-dropdown
                class="text-grey"
                unelevated
                no-icon-animation
                dense
                dropdown-icon="add"
                style="font-size: 8px">
                <q-list dense>
                  <q-item clickable v-close-popup @click="addCondition(0)">
                    <q-item-section>
                      <q-item-label>Add condition</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="addGroup(0)">
                    <q-item-section>
                      <q-item-label>Add group</q-item-label>
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-btn-dropdown>
            </div>
            <q-btn
              class="q-ml-md text-grey"
              style="font-size: 8px"
              dense
              :icon="operation.expanded ? 'expand_less' : 'expand_more'"
              unelevated
              @click="operation.expanded = !operation.expanded"
            />
          </div>
        </div>
      </div>
      <div :class="{'drop-zone': dropZoneActive, 'drop-zone-available': dropZoneAvailable}"
            class="q-pl-sm">
      </div>
      <div class="full-width">
        <div v-for="(condition, index) in localVal.slice(1)"
             :key="condition.key"
             class="group q-pl-md full-width">
          <div class="flex no-wrap full-width">
            <div v-if="!isArray(condition)"
                 class="flex items-center q-pl-sm q-pt-xs"
                 draggable="true"
                 @dragstart="dragStart($event, condition, index)">
              <q-btn
                :disable="localVal.length <= 2"
                unelevated
                dense
                icon="close"
                @click="removeCondition(index)"
                class="q-pr-sm text-grey"
                style="font-size: 8px"/>
              <div class="q-px-sm bg-secondary text-white chips">
                {{ condition.name }}
              </div>
            </div>
            <div v-else class="full-width">
              <rule-condition :value=condition
                              @update="updateValue($event, index)"
                              @remove="removeCondition(index, false)"/>
            </div>
          </div>
        </div>
      </div>
    </template>
    <template v-else>
      <div class="flex q-pl-sm no-wrap full-width justify-between"
          draggable="true"
           @dragstart="dragStart($event, localVal)">
        <div class="flex items-center">
          <q-btn
            unelevated
            dense
            icon="close"
            @click="removeCondition(0, true)"
            class="q-pr-sm text-grey"
            style="font-size: 8px; min-height: 35px"/>
          <div class="q-pa-sm bg-secondary text-white chips">
            {{ calcDesc }}
          </div>
        </div>
        <div class="flex items-center">
          <q-btn
            unelevated
            dense
            icon="edit"
            @click="prompt=true"
            class="text-grey q-ml-sm"
            style="font-size: 8px"/>
          <q-btn
            class="q-ml-md text-grey"
            style="font-size: 8px"
            dense
            :icon="operation.expanded ? 'expand_less' : 'expand_more'"
            unelevated
            @click="operation.expanded = !operation.expanded"
          />
        </div>
      </div>
    </template>
    <q-dialog v-model="prompt" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">Group description</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input type="textarea" rows="2" dense v-model="description" autofocus
                   @keyup.enter="prompt = false;updateDescription()"/>
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Cancel" v-close-popup/>
          <q-btn flat label="Save" v-close-popup @click="updateDescription()"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import {defineProps, defineEmits, ref, watch, computed} from 'vue';

const props = defineProps(['value']);
const emit = defineEmits(['update', 'remove', 'start-dragging', 'stop-dragging' ]);

const localVal = ref(props.value);
watch(() => props.value, (newVal) => {
  localVal.value = newVal;
}, {deep: true});

const operation = computed(() => {
  return localVal.value[0];
})

const prompt = ref(false);
const description = ref(operation.value.desc);

const updateValue = (event, index) => {
  localVal.value[index + 1] = event;
  emitUpdate()
}

const dropZoneActive = ref(false);
const dropZoneAvailable = ref(false);

const dragEnter = (event) => {
  event.preventDefault();
  dropZoneActive.value = true;
}

const drop = (event) => {
  const data = JSON.parse(event.dataTransfer.getData("text/plain"));
  if (data.condition) {
    localVal.value.splice(1, 0, data.condition);
    emitUpdate();
  }
  dropZoneActive.value = false;
}

const dragStart = (event, condition, index) => {
  // event.preventDefault();
  event.dataTransfer.clearData();
  event.dataTransfer.setData("text/plain", JSON.stringify({condition: condition, index: index}));
}

const isArray = (value) => {
  return value && typeof value === 'object' && value.constructor === Array;
}

function emitUpdate() {
  emit('update', localVal.value);
}

const addCondition = (index) => {
  localVal.value.splice(index + 1, 0, {name: "New added value", key: generateKey()});
  emitUpdate();
}

const updateDescription = () => {
  localVal.value[0].desc = description.value;
  emitUpdate();
}

const addGroup = (index) => {
  localVal.value.splice(index + 1, 0, [{name: "AND", key: generateKey()}, {
    name: "New added value",
    key: generateKey()
  }]);
  emitUpdate();
}

const removeCondition = (index, isRemove) => {
  if (isRemove) {
    emit('remove');
    return;
  }
  localVal.value.splice(index + 1, 1);
  emitUpdate();
}


const calcDesc = computed(() => {
  if (operation.value.desc) {
    return operation.value.desc;
  }
  {
    return 'No description'
  }
})

const generateKey = () => {
  return Math.floor(Math.random() * 1000000);
}

</script>

<style scoped>
.chips {
  border-radius: 4px;
  min-height: 0;
  width: 200px;
  /*width: max-content;*/
}

.drop-zone {
  border: 1px dashed #ccc;
  height: 20px;
  margin-left: 38px;
  background-color: #cccccc;
  margin-top: 5px;
  border-radius: 4px;
  width: 200px;
}

</style>
