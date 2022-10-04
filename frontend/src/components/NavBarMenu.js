import React, { useState } from 'react';
import { Select } from 'antd';
import { Link } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { useTranslation } from 'react-i18next';
import { LANGUAGE } from '../constant';
import i18next from 'i18next';

function NavBarMenu() {
  const {user, signout} = useAuth();
  const { t } = useTranslation()
  const [language, setLanguage] = useState(user?.employee?.fldPreferredLang);

  const menu = [{
    name: t('Work Order'),

    subMenu: [{
      name: t('Enquiry Maintenance'),
      url: '#'
    },
    {
      name: t('Scheduled Work Order Maintenance'),
      url: '#'
    },
    {
      name: t('Scheduled Work Order Maintenance in Yearly View'),
      url: '#'
    },
    {
      name: t('Work Order Maintenance'),
      url: '#'
    },
    {
      name: t('Work Order Audit'),
      url: '#'
    }]
  },
  {
    name: t('Patrol'),

    subMenu: [{
      name: t('Question Pool Maintenance'),
      url: '#'
    },
    {
      name: t('Patrol Form Maintenance'),
      url: '#'
    },
    {
      name: t('Patrol Assignment Maintenance'),
      url: '#'
    }]
  },
  {
    name: t('Stock'),

    subMenu: [{
      name: t('Stock Master Maintenance'),
      url: '#'
    },
    {
      name: t('Stock In'),
      url: '#'
    },
    {
      name: t('Stocktake'),
      url: '#'
    }]
  },
  {
    name: t('Reports'),

    subMenu: [{
      name: t('Stock Balance Report'),
      url: '#'
    },
    {
      name: t('Stock Movement Report'),
      url: '#'
    },
    {
      name: t('Work Order by Service Type Report'),
      url: '#'
    },
    {
      name: t('Work Order Manpower Report'),
      url: '#'
    },
    {
      name: t('Complaint Analysis Report'),
      url: '#'
    },
    {
      name: t('Expense Enquiry'),
      url: '#'
    },
    {
      name: t('Patrol Q&A Comparative Matrix'),
      url: '#'
    }]
  },
  {
    name: t('Administration'),

    subMenu: [{
      name: t('System Param Maintenance'),
      url: '#'
    },
    {
      name: t('Function Security Group Maintenance'),
      url: '#'
    },
    {
      name: t('Data Security Group Maintenance'),
      url: '#'
    },
    {
      name: t('User Profile Maintenance'),
      url: '/searchEmployee'
    },
    {
      name: t('Building Maintenance'),
      url: '#'
    },
    {
      name: t('Floor Unit Maintenance'),
      url: '#'
    },
    {
      name: t('Tenant Maintenance'),
      url: '#'
    },
    {
      name: t('Service Type Maintenance'),
      url: '#'
    },
    {
      name: t('Channel Maintenance'),
      url: '#'
    },
    {
      name: t('Work Order Overdue Maintenance'),
      url: '#'
    },
    {
      name: t('Device Registration Maintenance'),
      url: '#'
    },
    {
      name: t('My Profile'),
      url: '/myProfile'
    }]
  }]
  return (
    <>
      <div class="topnav" id="myTopnav" onClick={(e)=>{
        console.log(e);
        if(e.target.className.indexOf('icon') > -1) return;
        if(e.target.className.indexOf('dropbtn') > -1) return;
        
        var x = document.getElementById("myTopnav");
        if (x.className.indexOf("responsive") > -1) {
          x.className = "topnav";
        }
      }}>
        <div className='container'>
          <Link to={user?.employee?.fldHomePage ?? '/'}>{t('Home')}</Link>
          <div className='navbar-collapse'>
            <div style={{ marginLeft: 'auto' }}>

              {menu.map(item => {
                if (item.subMenu) {
                  return (
                    <div class="dropdown">
                      <button className="dropbtn">{item.name}</button>
                      <div class="dropdown-content">
                        {item.subMenu.map(sub => (
                          <Link to={sub.url}>{sub.name}</Link>
                        ))}
                      </div>
                    </div>
                  )
                }
              })}

              <div class="dropdown">
                <button className="dropbtn">{user?.employee?.fldEmpName}</button>
                <div class="dropdown-content">
                  <Link onClick={() => {
                   signout();
                  }}>{t('Logout')}</Link>
                </div>
              </div>
              <div style={{ display: 'flex', height: '100%' }}>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  <Select options={LANGUAGE.filter((_, idx)=> idx !== 0)} value={language} onChange={(value) => {
                    setLanguage(value);
                    i18next.reloadResources().then(()=>{
                      i18next.changeLanguage(value);
                    });
                }}>
                  </Select>
                </div>
              </div>

            </div>

          </div>
          <Link className='icon' onClick={()=>{
            var x = document.getElementById("myTopnav");
            if (x.className === "topnav") {
              x.className += " responsive";
            } else {
              x.className = "topnav";
            }
          }}>&#9776;</Link>
          {/* <a href="javascript:void(0);" class="icon" onclick="myFunction()">&#9776;</a> */}
        </div>
      </div>
    </>
  );
}

export default NavBarMenu;
