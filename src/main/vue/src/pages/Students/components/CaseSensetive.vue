<template>
  <dialog-item :label="$t('constantCheck.caseSensetive')"
               type="select"
                :options="['true', 'false']"
               left-label/>
</template>

<script setup>
import DialogItem from "components/DialogItem.vue";
</script>

<style scoped>

</style>
