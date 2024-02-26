import {loadData, postData, putData, deleteData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';

const generateRandomGroup = () => ({
  id: Math.floor(Math.random() * 10000),
  name: `Group ${Math.floor(Math.random() * 100)}`
})

const loadGroups = (string, page, fetchCount, sortBy, descending) => {
  return loadData(endpoints.groups, {
    params: {
      string: string,
      pageNumber: page,
      pageSize: fetchCount,
      sortField: sortBy || 'name',
      sortDirection: descending ? "DESC" : "ASC"
    }
  })
};

const loadGroupsCount = (string) => {
  return loadData(endpoints.groupsCount, {
    params: {
      string: string
    }
  })
}

const addGroup = (group) => {
  return postData(endpoints.groups, group)
}

const modifyGroup = (group) => {
  return putData(endpoints.groups, group)
}

const deleteGroup = (id) => {
  return deleteData(endpoints.groups + '/' + id)
}

export default {loadGroups, addGroup, modifyGroup, deleteGroup, loadGroupsCount};
