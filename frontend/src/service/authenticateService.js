import axios from 'axios';

export const authenticateService = {
  login,
  logout,
  currentUser
};
function login(param, callback) {
  const url = '/login';
  axios
    .post(url, new URLSearchParams(param))
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      callback({ error: { status: error.response.status, message: error.response.data.message } });
    });
}
function logout(page) {
  const url = '/logout';
  axios.post(url, {page});
}
function currentUser() {
  return localStorage.getItem('user') || '';
}
