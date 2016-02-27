package ru.khadzhinov.bookshelf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/sendmail").fullyAuthenticated()
			.antMatchers("/getshelves").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/email_confirm").permitAll()
			.and()
			.csrf().disable();
	}
}