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
      <rules-list v-if="!ruleModifyMode"
                  class="fit absolute"
                  @modify-rule="goToRuleModify"
                  :group="group"/>
      <rule-info v-else
                 @close="ruleModifyMode=false"/>
    </page-body>
  </page-layout>
</template>

<script setup>
import {useI18n} from "vue-i18n";
import StudentsList from "pages/Students/StudentsList.vue";
import LinkPanel from "components/linkPanel/LinkPanel.vue";
import WidgetPanel from "components/widgets/WidgetPanel.vue";
import RulesList from "pages/Groups/components/RulesList.vue";
import {useQuasar} from "quasar";
import {useGroupStore} from "stores/group";

import {computed, defineComponent, defineProps, ref} from 'vue';
import RuleInfo from "pages/Groups/components/RuleInfo.vue";

defineComponent({RulesList, StudentsList, LinkPanel, WidgetPanel});

const $q = useQuasar();
const {t} = useI18n();
const props = defineProps(['group']);
const isMobile = computed(() => $q.screen.lt.sm);
const groupStore = useGroupStore();

const ruleModifyMode = ref(false);

const goToRuleModify = () => {
  ruleModifyMode.value = true;
}

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
