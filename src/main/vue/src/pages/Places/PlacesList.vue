<template>
  <page-layout>
    <page-header>
      <template #title>
        Places
      </template>
    </page-header>
    <page-body>
      <div class="full-height absolute">
      </div>
    </page-body>
    <page-footer>
      Footer
    </page-footer>
  </page-layout>

</template>

<script>
import { defineComponent } from 'vue'
import PageHeader from "components/page/PageHeader.vue";

export default defineComponent({
  name: 'IndexPage',
  components: {PageHeader}
})
</script>
