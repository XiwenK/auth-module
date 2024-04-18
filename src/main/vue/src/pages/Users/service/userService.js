import {loadData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';
const getUsersCount = () => {
  return loadData(endpoints.usersCount);
}

export default { getUsersCount };
