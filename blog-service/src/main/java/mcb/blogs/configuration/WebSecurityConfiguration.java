package mcb.blogs.configuration;

import mcb.blogs.authentication.AuthenticationFilter;
import mcb.blogs.authentication.JwtAuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JwtAuthenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/authenticate*").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterAfter(new AuthenticationFilter(new JwtAuthenticationManager()), BasicAuthenticationFilter.class);
    }
}
