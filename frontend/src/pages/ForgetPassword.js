import React, { useState, useEffect, useRef } from 'react';
import { useNavigate  } from "react-router-dom";
import { Form,  Button, } from 'antd';
import { useTranslation } from 'react-i18next';
import { forgotPasswordService } from '../service/forgotPasswordService';
import $ from 'jquery';
import i18next from 'i18next';


const STATUS = {
  STARTED: 'Started',
  STOPPED: 'Stopped',
}

function ForgetPassword() {
  const { t } = useTranslation()
  const navigate  = useNavigate ();
  const [form] = Form.useForm();
  const [error, setError] = useState('');
  const [countdown, setCountdown] = useState()
  const [passwordIsEmpty, setpasswordIsEmpty] = useState(true);

  const goToLogin= ()=> {
    navigate("/login");
  }

  useEffect(()=>{
    i18next.changeLanguage('en');
  },[])

  

  const onChangePass = (obj) => {
    if (obj.target.value) {
      setpasswordIsEmpty(false);
    }
    else {
      setpasswordIsEmpty(true);
    }
  }

  const validatorRePass = ({ getFieldValue }) => ({
    validator(_, value) {
      if (!value || getFieldValue('newPassword') === value) {
        return Promise.resolve();
      }
      return Promise.reject(new Error('The two passwords that you entered do not match!'));
    },
  })

  const validatorIsNumber = () => ({
    validator(_, value) {
      if (value && !isNaN(value)) {
        return Promise.resolve();
      }
      return Promise.reject(new Error('không phải là số'));
    },
  })

  useInterval(
    () => {
      if (countdown.secondsRemaining > 0) {
        setCountdown({ ...countdown, secondsRemaining: countdown.secondsRemaining - 1 });
      } else {
        setCountdown({ ...countdown, status: STATUS.STOPPED });
      }
    },
    countdown?.status === STATUS.STARTED ? 1000 : null,
    // passing null stops the interval
  )

  return (
    <>
      <div className='container-login'>
        <Form
          form={form}
          name="nest-messages"
          onFinish={(values) => {
            setError('');
            const {loginId, newPassword, otp} = values;
            forgotPasswordService.updatePassword(loginId, newPassword, otp, setError, goToLogin);

          }}
        >
          <div className='wrap-login p-l-85 p-r-85 p-t-55 p-b-55'>
            <span className='login-form-title p-b-32'>
              {t('Forget Password')}
            </span>
            <div className='alert alert-danger' style={{ visibility: error ? 'visible' : 'hidden' }}>{error}</div>
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
                <input className='input100' />
              </Form.Item>
            </div>

            <div class="container-login100-form-btn">
              <Button
                type="primary"
                htmlType="button"
                onClick={async () => {
                  try {
                    const siteInfo = JSON.parse($("#allInfoApp")[0].value);
                    const {loginId} = await form.validateFields(['loginId']);
                    const showNewPassArea = () => {
                      setCountdown({
                        status: STATUS.STARTED,
                        secondsRemaining: siteInfo.countdownOtp
                      })
                    };
                    setError('');
                    forgotPasswordService.findUserByLoginId(loginId,showNewPassArea , setError);
                  }
                  catch {

                  }

                }}
                loading={countdown?.status === STATUS.STARTED}
              >
                {!countdown?.status ? 'Send OTP' : (countdown.secondsRemaining === 0 ? 'Re-send OTP' : countdown.secondsRemaining)}
              </Button>

            </div>
            {
              countdown?.status && (
                <>
                  <span class='txt1 p-b-11 required'>
                    {t('New Password')}
                  </span>
                  <div class='m-b-36'>
                    <Form.Item
                      name="newPassword"
                      className='input'
                      rules={[
                        {
                          required: true,
                          message: t('field is required')
                        },
                        forgotPasswordService.validatorPassword
                      ]}
                    >
                      <input type="password" onChange={onChangePass} className='input100' />
                    </Form.Item>
                  </div>

                  <span class={passwordIsEmpty ? 'txt1 p-b-11' : 'txt1 p-b-11 required'}>
                    {t('Re-input Password')}
                  </span>
                  <div class='m-b-36'>
                    <Form.Item
                      name="reInputPassword"
                      className='input'
                      rules={[
                        {
                          required: !passwordIsEmpty,
                          message: t('field is required')
                        },
                        validatorRePass
                      ]}
                    >
                      <input type="password" className='input100' />
                    </Form.Item>
                  </div>

                  <span class='txt1 p-b-11 required'>
                    {t('OTP')}
                  </span>
                  <div class='m-b-36'>
                    <Form.Item
                      name="otp"
                      className='input'
                      rules={[
                        {
                          required: true,
                          message: t('field is required')
                        },
                        {
                          min: 6,
                          message: "Must be 6 characters",
                        }, {
                          max: 6,
                          message: "Must be 6 characters",
                        }, validatorIsNumber
                      ]}
                    >
                      <input className='input100' />
                    </Form.Item>
                  </div>
                  <div class="container-login100-form-btn">
                    <Button htmlType="submit" className="login100-form-btn">
                      {t('Save')}
                    </Button>

                  </div>
                </>
              )
            }
          </div>
        </Form>
      </div>
    </>
  );
}
export default ForgetPassword;

function useInterval(callback, delay) {
  const savedCallback = useRef()

  // Remember the latest callback.
  useEffect(() => {
    savedCallback.current = callback
  }, [callback])

  // Set up the interval.
  useEffect(() => {
    function tick() {
      savedCallback.current()
    }
    if (delay !== null) {
      let id = setInterval(tick, delay)
      return () => clearInterval(id)
    }
  }, [delay])
}