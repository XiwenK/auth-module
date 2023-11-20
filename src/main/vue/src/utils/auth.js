import { Persistent } from 'src/utils/cache/persistent';
import { CacheTypeEnum, TOKEN_KEY } from 'src/enums/cacheEnums';
import projectSetting from 'src/settings/projectSettings';

const { permissionCacheType } = projectSetting;
const isLocal = permissionCacheType === CacheTypeEnum.LOCAL;

export function getToken() {
  return getAuthCache(TOKEN_KEY);
}

export function getAuthCache(key) {
  const fn = isLocal ? Persistent.getLocal : Persistent.getSession;
  return fn(key);
}

export function setAuthCache(key, value) {
  const fn = isLocal ? Persistent.setLocal : Persistent.setSession;
  return fn(key, value, true);
}

export function clearAuthCache(immediate = true) {
  const fn = isLocal ? Persistent.clearLocal : Persistent.clearSession;
  return fn(immediate);
}
