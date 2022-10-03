
import React,{ useEffect, useRef  } from 'react';
import 'antd/dist/antd.css';
import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import AuthProvider, { RequireAuth } from './AuthContext';
import { routes } from './routes';
import { siteService } from './service/siteService';


function App() {
  // eslint-disable-next-line no-mixed-operators
  const inputRef = useRef(null);
  const basename = window.location.pathname.substring(
    0,
    window.location.pathname.indexOf('/', 2)
  );
  useEffect(() => {    
    siteService.getPropertiesOfSite(inputRef);    
  }, []);
  return (
    <AuthProvider>
      <BrowserRouter basename={basename}>
        <div className="App">
          <input type={'hidden'} ref={inputRef} id={'allInfoApp'} />
          <Routes>
            {routes.map(({ path, Component, isLogin, props = {} }) => (
              <Route
                exact={false}
                path={path}
                element={
                  isLogin ? (
                    <RequireAuth>
                      <Component {...props} />
                    </RequireAuth>
                  ) : (
                    <Component {...props} />
                  )
                }
              ></Route>
            ))}

          </Routes>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
