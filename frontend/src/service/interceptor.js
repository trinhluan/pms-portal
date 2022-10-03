import axios from 'axios';
import { notification } from 'antd';


axios.interceptors.request.use(
  function (config) {
    if (config.url.indexOf('/') === 0) {
      config.url = config.url.substring(1);
    }
    // spinning start to show
    document.body.classList.add('loading');
    const data = window.localStorage.getItem('user');
    // Parse stored json or if none return initialValue
    const user = data ? JSON.parse(data) : null;
    if (user) {
      config.headers.Authorization = `Bearer ${user.token}`;
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  function (response) {
    // spinning hide
    document.body.classList.remove('loading');

    return response;
  },
  function (error) {
    document.body.classList.remove('loading');
    if (!error?.response || error.response.status === 401) {
      window.location.href = '/login';
    }
    if ((error?.request?.responseURL.indexOf('/login') || -1) === -1) {
      openNotification(error?.response?.data || error?.data);
    }

    return Promise.reject(error);
  }
);

const openNotification = (error) => {
  notification['error']({
    message: 'Error',
    description: error.message,
    placement: 'bottomRight',
    duration: 8
  });
};


export default axios;
