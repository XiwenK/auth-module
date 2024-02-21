import {loadData} from "src/rest/utils";
import endpoints from "src/rest/endpoints";

const generateRandomRule = () => ({
  id: Math.floor(Math.random() * 10000),
  name: `Rule ${Math.floor(Math.random() * 100)}`,
  fp: Math.random(),
});

const generateRandomRules = (count) => {
  const rules = [];
  for (let i = 0; i < count; i++) {
    rules.push(generateRandomRule());
  }
  return rules;
}

const loadRules = (string, page, fetchCount, sortBy, descending) => {
  return Promise.resolve(generateRandomRules(fetchCount));
};

const loadRulesCount = (string) => {
  return Promise.resolve(100);
}

export default {loadRules, loadRulesCount};
