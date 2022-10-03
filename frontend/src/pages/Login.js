import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Row, Col } from 'antd';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'antd/lib/form/Form';
import { useAuth } from '../AuthContext';
import { useTranslation } from 'react-i18next';



function Login() {
  const { t } = useTranslation()
  const navigate = useNavigate();
  const { form } = useForm();
  const auth = useAuth();
  const [error, setError] = useState('');

  return (
    <>
      <div className='container-login'>
      <Form
            form={form}
            name="nest-messages"
            onFinish={(values) => {
              auth.signin(values.loginId, values.password, (data) => {
                if (data.error) {
                  setError(t('login.wrongPassword'));
                } else {
                  navigate('/', { replace: true });
                }
              });
            }}
          >
        <div className='wrap-login p-l-85 p-r-85 p-t-55 p-b-55'>
          <span className='login-form-title p-b-32'>
           {t('Account Login')}
          </span>
          <div  className='alert alert-danger'>Access Denied</div>
          <span class='txt1 p-b-11 required'>
            {t('Login ID')}
          </span>
          <div class='m-b-36'>
            <Form.Item
                    name="loginId"
                    rules={[
                      {
                        required: true,
                        message: t('Field cannot be empty')
                      }
                    ]}
                  >
              <input className='input100'/>
            </Form.Item>
          </div>
          <span class='txt1 p-b-11'>
            {t('Password')}
          </span>
          <div class='m-b-36'>
            <Form.Item
                    name="password"
                    rules={[
                      {
                        required: true,
                        message: t('Field cannot be empty')
                      }
                    ]}
                  >
              <input type='password' className='input100'/>
            </Form.Item>
          </div>


            <div className='flex-sb-m w-full p-b-48'>
              <div className="contact100-form-checkbox">
                <input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me"/>
                <label class="label-checkbox100" for="ckb1">
                Remember me
                </label>
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
