import React, { useEffect, useState } from 'react';
import { Form, Button, Checkbox } from 'antd';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'antd/lib/form/Form';
import { useAuth } from '../AuthContext';
import { useTranslation } from 'react-i18next';
import i18next from 'i18next';

function Login() {
  const { t } = useTranslation()
  const navigate = useNavigate();
  const { form } = useForm();
  const auth = useAuth();
  const [error, setError] = useState('');

  useEffect(()=>{
    i18next.changeLanguage('en');
  },[])
  return (
    <>
      <div className='container-login'>
      <Form
            form={form}
            name="nest-messages"
            onFinish={(values) => {
              const data = new FormData()
              data.append('username',values.loginId)
              data.append('password',values.password)
              data.append('remember-me', values.remember);

              auth.signin(data, (data) => {
                if (data.error) {
                  setError(t(data.error.message));
                } else {
                  i18next.reloadResources()
                      .then(() => {
                        i18next.changeLanguage(data.employee.fldPreferredLang);
                      });
                  navigate('/', { replace: true });
                }
              });
            }}
          >
        <div className='wrap-login p-l-85 p-r-85 p-t-55 p-b-55'>
          <span className='login-form-title p-b-32'>
           {t('Account Login')}
          </span>
         {
          (error ?? '') !== '' && (
              <div  className='alert alert-danger'>{error}</div>
          )
         } 
          <span class='txt1 p-b-11 required'>
            {t('Login ID')}
          </span>
          <div class='m-b-36'>
            <Form.Item
                    className='input'
                    name="loginId"
                    rules={[
                      {
                        required: true,
                        message: t('field is required')
                      }
                    ]}
                  >
              <input className='input100'/>
            </Form.Item>
          </div>
          <span class='txt1 p-b-11 required'>
            {t('Password')}
          </span>
          <div class='m-b-36'>
            <Form.Item
             className='input'
                    name="password"
                    rules={[
                      {
                        required: true,
                        message: t('field is required')
                      }
                    ]}
                  >
              <input type='password' className='input100'/>
            </Form.Item>
          </div>

            <div className='flex-sb-m w-full p-b-48'>
              <div className="contact100-form-checkbox">
              <Form.Item
            name="remember"
            className="mb-0 text-center"
            valuePropName="checked"
            initialValue={false}
          >
            <Checkbox>{t('Remember me')}</Checkbox>
          </Form.Item>
</div>
<div>
<Link to="/passwordReissue">{t('Forget Password')}</Link>

</div>
</div>
<div class="container-login100-form-btn">
<Button htmlType="submit" className="login100-form-btn">
                  {t('Login')}
                </Button>

</div>

        </div>
        </Form>
      </div>
    </>
  );
}
export default Login;
