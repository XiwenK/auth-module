import {loadData} from "src/rest/utils";
import endpoints from "src/rest/endpoints";

const generateRandomRole = () => ({
  id: Math.floor(Math.random() * 10000),
  name: `Role ${Math.floor(Math.random() * 100)}`,
  fp: Math.random(),
});

const generateRandomRoles = (count) => {
  const roles = [];
  for (let i = 0; i < count; i++) {
    roles.push(generateRandomRole());
  }
  return roles;
}

const loadRoles = (string, page, fetchCount, sortBy, descending) => {
  return Promise.resolve(generateRandomRoles(fetchCount));
};

const loadRolesCount = (string) => {
  return Promise.resolve(100);
}

export default {loadRoles, loadRolesCount};
