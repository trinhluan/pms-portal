import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Select, Pagination} from 'antd';
import { Link, useNavigate } from 'react-router-dom';
import axios from '../service/interceptor';
import { ALLOW_LOGIN, LANGUAGE, LANG_DIC, STATUS } from '../constant';
import { useTranslation } from 'react-i18next';
import NavBarMenu from '../components/NavBarMenu';
import Footer from '../components/Footer';

function Login() {
  const [form] = Form.useForm();
  const { t } = useTranslation()
  const navigate = useNavigate();
  const [employeeList, setEmployeeList] = useState([]);
  const [siteList, setSiteList] = useState([]);
  const pageSize = 10;

  const [pagingValue, setPagingValue] = useState({
    totalPage: 0,
    current: 1,
    minIndex: 0,
    maxIndex: 0
 });

  useEffect(() => {
    getAllSites();
    getAllEmployee({});
    setPagingValue({
      ...pagingValue,
      totalPage: employeeList?.length / pageSize,
      minIndex: 0,
      maxIndex: pageSize
    });
  }, []);

  const getAllEmployee = (params) => {
    axios
      .post('/api/searchEmployee', {
        fldSiteCode: params?.fldSiteCode,
        fldEmpLoginID: params?.fldEmpLoginID,
        fldEmpName: params?.fldEmpName,
        fldEmpEmail: params?.fldEmpEmail,
        fldAllowLogIn: params?.fldAllowLogIn,
        fldPreferredLang: params?.fldPreferredLang,
        fldEmpStatus: params?.fldEmpStatus
      })
      .then((res) => {
        setEmployeeList(res.data);
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

  const handleChange = (page) => {
    let data = form.getFieldsValue()
    let params = {data};
    getAllEmployee(params);
    setPagingValue({
      ...pagingValue,
      current: page,
      minIndex: (page - 1) * pageSize,
      maxIndex: page * pageSize
    });
  };

  return (
    <>
    <NavBarMenu/>
    <div className='container'>
      <span className='page-title'>{t('User Profile Maintenance')}</span>
      <div className='search-criteria'>
        <span className='criteria-label'>{t('Search Criteria')}</span>
        <Form
            name="frmSearchEmpl"
            form={form}
            autoComplete="off"
            onFinish={(values) => {
              let params = {};
              params.fldSiteCode = values?.siteCode;
              params.fldEmpLoginID = values?.loginName;
              params.fldEmpName = values?.userName;
              params.fldEmpEmail = values?.empEmail;
              params.fldAllowLogIn = values?.allowLogIn;
              params.fldPreferredLang = values?.preferredLang;
              params.fldEmpStatus = values?.status;
              getAllEmployee(params);
            }}
          >
            <table>
              <tr>
                <td><div className="label-search-ui">{t('Primary Login Site Code')}</div></td>
                <td>
                  <Form.Item name="siteCode">
                    <Select className="sel-search" options={siteList} />
                  </Form.Item>
                </td>
                <td></td>
                <td><div className="label-search-ui">{t('Allow Login')}</div></td>
                <td>
                  <Form.Item name="allowLogIn">
                    <Select className="sel-search" options={ALLOW_LOGIN} />
                  </Form.Item>
                </td>
              </tr>
              <tr>
                <td><div className="label-search-ui">{t('Login Name')}</div></td>
                <td>
                <Form.Item name="loginName">
                    <Input className="txt-search" />
                  </Form.Item></td>
                <td></td>
                <td><div className="label-search-ui">{t('Language')}</div></td>
                <td><Form.Item name="preferredLang">
                    <Select className="sel-search" options={LANGUAGE} />
                  </Form.Item></td>
              </tr>
              <tr>
                <td><div className="label-search-ui">{t('User Name')}</div></td>
                <td>
                <Form.Item name="userName">
                    <Input className="txt-search" />
                  </Form.Item></td>
                <td></td>
                <td><div className="label-search-ui">{t('Status')}</div></td>
                <td> <Form.Item name="status">
                    <Select className="sel-search" options={STATUS} />
                  </Form.Item></td>
              </tr>
              <tr>
                <td><div className="label-search-ui">{t('Email Account')}</div></td>
                <td>
                <Form.Item name="empEmail">
                    <Input className="txt-search" />
                  </Form.Item></td>
                <td></td>
                <td></td>
                <td> </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <Button htmlType="submit" className="btn btn-search" >
                    {t('Search')}
                  </Button>
                  <Button className="btn btn-reset" 
                    onClick={() => {
                      form.resetFields();
                     }} >
                    {t('Reset')}
                  </Button></td>
                <td></td>
                <td></td>
                <td> </td>
              </tr>
            </table>
            
          </Form>
       
      </div>
      <div>
        <table className="table-parent">
          <tr>
            <td style={{width: "50%"}}>
              <Button className="btn-create" onClick={() => { navigate("/detailEmployee") }}>
                {t('Create')}
              </Button>
            </td>
            <td>
              <Pagination
                pageSize={pageSize}
                current={pagingValue.current}
                total={employeeList?.length}
                onChange={handleChange}
                style={{ bottom: "0px" }}
              />
            </td>
          </tr>
        </table>

      </div>
      <div className='search-result'>
        <table className="table-parent border-default">
          <thead className="border-default">
            <th class="border-default">{t('Edit')}</th>
            <th class="border-default">{t('Login Name')}</th>
            <th class="border-default">{t('User Name')}</th>
            <th class="border-default">{t('Email Account')}</th>
            <th class="border-default">{t('Allow Login')}</th>
            <th class="border-default">{t('Language')}</th>
            <th class="border-default">{t('Is Technician')}</th>
            <th class="border-default">{t('Is Patrol Staff')}</th>
          </thead>
          <tbody>
            {employeeList?.map((row, index) => {
              return (
                index >= pagingValue.minIndex &&
                index < pagingValue.maxIndex && 
                <tr>
                  <td className="vertical-align-top text-center border-default">
                    {' '}
                    <Link
                      to={{
                        pathname: '/detailEmployee',
                        search: `?fldEmpNo=${row.fldEmpNo}`
                      }}
                    >
                      {t('Edit')}
                    </Link>
                  </td>
                  <td className="vertical-align-top border-default">
                    {row.fldEmpLoginID}
                  </td>
                  <td className="vertical-align-top border-default">
                    {row.fldEmpName}
                  </td>
                  <td className="vertical-align-top border-default">
                    {row.fldEmpEmail}
                  </td>
                  <td className="vertical-align-top border-default">
                    {row.fldAllowLogIn}
                  </td>
                  <td className="vertical-align-top border-default">
                    {LANG_DIC[row.fldPreferredLang]}
                  </td>
                  <td className="vertical-align-top border-default">
                    {row.fldRespPersonTech}
                  </td>
                  <td className="vertical-align-top border-default">
                    {row.fldIsPatrolStaff}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
      
    </div>
    <Footer/>
    </>
  );
}
export default Login;
