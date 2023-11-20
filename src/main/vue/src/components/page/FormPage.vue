<template>
  <div class="fullscreen login-form column items-center justify-center">
    <div class="fixed-center column items-center">
      <div class="text-h3 text-white text-weight-bold q-pb-md">
        <slot name="title"/>
      </div>
      <slot name="form"/>
    </div>
  </div>
</template>
