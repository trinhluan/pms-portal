import React from 'react';
import { authenticateService } from './service/authenticateService';
import { Navigate } from 'react-router-dom';
import i18next from 'i18next';

const AuthContext = React.createContext(null);

export function useAuth() {
  return React.useContext(AuthContext);
}

export function RequireAuth({ children }) {
  let auth = useAuth();

  if (!auth.user) {
    // Redirect them to the /login page, but save the current location they were
    // trying to go to when they were redirected. This allows us to send them
    // along to that page after they login, which is a nicer user experience
    // than dropping them off on the home page.
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default function AuthProvider({ children }) {
  const [user, setUser] = React.useState(() => {
    try {
      // Get from local storage by key
      const item = window.localStorage.getItem('user');
      // Parse stored json or if none return initialValue
      return item ? JSON.parse(item) : null;
    } catch (error) {
      // If error also return initialValue
      console.log(error);
      return null;
    }
  });

  let signin = (param, callback) => {
    authenticateService.login(param, (data) => {
      if (!data.error) {
        const user = { ...data };
        setUser(user);
        // set language from user info
        if(user?.employee?.fldPreferredLang) {
          i18next.changeLanguage(user?.employee?.fldPreferredLang);
        }
        
        localStorage.setItem(
          'user',
          JSON.stringify({
            ...user
          })
        );
      }
      callback(data);
    });
  };

  let signout = (callback) => {
    authenticateService.logout();
    setUser(null);
    localStorage.removeItem('user');
    if (callback) {
      callback();
    }
    window.location.href = './login';
  };

  const value = { user, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
