package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

	public SecurityConfig2() {
        super();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.antMatcher("/acceptance/hw/10811000DMG741D7411023900/Database**")
//		.authorizeRequests()
//		.anyRequest()
//		.hasRole("ADMIN")
//		
//		.and()
//        .formLogin()
//        .loginPage("/loginAdmin")
//        .loginProcessingUrl("/admin_login")
//        .failureUrl("/loginAdmin?error=loginError")
//        .defaultSuccessUrl("/adminPage")
//		
//		.and()
//        .logout()
//        .logoutUrl("/admin_logout")
//        .logoutSuccessUrl("/protectedLinks")
//        .deleteCookies("JSESSIONID")
//        
//        .and()
//        .exceptionHandling()
//        .accessDeniedPage("/403")
//         
//        .and()
//        .csrf().disable();
		
		
		
		http.httpBasic().and().authorizeRequests()     //例外處理
        .antMatchers("/css/**", "/index").permitAll()
		.antMatchers("/teacher/").hasRole("ADMIN")
		.antMatchers("/student/").hasRole("USER")
//		.anyRequest().denyAll()    //除了上述條件以外全部擋住
		.and().csrf().disable();   //關掉跨網站的請求(避免回傳錯誤403)
		
		
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("12345");
		System.out.println(password);
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select std_id , std_password, enabled from student where std_id=?")
		.authoritiesByUsernameQuery("select std_id, role from student where std_id=?")
		.passwordEncoder(passwordEncoder);


		
					

		
	// 	auth.inMemoryAuthentication()     // 驗證資訊存放於記憶體
    //    .passwordEncoder(passwordEncoder)
    //    .withUser("admin") 
    //        .password(passwordEncoder.encode("12345"))
    //        .roles("ADMIN", "USER")
    //    .and()
    //    .withUser("user")
    //        .password(passwordEncoder.encode("12345"))
    //        .roles("USER");

		
		
		

		
		
		
		
		
	}

}