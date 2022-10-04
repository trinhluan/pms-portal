import axios from '../service/interceptor'
import $ from 'jquery';

export const forgotPasswordService = {
  findUserByLoginId,
  allowSendOtp,
  validatorPassword,
  updatePassword
};


function validatorPassword({ getFieldValue }) {
  return {
    validator(_, value) {
      const siteInfo = JSON.parse($("#allInfoApp")[0].value);
      const secPCP = siteInfo.fldSecPCP;
      const pwdMinLen = siteInfo.fldPwdMinLen;
      const pwdConUCC = siteInfo.fldPwdConUCC;
      const pwdConLCC = siteInfo.fldPwdConLCC;
      const pwdConNC = siteInfo.fldPwdConNC;
      const pwdConSC = siteInfo.fldPwdConSC;
      const pwdNotUN = siteInfo.fldPwdNotUN;

      if (!value)
        return Promise.resolve();
      if (secPCP !== 'Yes')
        return Promise.resolve();
      if (value.length < pwdMinLen)
        return Promise.reject(new Error('Must be 8 characters or more' ));
      if (pwdConUCC && pwdConUCC.toUpperCase() === 'YES') {
        const arr = Array.from(value);
        const format = /^[a-zA-Z]+$/;
        const checkUcc = arr.some(function (e) {
          return (format.test(e) && e.toUpperCase() === e);
        });
        if (!checkUcc) {
          return Promise.reject(new Error('Must contain atleast one UPPER CASE character'));
        }
      }
      if (pwdConLCC && pwdConLCC.toUpperCase() === 'YES') {
        const arr = Array.from(value);
        const format = /^[a-zA-Z]+$/;
        const checkLcc = arr.some(function (e) {
          return (format.test(e) && e.toLowerCase() === e);
        });
        if (!checkLcc) {
          return Promise.reject(new Error('Must contain atleast one LOWER CASE character'));
        }
      }
      if (pwdConNC && pwdConNC.toUpperCase() === 'YES') {
        const arr = Array.from(value);
        const checkConNC = arr.some(function (e) {
          return !isNaN(e);
        });
        if (!checkConNC) {
          return Promise.reject(new Error('Must contain atleast one digit character'));
        }
      }
      if (pwdConSC && pwdConSC.toUpperCase() === 'YES') {
        const format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
        const checkConSC = format.test(value);
        if (!checkConSC) {
          return Promise.reject(new Error('Must contain atleast one special character'));
        }
      }
      if (pwdNotUN && pwdNotUN.toUpperCase() === 'YES') {
        const loginID = getFieldValue('loginId');
        const checkNotUN = loginID === value;
        if (checkNotUN) {
          return Promise.reject(new Error('Do not use your Login ID as password'));
        }
      }
      return Promise.resolve();
    },
  };
}

function findUserByLoginId(loginId, showNewPassArea, showError) {
  const url = 'api/finduserbyloginid';
  axios
    .post(url, { loginId: loginId })
    .then((res) => {
      if (res.data) {
        allowSendOtp(loginId, showNewPassArea);
      }
      else {
        showError('user not exist');
      }
    });
}

function allowSendOtp(loginId, showNewPassArea) {
  const url = 'api/sendmailotp';
  axios
    .post(url, { loginId: loginId })
    .then((res) => {
      if (res.data) {
        showNewPassArea();
      }
    });
}

function updatePassword(loginId, password, otp, showError, goToLogin) {
  const url = 'api/updatepassword';
  axios
  .post(url, { loginId: loginId, password: password, otp: otp })
    .then((res) => {
      if(res.data.status){
        goToLogin();
      }
      else{
        showError(res.data.message);
      }
    });
}
