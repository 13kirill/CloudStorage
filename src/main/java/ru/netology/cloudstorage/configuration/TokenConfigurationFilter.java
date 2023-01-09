package ru.netology.cloudstorage.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenConfigurationFilter extends AbstractAuthenticationProcessingFilter {

    protected TokenConfigurationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String authToken = request.getHeader("auth-token");
        if(authToken == null){
            TokenAuthentication tokenAuthentication = new TokenAuthentication();
            tokenAuthentication.setAuthenticated(false);
            return tokenAuthentication;
        }
        TokenAuthentication tokenAuthentication = new TokenAuthentication(authToken);
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }
}
