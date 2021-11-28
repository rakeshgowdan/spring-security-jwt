package com.rakesh.securityjwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rakesh.securityjwt.service.UserDetailServiceHandler;

@Configuration
@EnableWebSecurity
public class JWTConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailServiceHandler userDetailServiceHandler;
	
	//using this method, we specify what is the auth imple type
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailServiceHandler);
	}

	
	//using this method, we control which end points are permitted & not permitted 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
				.csrf() 			
				//Cross-Site Request Forgery (CSRF) is an attack that forces authenticated users to submit a request to a Web application against which they are currently authenticated.
				.disable()
				.cors() 	 
				//Cross-origin resource sharing is a mechanism that allows restricted resources on a web page to be requested from another domain outside the domain from which the first resource was served
				.disable()
				.authorizeRequests()
				.antMatchers("/api/auth/userAuthenticate")  //only allow this endpoint with out authentication
				.permitAll()
				.anyRequest().authenticated()   //for any other request authentication is mandate 
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // every request should be independent & server need not to manage session
	}

	@Bean
	public PasswordEncoder passwordEncode() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authManager() throws Exception {
		return super.authenticationManagerBean();
	}
}
