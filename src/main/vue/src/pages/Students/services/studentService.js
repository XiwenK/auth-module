import {deleteData, loadData, postData, putData} from "src/rest/utils";
import endpoints from "src/rest/endpoints";

const loadStudents = (groupId) => {
  if (groupId === undefined || groupId === null || groupId === '') return Promise.resolve([]);
  return loadData(endpoints.students, {
    params: {
      groupId: groupId
    }
  })
};

const loadStudentsCount = (groupId) => {
  if (groupId === undefined || groupId === null || groupId === '') return Promise.resolve(0);
  return loadData(endpoints.studentsCount, {
    params: {
      groupId: groupId
    }
  })
}

const addStudent = (student) => {
  return postData(endpoints.students, student)
}

const addStudentByUrl = (student) => {
  return postData(endpoints.studentsByUrl, student)
}

const modifyStudent = (student) => {
  return putData(endpoints.students, student)
}

const deleteStudent= (id, groupId) => {
  return deleteData(endpoints.students, {
    params: {
      id: id,
      groupId: groupId
    }
  })
}

export default {loadStudents, loadStudentsCount, addStudent, modifyStudent, deleteStudent, addStudentByUrl};
