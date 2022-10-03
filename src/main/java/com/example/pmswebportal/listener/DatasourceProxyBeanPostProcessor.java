package com.example.pmswebportal.listener;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder.SingleQueryExecution;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import javax.sql.DataSource;

import com.example.pmswebportal.security.CustomAccountDetail;
import com.example.pmswebportal.utilities.HttpReqRespUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource && !(bean instanceof ProxyDataSource)) {
            // Instead of directly returning a less specific datasource bean
            // (e.g.: HikariDataSource -> DataSource), return a proxy object.
            // See following links for why:
            //   https://stackoverflow.com/questions/44237787/how-to-use-user-defined-database-proxy-in-datajpatest
            //   https://gitter.im/spring-projects/spring-boot?at=5983602d2723db8d5e70a904
            //   https://arnoldgalovics.com/configuring-a-datasource-proxy-in-spring-boot/
            final ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
            return factory.getProxy();
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    private static class ProxyDataSourceInterceptor implements MethodInterceptor {
        private final DataSource dataSource;

        public ProxyDataSourceInterceptor(final DataSource dataSource) {
            this.dataSource = ProxyDataSourceBuilder.create(dataSource)
                    .multiline()
                    .afterQuery(new SingleQueryExecution() {
                        @Override
                        public void execute(ExecutionInfo arg0, List<QueryInfo> queryInfoList) {
                            Connection connection = null;
                            try {
                                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                                
                                String tableName = String.format("tblsyssqllog%s", String.valueOf(LocalDate.now().getYear()));
                                
                                connection = dataSource.getConnection();

                                for (QueryInfo queryInfo : queryInfoList) {

                                    if(queryInfo.getQuery().indexOf("tblsysaccesslog") > 0 ) continue;
                                    PreparedStatement statement = connection.prepareStatement(
                                    String.format("INSERT INTO %s (fldEmpNo, fldEmpName, fldHost, fldAction, fldDateTime) values(?,?,?,?,?)", tableName));

                                    if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                                        statement.setString(1, "anonymousUser");
                                        statement.setString(2, "anonymousUser");
                                    } else {
                                        CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
                                        
                                        statement.setString(1, account.getUsername());
                                        statement.setString(2, account.getEmployee().getFldEmpName());
                                    }
                                    
                                    statement.setString(3, HttpReqRespUtils.getClientIpAddress());
                                    statement.setString(4, queryInfo.getQuery());
                                    statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                                    statement.execute();
                                }

                                
                            } catch (SQLException e) {

                            } finally {
                                if(connection != null) {
                                    try {
                                        connection.close();
                                    } catch (SQLException e) {
                                    }
                                }
                            }
                        }
                    })
                    .build();
        }

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            final Method proxyMethod = ReflectionUtils.findMethod(this.dataSource.getClass(),
                    invocation.getMethod().getName());
            if (proxyMethod != null) {
                return proxyMethod.invoke(this.dataSource, invocation.getArguments());
            }
            return invocation.proceed();
        }
    }
}