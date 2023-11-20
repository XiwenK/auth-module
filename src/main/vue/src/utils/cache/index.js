import { getStorageShortName } from 'src/utils/env';
import { createStorage as create } from './storageCache';
import { enableStorageEncryption, DEFAULT_CACHE_TIME } from 'src/settings/encryptionSetting';

const createOptions = (storage, options) => {
  return {
    // No encryption in debug mode
    hasEncrypt: enableStorageEncryption,
    storage,
    prefixKey: getStorageShortName(),
    ...options,
  };
};

export const WebStorage = create(createOptions(sessionStorage));

export const createStorage = (storage = sessionStorage, options= {}) => {
  return create(createOptions(storage, options));
};

export const createSessionStorage = (options = {}) => {
  return createStorage(sessionStorage, { ...options, timeout: DEFAULT_CACHE_TIME });
};

export const createLocalStorage = (options= {}) => {
  return createStorage(localStorage, { ...options, timeout: DEFAULT_CACHE_TIME });
};

export default WebStorage;
