<template>
  <div class="column full-height no-wrap">
    <div class="flex q-py-md q-px-lg justify-between items-center">
      <div class="text-h5 flex-grow-1">
        <div class="cursor-pointer editable-field-content">
          {{ model.label }}
          <q-popup-edit v-model="model.label"
                        auto-save
                        v-slot="scope"
                        :validate="val => val.length > 5">
            <q-input v-model="scope.value"
                     dense
                     autofocus
                     counter
                     :rules="[val => scope.validate(val) || 'More than 5 chars required']"
                     @keyup.enter="scope.set" />
          </q-popup-edit>
        </div>
      </div>
      <div class="flex q-ml-md">
        <q-btn-dropdown color="primary"
                        no-caps
                        label="Changed">
          <q-list>
            <q-item clickable v-close-popup @click="onItemClick">
              <q-item-section>
                <q-item-label>Save</q-item-label>
                <q-item-label caption>Set to Saved(Not Active) state</q-item-label>
              </q-item-section>
            </q-item>

            <q-item clickable v-close-popup @click="onItemClick">
              <q-item-section>
                <q-item-label>Revert</q-item-label>
              </q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
        <button-crud
          icon="close"
          label="Close"
          color="primary"
          :compact="false"
          class="q-ml-md"
          @click="emit('close')"
        />
      </div>
    </div>
    <q-splitter horizontal/>
    <div class="flex row fit q-pa-md">
      <div class="col-5">
        <div class="q-pa-sm">
          <div class="editable-field-label">
            Description
          </div>
          <div class="cursor-pointer editable-field-content">
            {{ model.desc }}
            <q-popup-edit v-model="model.desc"
                          auto-save
                          v-slot="scope"
                          :validate="val => val.length > 5">
              <q-input v-model="scope.value"
                       type="textarea"
                       dense
                       autofocus
                       counter
                       :rules="[val => scope.validate(val) || 'More than 5 chars required']"
                       @keyup.enter="scope.set" />
            </q-popup-edit>
          </div>
        </div>
        <div class="q-pa-sm">
          <div class="editable-field-label">
            Risk
          </div>
          <div class="cursor-pointer editable-field-content">
            {{ `${model.risk}%` }}
            <q-popup-edit v-model="model.risk"
                          auto-save
                          v-slot="scope"
                          :validate="val => val > 0 && val <= 100">
              <q-input v-model="scope.value"
                       dense
                       autofocus
                       counter
                       :rules="[val => scope.validate(val) || 'More 0 and less or equal 100 required ']"
                       @keyup.enter="scope.set" />
            </q-popup-edit>
          </div>
        </div>
        <div class="q-pa-sm">
          <div class="editable-field-label">
            Tags
          </div>
          <div class="flex">
            <q-chip icon="shop" label="E-com"/>
            <q-chip icon="flag" label="Risk countries"/>
          </div>
        </div>
        <div class="q-pa-sm">
          <div class="editable-field-label">
            Statistics
          </div>
          <div class="flex">

          </div>
        </div>
      </div>
      <div class="col-7">
        <div class="editable-field-label">
          Conditions
        </div>
        <q-scroll-area id="formula" class="items-center full-height full-width">
          <rule-condition
            :value="ruleConditions"
            @update="updateValue($event)"/>
        </q-scroll-area>
      </div>
    </div>
  </div>
</template>

<script setup>
import RuleCondition from "pages/Students/components/RuleCondition.vue";
import {ref, defineEmits} from "vue";
import ButtonCrud from "components/buttons/ButtonCrud.vue";

const emit = defineEmits(['close']);

const model = ref({
  label: 'Suspicious activity',
  desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec purus feugiat, molestie ipsum et, consequat nib',
  risk: 80,
  tags: [
    'countries',
    'e-commerce',
    'merchants'
  ]
});

const ruleConditions = ref(
  [
    {name: "AND", key: 1},
    {name: "Time between 10:00 and 12:00", key: 2},
    {name: "Country = France", key: 3},
    [
      {name: "OR", key: 4},
      {name: "Time between 10:00 and 12:00", key: 5},
      {name: "Country = France", key: 6},
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        {name: "Country = France", key: 6},
      ],
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        {name: "Country = France", key: 6},
      ],
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        {name: "Country = France", key: 6},
      ],
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        [
          {name: "OR", key: 4},
          {name: "Time between 10:00 and 12:00", key: 5},
          {name: "Country = France", key: 6},
        ],
      ]
    ],
    [
      {name: "OR", key: 4},
      {name: "Time between 10:00 and 12:00", key: 5},
      {name: "Country = France", key: 6},
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        {name: "Country = France", key: 6},
      ],
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        {name: "Country = France", key: 6},
      ],
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        {name: "Country = France", key: 6},
      ],
      [
        {name: "OR", key: 4},
        {name: "Time between 10:00 and 12:00", key: 5},
        [
          {name: "OR", key: 4},
          {name: "Time between 10:00 and 12:00", key: 5},
          {name: "Country = France", key: 6},
        ],
      ]
    ]
  ]);

const updateValue = (event) => {
  ruleConditions.value = event;
}

</script>

<style scoped>

</style>
