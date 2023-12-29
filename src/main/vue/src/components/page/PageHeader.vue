<template>
  <div
    class="page-header q-header q-layout__section--marginal
       flex-grow-0"
    :class="{'q-header--bordered bg-white text-black': $q.screen.lt.md,
              'bg-primary text-white desktop': !$q.screen.lt.md}"
  >
    <div class="q-toolbar row no-wrap items-center">
      <slot name="buttons-left"/>
      <div class="q-toolbar__title ellipsis text-center text-body1 q-pl-none page-header__title">
        <slot name="title"/>
      </div>
    </div>
  </div>
</template>

<script setup>


</script>

<style scoped lang="scss">
</style>
