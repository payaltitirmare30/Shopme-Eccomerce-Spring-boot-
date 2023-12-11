package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalAuthentication
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}  
	
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(PasswordEncoder());
		
		return authProvider;
	}	
	
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 .requestMatchers("/customer").authenticated()
		 .anyRequest().permitAll()
		 .and()
		 .formLogin()
		   .loginPage("/login")
		   .usernameParameter("email")
		   .permitAll()
		   .and()
		   .logout().permitAll()
		   .and()
		   .rememberMe()  //after logout cookie will delete 
	 		.key("AbcDefgHijklmnopqrs_1234567890")//this when we restart application then cookies delet thats why by using this cookies not delete
	 			.tokenValiditySeconds(30 * 24 * 60 * 60); //code validate for 30 days
		 
		 
		 return http.build();	      
	 } 
	
	    @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**","/webjars/**");
	    } 
	   
	 }



