package com.ums.config;

import com.ums.service.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
                .authorizeRequests()
                .antMatchers("/signin.xhtml","/signup.xhtml", "/signin.jsf","/signup.jsf",
                        "/javax.faces.resource/**", "/error", "/resources/**", "/static/**",
                        "/css/**", "/js/**", "/images/**", "/fonts/**", "/vendor/**", "/h2-console/**").permitAll()
                .antMatchers("/products.xhtml", "/products.jsf").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin.jsf")
                //.loginProcessingUrl("/loginUser")
                .defaultSuccessUrl("/homepage.jsf")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/signin.jsf?error=true"))
                //.failureUrl("signin.jsf?error=true")
                .and()
                .logout()
                //.logoutUrl("/logoutUser")
                .logoutSuccessUrl("/signin.jsf?success=true");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()); // if using encoded passwords
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = passwordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("user").password("pass123").roles("USER");
//    }

}
