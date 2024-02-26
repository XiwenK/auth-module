<template>
  <page-layout class="page-desktop-right">
    <page-header>
      <template #title>
        {{ $t('groupsList.groupDetailsTitle') }}
      </template>
      <template #buttons-left>
        <page-header-btn-back
          :label="$t('buttons.backButton')"
        />
      </template>
    </page-header>
    <page-body>
      <roles-list class="fit absolute"
                  :group="group"/>
    </page-body>
  </page-layout>
</template>

<script setup>
import {useI18n} from "vue-i18n";
import RolesList from "pages/Groups/components/RolesList.vue";
import {defineComponent, defineProps} from 'vue';

defineComponent({RulesList: RolesList});

const {t} = useI18n();
const props = defineProps(['group']);

</script>
