
import Home from './pages/Home';
import Login from './pages/Login';
import Employee from './pages/Employee';
import ForgetPassword from './pages/ForgetPassword';

import DetailEmployee from './pages/DetailEmployee';
import MyProfile from './pages/MyProfile';

export const routes = [
  {
    path: '/',
    Component: Home,
    isLogin: true
  },
  {
    path: '/login',
    Component: Login
  },
  {
    path: '/searchEmployee',
    Component: Employee
  },
  {
    path: '/passwordReissue',
    Component: ForgetPassword
  },
  {
    path: '/detailEmployee',
    Component: DetailEmployee
  },
  {
    path: '/myProfile',
    Component: MyProfile
  },
  
];
