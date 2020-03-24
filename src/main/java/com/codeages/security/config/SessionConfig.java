package com.codeages.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.stereotype.Component;

import com.codeages.framework.session.HttpSessionStrategy;

@Configuration
@EnableJdbcHttpSession
@Component
public class SessionConfig extends HttpSessionStrategy {

}
