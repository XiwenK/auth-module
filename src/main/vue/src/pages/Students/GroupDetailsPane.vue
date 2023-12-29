<template>
  <page-layout class="page-desktop-right">
    <page-header>
      <template #title>
        {{ $t('groupsList.groupDetailsTitle') }}
      </template>
      <template #buttons-left>
        <page-header-btn-back
          :label="$t('studentsList.backButton')"
        />
      </template>
    </page-header>
    <page-body>
      <div class="flex row full-height full-width absolute">
        <div class="col-4 q-pa-md">
          <div class="text-h6">
            Suspicious students
          </div>
          <div class="q-pa-md">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium, alias asperiores atque autem
          </div>
          <div class="q-pa-md">
            Risk value: 0.5
          </div>
          <div class="q-pa-md">
            Active: yes
          </div>
          <div>
            <div class="text-h6">
              Statistics
            </div>
          </div>
        </div>
        <div class="col-8">
          <q-scroll-area  id="formula" class="q-pa-md items-center full-height full-width">
            <rule-condition
              :value="ruleConditions"
              @update="updateValue($event)"/>
          </q-scroll-area>
        </div>
      </div>
    </page-body>
  </page-layout>
</template>

<script setup>
import {useI18n} from "vue-i18n";
import StudentsList from "pages/Students/StudentsList.vue";
import LinkPanel from "components/linkPanel/LinkPanel.vue";
import WidgetPanel from "components/widgets/WidgetPanel.vue";
import {useQuasar} from "quasar";
import {useGroupStore} from "stores/group";

import {computed, defineComponent, defineProps, ref} from 'vue';
import RuleCondition from "pages/Students/components/RuleCondition.vue";

defineComponent({StudentsList, LinkPanel, WidgetPanel});

const $q = useQuasar();
const {t} = useI18n();
const props = defineProps(['group']);
const isMobile = computed(() => $q.screen.lt.sm);
const groupStore = useGroupStore();

const desktopClasses = computed(() => {
  return isMobile.value ? [] : ['q-pa-sm'];
})

const updateValue = (event) => {
  ruleConditions.value = event;
}

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

const widgets = computed(() => {
  const studentsPaid = groupStore.getSelectedStudentList?.reduce((acc, curr) => {
    acc = acc + (curr.paid ? curr.amount : 0);
    return acc;
  }, 0) || 0;
  const placePrice = groupStore.getSelectedGroup?.placePrice || 0;
  const placesCount = groupStore.getSelectedGroup?.placesCount || 0;
  const studentsCount = groupStore.getSelectedStudentList?.length || 0;

  if (!placesCount || !placePrice) {
    return []
  }

  return [
    {
      type: 'percent',
      title: t('groupsList.widget.paid'),
      value: studentsPaid,
      total: placesCount * placePrice,
      unit: 'euro',
      threshold: 75
    },
    {
      type: 'percent',
      title: t('groupsList.widget.booked'),
      value: studentsCount,
      total: placesCount,
      threshold: 50
    }
  ]
})
</script>
<style scoped lang="scss">

</style>
