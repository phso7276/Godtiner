package org.zerock.jpaweb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.jpaweb.handler.ApiLoginFailHandler;
import org.zerock.jpaweb.handler.AuthSuccessHandler;
import org.zerock.jpaweb.security.filter.ApiCheckFilter;
import org.zerock.jpaweb.security.filter.ApiLoginFilter;
import org.zerock.jpaweb.security.service.MemberDetailsService;
import org.zerock.jpaweb.security.util.JWTUtil;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private MemberDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("11111"))
                .roles("USER")
                .build();

        log.info("userDetailsService");
        log.info(user);

        return new InMemoryUserDetailsManager(user);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //AuthenticationManager 설정
       AuthenticationManagerBuilder authenticationManagerBuilder = http.
                getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.
                userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

  /*     http.authorizeHttpRequests((auth)->{
            auth.antMatchers("/api/login").permitAll();
            auth.antMatchers("/sample/member").hasRole("USER");
            auth.antMatchers("/member/**").hasAnyAuthority("USER");
        });*/

        //반드시 필요
        http.authenticationManager(authenticationManager);

        http.formLogin();
           //     .defaultSuccessUrl("/sample/member");
        http.csrf().disable();
/*        http.cors().disable();*/
        http.logout();

        http.oauth2Login()
                .successHandler(successHandler());
                //.defaultSuccessUrl("/sample/member");

        http.rememberMe()
                .tokenValiditySeconds(60*60*24*7)
                .userDetailsService(userDetailsService);
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager)
            throws Exception{

        ApiLoginFilter apiLoginFilter= new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        apiLoginFilter
                .setAuthenticationFailureHandler(new ApiLoginFailHandler());

        return apiLoginFilter;
    }
    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public AuthSuccessHandler successHandler() {
        return new AuthSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter(){

        return new ApiCheckFilter("/notes/**/**",jwtUtil());
    }




}
