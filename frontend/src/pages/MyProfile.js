import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Select, Row, Col, Divider } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from '../service/interceptor';
import { HOME_PAGE, LANGUAGE } from '../constant';
import { useTranslation } from 'react-i18next';
import NavBarMenu from '../components/NavBarMenu';
import { useAuth } from '../AuthContext';
import Footer from '../components/Footer';

function Login() {
  const [form] = Form.useForm();
  const {user} = useAuth();
  const { t } = useTranslation()
  const navigate = useNavigate();
  const [msg, setMsg] = useState("");

  useEffect(() => {

    const fldEmpNo = user?.employee?.fldEmpNo;
    if (fldEmpNo) {
      axios({
        method: 'get',
        url: '/api/detailEmployee',
        params: {
          fldEmpNo,
        }
      }).then((res) => {
        form.setFieldsValue(res.data);
      });
    }
  }, []);

  const saveEmployee = () => {
    axios
      .post("/api/updateMyprofile", 
        form.getFieldsValue()
      )
      .then((res) => {
        if(!res.data.status){
          setMsg(res.data.message);
        } else {
          setMsg("Profile has been changed!");
        }
      });
  };

  const a = HOME_PAGE;
  console.log(a);
  return (
    <>
      <NavBarMenu/>
      <div className="container">
        <span className='page-title'>{t('My Profile')}</span>
        <div className='wrap-login wrap-border'>
          <div className='alert alert-danger' style={{ visibility: msg ? 'visible' : 'hidden' }}>{msg}</div>
          <Form
            name="frmSearchEmpl"
            form={form}
            autoComplete="off"
            onFinish={() => {
              setMsg("");
              saveEmployee();
            }}
          >

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div className='required'>{t('Login Name')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="fldEmpLoginID" rules={[
                      {
                        required: true,
                        message: t('Field cannot be empty')
                      }
                    ]}>
                      <Input />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div  className='required'>{t('User Name')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="fldEmpName" rules={[
                      {
                        required: true,
                        message: t('Field cannot be empty')
                      }
                    ]}>
                      <Input />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div className='required'>{t('Email')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="fldEmpEmail" rules={[
                      {
                        required: true,
                        message: t('Field cannot be empty')
                      }
                    ]}>
                      <Input />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui required">{t('Preferred Language')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldPreferredLang" rules={[
                      {
                        required: true,
                        message: t('Field cannot be empty')
                      }
                    ]}>
                      <Select defaultValue={LANGUAGE[1]} options={LANGUAGE.filter((_, idx)=> idx !== 0)} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Home Page')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldHomePage">
                      <Select  options={HOME_PAGE} />
                    </Form.Item>
                  </Col>
                </Row>

              </Col>
            </Row>

            <Divider />

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('Old Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empOldPwd" rules={[
                      ({ getFieldValue }) => ({
                        validator(_, value) {
                          if (!value && getFieldValue('empNewPwd')) {
                            return Promise.reject(new Error(t('Old Password') + t('share.error.notEntered')));
                          }
                          return Promise.resolve();
                        },
                      }),
                    ]}>
                      <Input type='password' />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('New Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empNewPwd">
                      <Input type='password' />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('Confirm Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empConfirmNewPwd" rules={[
                      ({ getFieldValue }) => ({
                        validator(_, value) {
                          if (!value || getFieldValue('empNewPwd') === value) {
                            return Promise.resolve();
                          }
                          return Promise.reject(new Error('The two passwords that you entered do not match!'));
                        },
                      }),
                    ]}>
                      <Input type='password' />
                    </Form.Item>
                  </Col>
                </Row>
              </Col>
            </Row>

            <Divider />

            <Col md={{ span: 24 }}>
              <Row className='border-default-search p-5'>
                <Col md={{ span: 6 }}>
                </Col>
                <Col md={{ span: 6 }}>
                  <Button className="btn-primary btn-search btn" htmlType='submit'>
                    {t('Save')}
                  </Button>
                  {' '}
                  <Button className="btn-primary btn-search btn" 
                    onClick={() => {
                      navigate("/")
                    }} >
                    {t('Quit')}
                  </Button>
                </Col>
              </Row>
            </Col>
            <Form.Item hidden name="fldEmpNo" />
          </Form>
        </div>
      </div>
      <Footer/>
    </>
  );
}
export default Login;
