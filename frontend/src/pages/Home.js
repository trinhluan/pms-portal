import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import Footer from '../components/Footer';
import NavBarMenu from '../components/NavBarMenu';

function Home() {
  
  const {user} = useAuth();
  const navigate = useNavigate();
  useEffect(()=>{
    if(user?.employee?.fldHomePage && user?.employee?.fldHomePage !== '#' ) {
      navigate(user?.employee?.fldHomePage, { replace: true });
    }
  },[])

  return (
    <>
    <NavBarMenu/>
    <div className='container'></div>
    <Footer/>
    </>
  );
}

export default Home;
