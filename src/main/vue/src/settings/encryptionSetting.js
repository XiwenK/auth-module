import { isDevMode } from 'src/utils/env';

// System default cache time, in seconds
export const DEFAULT_CACHE_TIME = 60 * 60 * 24 * 7;

// aes encryption key
export const cacheCipher = {
  key: '_11111011001111@',
  iv: '@11100110001111_',
};

// Whether the system cache is encrypted using aes
export const enableStorageEncryption = !isDevMode();
