import {ref} from 'vue';

export default function () {
  const isDialogVisible = ref(false);
  function onHide() {
    isDialogVisible.value = false;
  }

  function onSubmit() {
    isDialogVisible.value = false;
  }

  function showDialog() {
    isDialogVisible.value = true;
  }
  return {
    isDialogVisible,
    onHide,
    onSubmit,
    showDialog
  }
}
