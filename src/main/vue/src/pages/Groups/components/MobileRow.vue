<template>
  <q-tr ref="rowRef"
        :props="data"
        class="mobile-row relative-position"
        v-touch-swipe.mouse.left="$q.screen.lt.md && swipeLeft"
        v-touch-swipe.mouse.right="$q.screen.lt.md && swipeRight"
        @click="onClick"
  >
    <q-td v-for="column in data.cols"
          :key="column.name"
          :props="data"
          style="white-space: break-spaces; z-index: 1"
          :style="`transform: translateX(${rowOffset}px); transition: transform 0.3s, background-color 0.3s; background-color: white`">
      <slot :name="column.name" :row="row" :column="column">
        {{ row[column.name] }}
      </slot>
    </q-td>
    <button-icon-list
      v-if="$q.screen.lt.md"
      class="absolute button-icon-list"
      :style="`height: ${rowHeight}; right: 0`">
      <button-icon icon="edit" label='Edit' @click.stop.prevent="onEdit"/>
      <button-icon icon="delete" label="Delete" color="negative" @click.stop.prevent="onDelete"/>
    </button-icon-list>
  </q-tr>
</template>
<script setup>

import ButtonIconList from "components/buttons/ButtonIconList.vue";
import ButtonIcon from "components/buttons/ButtonIcon.vue";
import {useRouter} from "vue-router";
import {useQuasar} from "quasar";

import {ref, defineProps, computed, defineEmits, watch, onMounted} from "vue";

const props = defineProps({
  data: {
    type: Object,
    default: () => {
    }
  },
  selected: {
    type: String,
    default: null
  }
});
const emit = defineEmits(['delete', 'edit', 'select', 'go-to-student', 'click']);

const $q = useQuasar();
const router = useRouter();

watch(() => props.selected, (newVal) => {
  if (newVal !== props.data?.row.id) {
    rowOffset.value = 0;
  }
});

watch(() => props.data, (newVal) => {
  updateRowHeight();
});

const isMobile = computed(() => $q.screen.lt.md);

//Fields
const row = computed(() => props.data?.row || {});
const scheduleLabel = computed(() => row.value.schedule?.label || '');
const dateTime = computed(() => row.value.dateTime?.split(' ')?.[0]);
const bookedToTotalPercent = (row) => row.placesCount === 0 ? 0 : row.booked / row.placesCount * 100;
const bookedColor = (row) => bookedToTotalPercent(row) > 80 ? 'green' : bookedToTotalPercent(row) > 40 ? 'blue' : 'red';


//Swipe logic
const rowOffset = ref(0);
const swipeLeft = (e) => {
  if (e.distance?.x >= 5) {
    select();
    rowOffset.value = -152;
  }
};
const swipeRight = (e) => {
  if (isMobile.value && e.distance?.x >= 5) {
    rowOffset.value = 0;
  }
};

//Emit logic
const onEdit = () => {
  swipeRight({distance: {x: 5}})
  emit('edit');
};
const onDelete = () => {
  swipeRight({distance: {x: 5}})
  emit('delete');
};
const select = () => {
  emit('select');
};

const onClick = (e) => {
  if (rowOffset.value !== 0) return;
  emit('click', props.data.row);
};

const rowRef = ref(null);
const rowHeight = ref('0px');
const updateRowHeight = () => {
  rowHeight.value = `${rowRef.value?.$el.clientHeight}px`;
};

onMounted(() => {
  updateRowHeight();
});

</script>
<style lang="scss">
.button-icon-list {
  border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

tr:last-child .button-icon-list {
  border-bottom: none;
}

tbody {

}
</style>
