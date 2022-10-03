import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Select, Row, Col, Divider } from 'antd';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../service/interceptor';
import { YES_NO_CONST, LANGUAGE_EDIT, HOME_PAGE, STATUS } from '../constant';
import { useTranslation } from 'react-i18next';
import NavBarMenu from '../components/NavBarMenu';
import { useAuth } from '../AuthContext';

function Login() {
  const [form] = Form.useForm();
  const {user} = useAuth();
  const { t } = useTranslation()
  const navigate = useNavigate();

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
      .then(() => {
        // navigate("/myProfile")
      });
  };

  return (
    <>
      <NavBarMenu/>
      <div className="container">
        <span className='page-title'>{t('My Profile')}</span>
        <div className='search-criteria'>
          <Form
            name="frmSearchEmpl"
            form={form}
            autoComplete="off"
            onFinish={() => {
              saveEmployee();
            }}
          >

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('Login Name')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="fldEmpLoginID" rules={[
                      {
                        required: true,
                        message: t('Login Name') + t('share.error.notEntered')
                      }
                    ]}>
                      <Input className="txt-search" />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('User Name')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="fldEmpName" rules={[
                      {
                        required: true,
                        message: t('User Name') + t('share.error.notEntered')
                      }
                    ]}>
                      <Input className="txt-search" />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('Email')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="fldEmpEmail" rules={[
                      {
                        required: true,
                        message: t('Email') + t('share.error.notEntered')
                      }
                    ]}>
                      <Input className="txt-search" />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Preferred Language')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldPreferredLang" rules={[
                      {
                        required: true,
                        message: t('Preferred Language') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select defaultValue={LANGUAGE_EDIT[0]} className="sel-search" options={LANGUAGE_EDIT} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Home Page')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldHomePage" rules={[
                      {
                        required: true,
                        message: t('Home Page') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select className="sel-search" defaultValue={'---'} options={HOME_PAGE} />
                    </Form.Item>
                  </Col>
                </Row>

              </Col>
            </Row>

            <Divider style={{'background-color':'blue'}} />

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('Old Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empOldPwd" rules={[
                      {
                        required: true,
                        message: t('Old Password') + t('share.error.notEntered')
                      }
                    ]}>
                      <Input className="txt-search" type='password' />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('New Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empNewPwd" rules={[
                      {
                        required: true,
                        message: t('New Password') + t('share.error.notEntered')
                      }
                    ]}>
                      <Input className="txt-search" type='password' />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">
                    <div>{t('Confirm Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empConfirmNewPwd" rules={[
                      {
                        required: true,
                        message: t('Confirm Password') + t('share.error.notEntered')
                      },
                      ({ getFieldValue }) => ({
                        validator(_, value) {
                          if (!value || getFieldValue('empNewPwd') === value) {
                            return Promise.resolve();
                          }
                          return Promise.reject(new Error('The two passwords that you entered do not match!'));
                        },
                      }),
                    ]}>
                      <Input type='password' className="txt-search" />
                    </Form.Item>
                  </Col>
                </Row>
              </Col>
            </Row>

            <Divider style={{'background-color':'blue'}} />

            <Col md={{ span: 24 }}>
              <Row className='border-default-search p-5'>
                <Col md={{ span: 6 }}>
                </Col>
                <Col md={{ span: 6 }}>
                  <Button className="btn-primary btn-search" htmlType='submit'>
                    {t('Save')}
                  </Button>
                  {' '}
                  <Button className="btn-primary btn-search" 
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
    </>
  );
}
export default Login;
