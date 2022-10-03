
package com.example.pmswebportal.security;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.pmswebportal.utilities.HttpReqRespUtils;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@EnableScheduling
public class WebSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager configProviders(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // authenticationManagerBuilder.authenticationProvider(usernamePasswordAuthenticationProvider());
        // authenticationManagerBuilder.authenticationProvider(smsAuthenticationProvider());
        // authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login", "/api/login", "/api/searchEmployee", "/api/translations", "/api/forgotpassword",
                        "/api/allSites", "/api/detailEmployee", "/api/getDataSercurityGroupList",
                        "/api/getFuncSecurityGroupList", "/api/getServiceTypeList", "/api/getpropertiesofsite",
                        "/api/finduserbyloginid", "/api/sendmailotp", "/api/addEmployee", "/api/updateEmployee",
                        "/api/updateMyprofile","/api/updatepassword",
                        "/assets/**", "/manifest.json", "/favicon.ico", "/static/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .and().logout().logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {

                        CustomAccountDetail account = (CustomAccountDetail) authentication.getPrincipal();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        Session session = entityManager.unwrap(Session.class);
                        session.doWork(new Work() {

                            @Override
                            public void execute(Connection connection) throws SQLException {

                                String tableName = String.format("tblsysaccesslog%s",
                                        String.valueOf(LocalDate.now().getYear()));

                                PreparedStatement statement = connection.prepareStatement(
                                        String.format(
                                                "INSERT INTO %s (fldEmpNo, fldEmpName, fldHost, fldAction, fldAuditLog, fldDateTime) values(?,?,?,?,?,?)",
                                                tableName));
                                statement.setString(1, account.getUsername());
                                statement.setString(2, account.getEmployee().getFldEmpName());
                                statement.setString(3, HttpReqRespUtils.getClientIpAddress());
                                statement.setString(4, "Logout");
                                statement.setString(5, String.format("%s %s",
                                        dateTimeFormatter.format(LocalDateTime.now()), request.getParameter("page")));
                                statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                                statement.execute();

                                connection.close();

                            }
                        });

                        response.sendRedirect("/login");
                    }
                });
        return http.build();
    }

}
