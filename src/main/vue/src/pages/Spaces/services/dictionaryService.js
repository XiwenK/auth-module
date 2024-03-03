import {loadData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';

const loadSchedules = () => {
  return loadData(endpoints.schedules).then(response => {
    if (response) {
      return response.map(item => {
        return {
          label: item.name,
          value: item.id
        }
      })
    }
  });
}

const loadResourceTypes = () => {
  return loadData(endpoints.resourceTypes).then(response => {
    if (response) {
      return response.map(item => {
        return {
          label: item.name,
          value: item.id
        }
      })
    }
  });
}

const loadCalendars = () => {
  return loadData(endpoints.calendars).then(response => {
    if (response) {
      return response.map(item => {
        return {
          label: item.name,
          value: item.id
        }
      })
    }
  });
}

const loadUsers = () => {
  return loadData(endpoints.users).then(response => {
    if (response) {
      return response.map(item => {
        return {
          label: item.username,
          value: item.id
        }
      })
    }
  });
}

export default {loadSchedules, loadResourceTypes, loadCalendars, loadUsers};

