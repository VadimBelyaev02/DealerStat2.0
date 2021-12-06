package com.leverx.dealerstat.config;

import com.leverx.dealerstat.model.Permission;
import com.leverx.dealerstat.security.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    @Autowired
    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.GET, "/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/**").anonymous()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/games").permitAll()
                .antMatchers(HttpMethod.GET, "/comments/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{id}/comments").permitAll()
                .antMatchers(HttpMethod.GET, "/objects/**").permitAll()
                .antMatchers(HttpMethod.POST, "/comments").authenticated()
                .antMatchers(HttpMethod.DELETE, "/comments/{commentId}").authenticated()
                .antMatchers(HttpMethod.PUT, "/comments/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/rating/**").permitAll()
                .antMatchers(HttpMethod.POST, "/objects").hasAuthority(Permission.TRADE.getPermission())
                .antMatchers(HttpMethod.PUT, "/objects/{id}").hasAuthority(Permission.TRADE.getPermission())
                .antMatchers(HttpMethod.PUT, "/games/{id}").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/games").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/become_trader").authenticated()
                .antMatchers(HttpMethod.GET, "/comments/unapproved").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/comments/approve").hasAuthority(Permission.WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer)
                .and()
                .logout()
          //      .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");
                //.logoutSuccessUrl("/login");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }



}
