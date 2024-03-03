import {loadData, postData, putData, deleteData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';

const loadUsers = (filter, page, fetchCount, sortBy, descending) => {
  return loadData(endpoints.users, {
    params: {
      filter: filter,
      pageNumber: page,
      pageSize: fetchCount,
      sortField: 'username',
      sortDirection: descending ? "DESC" : "ASC"
    }
  })
};

const loadUsersCount = (filter) => {
  return loadData(endpoints.usersCount, {
    params: {
      filter: filter
    }
  })
}

const addUser = (user) => {
  return postData(endpoints.users, user)
}

const modifyUser = (user) => {
  return putData(endpoints.users, user)
}

const deleteUser = (id) => {
  return deleteData(endpoints.users + '/' + id)
}

export default {loadUsers, add: addUser, modifyUser, deleteUser, loadUsersCount};
