package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
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
		 .requestMatchers("/users/**").hasAuthority("Admin")		 
		 .requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin","Editor")
		 .requestMatchers("/products/new","/products/delete/**").hasAnyAuthority("Admin","Editor")
		 .requestMatchers("/products/edit/**", "/products/save", "/products/check_unique").hasAnyAuthority("Admin", "Editor", "Salesperson")
		 .requestMatchers("/products","/products/", "/products/detail/**", "/products/page/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
		 .requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
		 .requestMatchers("/orders",  "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
		 .requestMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Assistant")
		 .requestMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("Admin", "Salesperson")
		 .requestMatchers("/orders_shipper/update/**").hasAuthority("Shipper")
		 .requestMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistant")
		
		 .anyRequest().authenticated()
		 .and()
		 .formLogin()
		 .loginPage("/login")
		 .usernameParameter("email")
		 .permitAll() 
		 .and().logout().permitAll()
		 .and()
		 	.rememberMe()  //after logout cookie will delete 
		 		.key("AbcDefgHijklmnopqrs_1234567890")//this when we restart application then cookies delet thats why by using this cookies not delete
		 			.tokenValiditySeconds(30 * 24 * 60 * 60); //code validate for 30 days
		 ;
		 
		 return http.build();	      
	 } 
	
	 @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**","/webjars/**");
	    }

	 
	 }



