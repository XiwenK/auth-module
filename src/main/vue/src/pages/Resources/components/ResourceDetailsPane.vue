<template>
  <page-layout class="page-desktop-right">
    <page-header>
      <template #title>
        {{$t('resourcesList.detailsTitle')}}
      </template>
      <template #buttons-left>
        <page-header-btn-back
          :label="$t('resourcesList.backButton')"
        />
      </template>
    </page-header>
    <page-body>
      <div class="column no-wrap full-height full-width absolute">
      </div>
    </page-body>
  </page-layout>
</template>
