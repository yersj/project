package com.cisco.cisco.config;

import com.cisco.cisco.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        public UserServiceImpl userService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService);
        }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedPage("/accessError");

        http.authorizeRequests()
                .antMatchers("/css/**","/jss/**").permitAll();

        http.formLogin()
                .loginProcessingUrl("/toEnter").permitAll()  // <- where to send post request of sign in
                .loginPage("/signin").permitAll()            // <- which page is default page to sign in (from where to send request)
                .usernameParameter("user_email")             // <- input type email name="user_email"
                .passwordParameter("user_password")          // <- input type password name="user_password
                .defaultSuccessUrl("/profilePage    ")               // <- where to redirect after success sign in process
                .failureUrl("/signin?error");                // <- where to redirect if unsuccessful sign in
        http.logout()
                .logoutSuccessUrl("/signin")                 // <- after log out button
                .logoutUrl("/toExit");                        // <- url endpoint to sign auth
        http.csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
