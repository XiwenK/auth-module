<template>
  <div class="q-pa-lg">
    {{ `User count is ${userCount}` }}
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import userService from "pages/Users/service/userService";

const userCount = ref(0);

onMounted(() => {
  console.log('UserPage mounted');
  userService.getUsersCount().then((response) => {
    userCount.value = response;
  })
})
</script>
