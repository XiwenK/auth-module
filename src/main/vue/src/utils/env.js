import pkg from '../../package.json';

export function getStorageShortName() {
  return `${process.env.GLOBAL_APP_NAME}${`__${pkg.version}`}__`.toUpperCase();
}

export function isDevMode() {
  return process.env.NODE_ENV === 'development';
}
