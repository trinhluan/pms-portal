export const ALLOW_LOGIN = [
  ['All', 'All'],
  ['Yes', 'Yes'],
  ['No', 'No']
].map((o) => ({ value: o[0], label: o[1] }));

export const LANGUAGE = [
  ['All', 'All'],
  ['en', 'English'],
  ['zh_TW', '繁體中文'],
  ['zh_CN', '简体中文']
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

export const LANGUAGE_EDIT = [
  ['English', 'English'],
  ['繁體中文', '繁體中文'],
  ['简体中文', '简体中文']
].map((o) => ({ value: o[0], label: o[1] }));

export const HOME_PAGE = [
  ['MyProfile', 'My Profile'],
  ['EnquiryMaintenance', 'Enquiry Maintenance'],
  ['WorkOrderMaintenance', 'Work Order Maintenance'],
  ['PatrolAssignmentMaintenance', 'Patrol Assignment Maintenance'],
  ['Dashboard', 'Dashboard']
].map((o) => ({ value: o[0], label: o[1] }));