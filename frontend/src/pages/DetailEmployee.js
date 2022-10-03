import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Select, Row, Col, Divider } from 'antd';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../service/interceptor';
import { YES_NO_CONST, LANGUAGE_EDIT, HOME_PAGE, STATUS } from '../constant';
import { useTranslation } from 'react-i18next';
import NavBarMenu from '../components/NavBarMenu';

function Login() {
  const [form] = Form.useForm();
  const { t } = useTranslation()
  const navigate = useNavigate();
  const [msgSave, setMsgSave] = useState(true);
  const [newFlg, setNewFlg] = useState(false);
  const [siteList, setSiteList] = useState([]);
  const [serviceTypeList, setServiceTypeList] = useState([]);
  const [funcSecurityGroupList, setFuncSecurityGroupList] = useState([]);
  const [dataSercurityGroupList, setDataSercurityGroupList] = useState([]);

  useEffect(() => {
    getAllSites();
    getDataSercurityGroupList();
    getFuncSecurityGroupList();
    getServiceTypeList();
    setMsgSave(true);

    const search = window.location.search;
    const params = new URLSearchParams(search);
    const fldEmpNo = params.get('fldEmpNo');
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
    } else {
      form.resetFields();
    }
    form.setFieldsValue({
      fldAllowLogIn: YES_NO_CONST[0]?.value,
      fldPreferredLang: LANGUAGE_EDIT[0]?.value,
      fldEmpStatus: STATUS[0]?.value,
      fldIsPatrolStaff: YES_NO_CONST[1]?.value,
      fldRespPersonTech: YES_NO_CONST[1]?.value,
      fldRespPersonNonTech: YES_NO_CONST[1]?.value,
    });
  }, []);

  const getDataSercurityGroupList = () => {
    axios({
      method: 'get',
      url: '/api/getDataSercurityGroupList',
      params: {
      }
    }).then((res) => {
      setDataSercurityGroupList(res.data.map(item => ({
        value: item.fldGroupCode,
        label: item.fldGroupName
      })));
    });
  };

  const getFuncSecurityGroupList = () => {
    axios({
      method: 'get',
      url: '/api/getFuncSecurityGroupList',
      params: {
      }
    }).then((res) => {
      setFuncSecurityGroupList(res.data.map(item => ({
        value: item.fldGroupCode,
        label: item.fldGroupName
      })));
    });
  };

  const getServiceTypeList = () => {
    axios({
      method: 'get',
      url: '/api/getServiceTypeList',
      params: {
      }
    }).then((res) => {
      setServiceTypeList(res.data.map(item => ({
        value: item.fldServiceType,
        label: item.fldServiceType
      })));
    });
  };

  const saveEmployee = (andNew) => {
    const search = window.location.search;
    const params = new URLSearchParams(search);
    const fldEmpNo = params.get('fldEmpNo');
    let url = "/api/addEmployee";
    if (fldEmpNo) {
      url = "/api/updateEmployee";
    }
    axios
      .post(url, 
        form.getFieldsValue()
      )
      .then(() => {
        if (andNew) {
          form.resetFields();
          navigate("/detailEmployee")
          setMsgSave(true);
        }
        setMsgSave(false);
      });

  };

  const getAllSites = () => {
    axios
      .post('/api/allSites')
      .then((res) => {
        const sites = res.data.map(item => ({
          value: item.fldSiteCode,
          label: item.fldSiteCode
        }));
        setSiteList(sites);
      });
  };

  return (
    <>
      <NavBarMenu/>
      <div className="container">
        <span className='page-title'>{t('User Profile Maintenance')}</span>
        <div className='search-criteria'>
          <div className='msg-save' hidden={msgSave}>{t('data is saved')}</div>
          <Form
            name="frmSearchEmpl"
            form={form}
            autoComplete="off"
            onFinish={() => {
              saveEmployee(newFlg);
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
                        message: t('Login Name') + t(' ') + t('share.error.notEntered')
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
                        message: t('User Name') + t(' ') + t('share.error.notEntered')
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
                        message: t('Email') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Input className="txt-search" />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Site Code')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldSiteCode" rules={[
                      {
                        required: true,
                        message: t('Site Code') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select className="sel-search" defaultValue={siteList[0]} options={siteList} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Allow Login')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldAllowLogIn" rules={[
                      {
                        required: true,
                        message: t('Allow Login') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select defaultValue={YES_NO_CONST[0]} className="sel-search" options={YES_NO_CONST} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Preferred Language')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldPreferredLang" rules={[
                      {
                        required: true,
                        message: t('Preferred Language') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select defaultValue={LANGUAGE_EDIT[0]} className="sel-search" options={LANGUAGE_EDIT} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Status')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldEmpStatus" rules={[
                      {
                        required: true,
                        message: t('Status') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select defaultValue={STATUS[0]} className="sel-search" options={STATUS} />
                    </Form.Item>
                  </Col>
                </Row>
              </Col>
            </Row>

            <Divider style={{'background-color':'blue'}} />

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Is Patrol Staff')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldIsPatrolStaff">
                      <Select defaultValue={YES_NO_CONST[1]} className="sel-search" options={YES_NO_CONST} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Respon. Person (Technical)')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldRespPersonTech">
                      <Select defaultValue={YES_NO_CONST[1]} className="sel-search" options={YES_NO_CONST} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Respon. Person (Non-Technical)')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldRespPersonNonTech">
                      <Select defaultValue={YES_NO_CONST[1]} className="sel-search" options={YES_NO_CONST} />
                    </Form.Item>
                  </Col>
                </Row>
              </Col>
            </Row>

            <Divider style={{'background-color':'blue'}} />

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Service Type(s) For Assignment')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="empServiceType">
                      <Select mode="multiple" className="sel-search" options={serviceTypeList} />
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
                    <div>{t('New Password')}</div>
                  </Col>
                  <Col md={{ span: 12 }} className="modal-primary">
                    <Form.Item name="empPwd" rules={[
                      {
                        required: true,
                        message: t('New Password') + t(' ') + t('share.error.notEntered')
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
                    <Form.Item name="empConfirmPwd" rules={[
                      {
                        required: true,
                        message: t('Confirm Password') + t(' ') + t('share.error.notEntered')
                      },
                      ({ getFieldValue }) => ({
                        validator(_, value) {
                          if (!value || getFieldValue('empPwd') === value) {
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

            <Row className="mb-primary">
              <Col md={{ span: 24 }}>
                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Function Security Group')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldFuncSecurityGroup" rules={[
                      {
                        required: true,
                        message: t('Function Security Group') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select className="sel-search" defaultValue={'---'} options={funcSecurityGroupList} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Data Security Group')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldDataSecurityGroup" rules={[
                      {
                        required: true,
                        message: t('Data Security Group') + t(' ') + t('share.error.notEntered')
                      }
                    ]}>
                      <Select className="sel-search" defaultValue={'---'} options={dataSercurityGroupList} />
                    </Form.Item>
                  </Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Home Page')}</Col>
                  <Col className="modal-primary" md={{ span: 12 }}>
                    <Form.Item name="fldHomePage" rules={[
                      {
                        required: true,
                        message: t('Home Page') + t(' ') + t('share.error.notEntered')
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
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Created At/By')}</Col>
                </Row>

                <Row className='border-default-search'>
                  <Col md={{ span: 6 }} className="label-search-ui">{t('Changed At/By')}</Col>
                </Row>
              </Col>
            </Row>

            <Col md={{ span: 24 }}>
              <Row className='border-default-search p-5'>
                <Col md={{ span: 6 }}>
                </Col>
                <Col md={{ span: 6 }}>
                  <Button htmlType="submit" className="btn-primary btn-search" onClick={() => {
                    // saveEmployee(false)
                    setNewFlg(false);
                  }}>
                    {t('Save')}
                  </Button>
                  {' '}
                  <Button htmlType="submit" className="btn-primary btn-search" style={{width: 100}} onClick={() => {
                    // saveEmployee(true)
                    setNewFlg(true);
                  }}>
                    {t('Save & New')}
                  </Button>
                  {' '}
                  <Button className="btn-primary btn-search" 
                    onClick={() => {
                      navigate("/searchEmployee")
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
