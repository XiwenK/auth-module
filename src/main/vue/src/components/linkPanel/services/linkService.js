import {postData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';
const getUrl = (groupId) => {
  return postData(endpoints.shortUrlsGetGroupUrl, {groupId: groupId});
};
export default {getUrl};
