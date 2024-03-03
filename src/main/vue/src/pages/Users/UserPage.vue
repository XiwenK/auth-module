<template>
    <div class="relative-position">
      <user-list class="page-desktop-left single"/>
    </div>
</template>

<script setup>

import UserList from "pages/Users/UserList.vue";
import {defineComponent} from 'vue';

defineComponent({UserList});
</script>
