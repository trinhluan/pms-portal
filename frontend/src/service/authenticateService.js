import axios from 'axios';

export const authenticateService = {
  login,
  logout,
  currentUser
};
function login(loginId, password, callback) {
  const url = '/api/login';
  axios
    .post(url, { loginId: loginId, password: password })
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      callback({ error: { status: error.response.status } });
    });
}
function logout(page) {
  const url = '/logout';
  axios.post(url, {page});
}
function currentUser() {
  return localStorage.getItem('user') || '';
}
