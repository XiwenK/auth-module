import { defineStore } from 'pinia';
import channelService from 'pages/Channels/services/channelService.js';

export const useChannelStore = defineStore('channel', {
  state: () => ({
    selectedChannel: undefined,
    selectedMapping: undefined,
    reqMappings: [],
    resMappings: []
  }),
  actions: {
    addReqMapping(mapping){
      this.reqMappings.push(mapping);
    },
    loadReqMappings (channelId) {
      return channelService.loadReqMappings(channelId).then(res => {
        this.reqMappings?.splice(0, this.reqMappings.length);
        res?.forEach(v => this.reqMappings.push(v))
        console.log('reqMappings is updated', res)
        return res;
      });
    },
    loadResMappings (channelId) {
      return channelService.loadResMappings(channelId).then(res => {
        this.resMappings = res;
        return res;
      });
    },
    setReqMappings (mappings) {
      this.reqMappings = mappings;
    },
    addMapping (channelId, mapping, isResponse) {
      return channelService.addMapping(channelId, mapping, isResponse);
    },
    modifyMapping (channelId, mapping, isResponse) {
      return channelService.modifyMapping(channelId, mapping, isResponse);
    },
    deleteMapping (channelId, mapping, isResponse) {
      return channelService.deleteMapping(channelId, mapping.id, isResponse);
    },
    selectChannel (channel) {
      this.selectedChannel = channel;
    },
    //todo: make update lists via store
    // addMapping (mapping) {
    //   this.selectedChannel.reqMappings.push(mapping);
    // },
    selectMapping (mapping) {
      this.selectedMapping = mapping;
    }
  },
  getters: {
    getSelectedChannel: (state) => {
      return state.selectedChannel;
    },
    getSelectedMapping: (state) => {
      return state.selectedMapping;
    },
    getReqStringMappings: (state) => {
      return state.reqMappings.filter((m) => m.type === 'STRING');
    },
    getResStringMappings: (state) => {
      return state.resMappings.filter((m) => m.type === 'STRING');
    },
    getReqMappings: (state) => {
      return state.reqMappings;
    },
    getResMappings: (state) => {
      return state.resMappings;
    },
    attributes: (state) => {
      return state.reqMappings.map(item => ({value: item.id, label: item.attribute}));
    }
  }
})
