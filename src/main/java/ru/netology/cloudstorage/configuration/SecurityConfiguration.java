package ru.netology.cloudstorage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(tokenConfigurationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeRequests().antMatchers("/login", "/logout").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public TokenConfigurationFilter tokenConfigurationFilter() {
        return new TokenConfigurationFilter("/**");
    }
}
