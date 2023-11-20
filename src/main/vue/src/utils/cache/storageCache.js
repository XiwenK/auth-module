import {cacheCipher} from 'src/settings/encryptionSetting';
import {AesEncryption} from 'src/utils/cipher';
import {isNullOrUnDef} from 'src/utils/is';

export const createStorage = ({
                                prefixKey = '',
                                storage = sessionStorage,
                                key = cacheCipher.key,
                                iv = cacheCipher.iv,
                                timeout = null,
                                hasEncrypt = true,
                              } = {}) => {
  if (hasEncrypt && [key.length, iv.length].some((item) => item !== 16)) {
    throw new Error('When hasEncrypt is true, the key or iv must be 16 bits!');
  }

  const encryption = new AesEncryption({key, iv});
  const WebStorage = class WebStorage {
    constructor() {
      this.storage = storage;
      this.prefixKey = prefixKey;
      this.encryption = encryption;
      this.hasEncrypt = hasEncrypt;
    }

    getKey(key) {
      return `${this.prefixKey}${key}`.toUpperCase();
    }

    set(key, value, expire = timeout) {
      const stringData = JSON.stringify({
        value,
        time: Date.now(),
        expire: !isNullOrUnDef(expire) ? new Date().getTime() + expire * 1000 : null,
      });
      const stringifyValue = this.hasEncrypt
        ? this.encryption.encryptByAES(stringData)
        : stringData;
      this.storage.setItem(this.getKey(key), stringifyValue);
    }

    get(key, def = null) {
      const val = this.storage.getItem(this.getKey(key));
      if (!val) return def;

      try {
        const decVal = this.hasEncrypt ? this.encryption.decryptByAES(val) : val;
        const data = JSON.parse(decVal);
        const {value, expire} = data;
        if (isNullOrUnDef(expire) || expire >= new Date().getTime()) {
          return value;
        }
        this.remove(key);
      } catch (e) {
        return def;
      }
    }

    remove(key) {
      this.storage.removeItem(this.getKey(key));
    }

    /**
     * Delete all caches of this instance
     */
    clear(){
      this.storage.clear();
    }
  };
  return new WebStorage();
};
