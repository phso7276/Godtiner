package com.godtiner.api.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godtiner.api.domain.member.repository.MemberRepository;
import com.godtiner.api.domain.member.service.LoginFailureHandler;
import com.godtiner.api.domain.member.service.LoginService;
import com.godtiner.api.domain.member.service.LoginSuccessJWTProvideHandler;
import com.godtiner.api.global.jwt.service.JwtService;
import com.godtiner.api.global.login.filter.JsonUsernamePasswordAuthenticationFilter;
import com.godtiner.api.global.login.filter.JwtAuthenticationProcessingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Import(BeanValidatorPluginsConfiguration.class) // 1
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()) // 2
                .select() // 3
                .apis(RequestHandlerSelectors.basePackage("com.godtiner.api"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKey())) // 4
                .securityContexts(List.of(securityContext())); // 5
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("KUKE market")
                .description(" market REST API Documentation")
                .license("gmlwo308@gmail.com")
                .licenseUrl("https://github.com/songheejae/kuke-market")
                .version("1.0")
                .build();
    }

    private static ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer Token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .operationSelector(oc -> oc.requestMappingPattern().startsWith("/")).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "global access");
        return List.of(new SecurityReference("Authorization", new AuthorizationScope[] {authorizationScope}));
    }

    @Configuration
    @RequiredArgsConstructor
    public static class SecurityConfig {

        private final LoginService loginService;
        private final ObjectMapper objectMapper;
        private final MemberRepository memberRepository;
        private final JwtService jwtService;


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .formLogin().disable()//1 - formLogin 인증방법 비활성화
                    .httpBasic().disable()//2 - httpBasic 인증방법 비활성화(특정 리소스에 접근할 때 username과 password 물어봄)
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    .authorizeRequests()
                    .antMatchers("/member/**","/feed/**",
                            "/signIn", "/signUp","/myRoutine/**","sharedRoutine/**","/tags","/").permitAll()
                    .anyRequest().permitAll();

                //.anyRequest().authenticated();

            http.addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class);
            http.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class);

            http.cors();

            return http.build();
        }

       @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {

           return (web) ->  web.ignoring().mvcMatchers("/exception/**",
                   "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**");

        }

       @Bean
       public PasswordEncoder passwordEncoder(){
           return PasswordEncoderFactories.createDelegatingPasswordEncoder();
       }
       @Bean
       public AuthenticationManager authenticationManager() {//2 - AuthenticationManager 등록
           DaoAuthenticationProvider provider = new DaoAuthenticationProvider();//DaoAuthenticationProvider 사용
           provider.setPasswordEncoder(passwordEncoder());//PasswordEncoder로는 PasswordEncoderFactories.createDelegatingPasswordEncoder() 사용
           provider.setUserDetailsService(loginService); //이후 작성할 코드입니다.
           return new ProviderManager(provider);
       }
        @Bean
        public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
            return new LoginSuccessJWTProvideHandler(jwtService, memberRepository);
        }

        @Bean
        public LoginFailureHandler loginFailureHandler(){
            return new LoginFailureHandler();
        }


        @Bean
        public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter(){
            JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
            jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
            jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
            jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
            return jsonUsernamePasswordLoginFilter;
        }

        @Bean
        public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
            JwtAuthenticationProcessingFilter jsonUsernamePasswordLoginFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);

            return jsonUsernamePasswordLoginFilter;
        }

    /*    @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("https://localhost:3000"));
            configuration.setAllowedMethods(Arrays.asList("GET","POST"));
            // you can configure many allowed CORS headers

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }*/

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.addAllowedOrigin("http://localhost:3000");
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*");
            configuration.addExposedHeader("*");
            configuration.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }
}