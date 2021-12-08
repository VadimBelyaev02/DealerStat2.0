package com.leverx.dealerstat.config;

import com.leverx.dealerstat.entity.Permission;
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
                .antMatchers(HttpMethod.GET, "/api/*").hasAuthority(Permission.READ.getPermission())

                .antMatchers(HttpMethod.DELETE, "/api/objects/*").hasAuthority(Permission.TRADE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/objects/*").hasAuthority(Permission.TRADE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/objects/*").hasAuthority(Permission.TRADE.getPermission())

                .antMatchers(HttpMethod.POST, "/api/deals/*").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/deals/*").hasAuthority(Permission.UPDATE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/deals/*").hasAuthority(Permission.DELETE.getPermission())

                .antMatchers(HttpMethod.POST, "/api/games/*").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/games/*").hasAuthority(Permission.UPDATE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/games/*").hasAuthority(Permission.DELETE.getPermission())

                .antMatchers(HttpMethod.POST, "/api/comments/*").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/comments/*").hasAuthority(Permission.READ.getPermission())
                // approve?
                .antMatchers(HttpMethod.DELETE, "/api/comments/*").hasAuthority(Permission.READ.getPermission())

                .antMatchers(HttpMethod.PUT, "/api/users/*").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/users/*").hasAuthority(Permission.READ.getPermission())

                .antMatchers(HttpMethod.PUT, "/api/auth/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/*").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID");
//                .and()
//                .exceptionHandling().accessDeniedHandler(as)
//                .logoutSuccessUrl("/login");
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
