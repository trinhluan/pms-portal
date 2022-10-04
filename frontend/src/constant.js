import { t } from "i18next";

export const ALLOW_LOGIN = [
  ['All', 'All'],
  ['Yes', 'Yes'],
  ['No', 'No']
].map((o) => ({ value: o[0], label: o[1] }));

export const LANG_DIC = {
  'en' : 'English',
  'zh_TW' : '繁體中文',
  'zh_CN' : '简体中文'
}

export const LANGUAGE = [
  ['All', 'All'],
  ['en', LANG_DIC['en']],
  ['zh_TW', LANG_DIC['zh_TW']],
  ['zh_CN', LANG_DIC['zh_CN']]
].map((o) => ({ value: o[0], label: o[1] }));

export const STATUS = [
  ["Active", 'Active'], 
  ["InActive", 'InActive']
].map((o) => ({
  value: o[0],
  label: o[1]
}));

export const YES_NO_CONST = [
  ['Yes', 'Yes'],
  ['No', 'No']
].map((o) => ({ value: o[0], label: o[1] }));

export const HOME_PAGE = [
  ['#', '----'],
  ['/myProfile', 'My Profile'],
  ['#', 'Enquiry Maintenance'],
  ['#', 'Work Order Maintenance'],
  ['#', 'Patrol Assignment Maintenance'],
  ['#', 'Dashboard']
].map((o) => ({ value: o[0], label: o[1] }));