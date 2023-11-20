import { defineStore } from 'pinia';

export const useGroupStore = defineStore('group', {
  state: () => ({
    selectedGroup: undefined,
    selectedStudentList: []
  }),
  actions: {
    selectGroup (group) {
      this.selectedGroup = group;
    },
    selectStudentList (studentList) {
      this.selectedStudentList = studentList;
    }
  },
  getters: {
    getSelectedGroup: (state) => {
      return state.selectedGroup;
    },
    getSelectedStudentList: (state) => {
      return state.selectedStudentList;
    }
  }
})
