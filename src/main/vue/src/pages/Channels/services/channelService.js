import {loadData, postData, putData, deleteData} from 'src/rest/utils';
import endpoints from 'src/rest/endpoints';

const generateRandomChannel = () => ({
  id: Math.floor(Math.random() * 10000),
  name: `Group ${Math.floor(Math.random() * 100)}`,
  description: 'Some description ' + Math.floor(Math.random() * 100),
  isActive: Math.random() > 0.5,
})

const generateMappings = () => ({
  id: Math.floor(Math.random() * 10000),
  field: `Field ${Math.floor(Math.random() * 100)}`,
  attribute: `Attribute ${Math.floor(Math.random() * 100)}`,
  description: 'Some description ' + Math.floor(Math.random() * 100),
  type: "STRING",
  isActive: Math.random() > 0.5,
});

const channels = [];
for (let i = 0; i < 10; i++) {
  const channel = generateRandomChannel();
  channel.reqMappings = [];
  for (let j = 0; j < 10; j++) {
    channel.reqMappings.push(generateMappings());
  }
  channel.resMappings = [];
    for (let j = 0; j < 2; j++) {
    channel.resMappings.push(generateMappings());
    }
  channels.push(channel);
}

const loadChannels = (string, page, fetchCount, sortBy, descending) => {
  return loadData(endpoints.groups, {
    params: {
      string: string,
      pageNumber: page,
      pageSize: fetchCount,
      sortField: sortBy || 'name',
      sortDirection: descending ? "DESC" : "ASC"
    }
  }).then(() => {
    return channels;
  })
};

const loadChannelsCount = (string) => {
  return Promise.resolve(channels.length);
}

const addChannel = (channel) => {
  channel.id = Math.floor(Math.random() * 10000);
  channel.reqMappings = [];
  channel.resMappings = [];
  channels.push(channel);
  return Promise.resolve();
}

const modifyChannel = (channel) => {
  const index = channels.findIndex((c) => c.id === channel.id);
  channels[index] = channel;
  return Promise.resolve();
}

const deleteChannel = (id) => {
  const index = channels.findIndex((c) => c.id === id);
  channels.splice(index, 1);
  return Promise.resolve();
}

const loadReqMappings = (channelId) => {
  if (channelId === undefined || channelId === null || channelId === '') return Promise.resolve([]);
  const channel = channels.find((c) => c.id === channelId);
  return Promise.resolve(channel.reqMappings);
}

const loadReqMappingsCount = (channelId) => {
  if (channelId === undefined || channelId === null || channelId === '') return Promise.resolve(0);
  const channel = channels.find((c) => c.id === channelId);
  console.log("loadMappingsCount", channelId, channel.reqMappings.length, "mappings")
  return Promise.resolve(channel.reqMappings.length);
}

const loadResMappings = (channelId) => {
    if (channelId === undefined || channelId === null || channelId === '') return Promise.resolve([]);
    const channel = channels.find((c) => c.id === channelId);
    console.log("loadMappings", channelId, channel.resMappings.length, "mappings")
    return Promise.resolve(channel.resMappings);
}

const loadResMappingsCount = (channelId) => {
    if (channelId === undefined || channelId === null || channelId === '') return Promise.resolve(0);
    const channel = channels.find((c) => c.id === channelId);
    console.log("loadMappingsCount", channelId, channel.resMappings.length, "mappings")
    return Promise.resolve(channel.resMappings.length);
}

const addMapping = (channelId, mapping, isResponse) => {
  mapping.id = Math.floor(Math.random() * 10000);
  const channel = channels.find((c) => c.id === channelId);
  if (isResponse) {
    channel.resMappings.push(mapping);
  } else {
    channel.reqMappings.push(mapping);
  }
  return Promise.resolve(mapping);
}

const modifyMapping = (channelId, mapping, isResponse) => {
  const channel = channels.find((c) => c.id === channelId);
  let index = -1;
  if (isResponse) {
    index = channel.resMappings.findIndex((m) => m.id === mapping.id);
    channel.resMappings[index] = mapping;
  } else {
    index = channel.reqMappings.findIndex((m) => m.id === mapping.id);
    channel.reqMappings[index] = mapping;
  }
  return Promise.resolve();
}

const deleteMapping = (channelId, mappingId, isResponse) => {
  const channel = channels.find((c) => c.id === channelId);
  let index = -1;
  if (isResponse) {
    index = channel.resMappings.findIndex((m) => m.id === mapping.id);
    channel.resMappings.splice(index, 1);
  } else {
    index = channel.resMappings.findIndex((m) => m.id === mapping.id);
    channel.reqMappings.splice(index, 1);
  }
  return Promise.resolve();
}

export default {addChannel, modifyChannel, deleteChannel, loadChannels, loadChannelsCount, loadReqMappings, loadReqMappingsCount, addMapping, modifyMapping, deleteMapping, loadResMappings, loadResMappingsCount };
