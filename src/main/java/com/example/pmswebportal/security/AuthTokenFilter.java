
package com.example.pmswebportal.security;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.pmswebportal.services.LoginAttemptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest passedRequest = request;

        if (request.getRequestURI().contains("/login")) {
            passedRequest = new MyHttpServletRequestWrapper(request);
            String loginId = readLoginIdFromRequest(passedRequest);
            if (Strings.isNotEmpty(loginId) && loginAttemptService.isBlocked(loginId)) {
                ObjectMapper objectMapper = new ObjectMapper();
                response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
                response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(response.getWriter(), Collections.singletonMap("message", "Too many attempts"));
                response.getWriter().flush();
                response.flushBuffer();
                return;
            }
        }
        filterChain.doFilter(passedRequest, response);
    }

    private String readLoginIdFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String loginFormJson = IOUtils.toString(reader);
        if (Strings.isNotEmpty(loginFormJson)) {
            ObjectNode node = new ObjectMapper().readValue(loginFormJson, ObjectNode.class);
            if (node.get("loginId") != null) {
                return node.get("loginId").asText();
            }
        }
        return Strings.EMPTY;
    }
}