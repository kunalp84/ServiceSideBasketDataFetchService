package com.basket.app.config;


import com.basket.app.filter.JwtRequestFilter;
import org.jasypt.springsecurity2.providers.encoding.PBEPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurationBasicAuth extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
       // Map encoders = new HashMap<>();
       // encoders.put("jasypt", new StrongPasswordEncoder());
        //PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("jasypt", encoders);
          auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(10));

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/profileController/profileDataAdd")
                .permitAll()
                //loginCheckService
                .antMatchers(HttpMethod.POST,"/loginCheckService")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/actuator/**")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/**")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/ScholarBasket/VerifyAccount**")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/otpController/getOtp/**@**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/otpController/passwordReset")
                .permitAll().
                  antMatchers("/authenticate").permitAll()

                .anyRequest().authenticated()

                .and()   // now Jwt auth
                .exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);




             // .formLogin().failureHandler(customAuthenticationFailureHandler()).loginPage("/Login").permitAll();
         /*     this line for basic auth    .httpBasic().realmName("MY_TEST_REALM").authenticationEntryPoint(getBasicAuthEntryPoint());
         *
         * /
          */
               // http.formLogin().failureHandler(customAuthenticationFailureHandler());

        //Jwt auth filter to HTTP requests
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }


    /*
    *
    *
    * 	httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().
						anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    *
    *
    * */

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        //FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        //bean.setOrder(0);
        //return bean;
        return new CorsFilter(source);
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
