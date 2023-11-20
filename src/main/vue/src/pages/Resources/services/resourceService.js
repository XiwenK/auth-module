import {loadData, postData, putData, deleteData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';

const loadResources = (string, page, fetchCount, sortBy, descending) => {
  return loadData(endpoints.resources, {
    params: {
      string: string,
      pageNumber: page,
      pageSize: fetchCount,
      sortField: sortBy || 'name',
      sortDirection: descending ? "DESC" : "ASC"
    }
  }).then(res => {
    return res.map(item => {
      item.calendar = {value: item.calendar.id, label: item.calendar.name};
      return item;
    })
  })
};

const loadResourcesCount = (string) => {
  return loadData(endpoints.resourcesCount, {
    params: {
      string: string
    }
  })
}

const addResource = (resource) => {
  const calendar = resource.calendar;
  resource.calendar = {id: calendar.value, name: calendar.label};
  const type = resource.type;
  resource.type = type.value;
  return postData(endpoints.resources, resource);
}

const modifyResource = (resource) => {
  const calendar = resource.calendar;
  resource.calendar = {id: calendar.value, name: calendar.label};
  return putData(endpoints.resources, resource);
}

const deleteResource = (id) => {
  return deleteData(endpoints.resources + '/' + id)
}

export default {loadResources, addResource, modifyResource, deleteResource, loadResourcesCount};
