//package com.example.demo;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.*;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		http.httpBasic().and().authorizeRequests()     //例外處理
//		//.antMatchers(HttpMethod.POST,"/student/").hasRole("ADMIN")  //除了POST student其他功能都不可使用
//		.antMatchers("/student/").permitAll()  //允許全部可存取
//		.antMatchers("/css/**", "/index").permitAll()
//		.antMatchers("/acceptance/hw/10811000DMG741D7411023900/Database**").hasRole("ADMIN")
////		.antMatchers("/admin/**").hasRole("USER")
//		.anyRequest().denyAll()    //除了上述條件以外全部擋住
//		.and().csrf().disable();   //關掉跨網站的請求(避免回傳錯誤403)
//		// .and().formLogin().loginPage("/login")
//		// .failureUrl("/login-error").and().exceptionHandling().accessDeniedPage("/");
//
//	}
//
//	@Autowired
//	private DataSource dataSource;
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String password = passwordEncoder.encode("12345");
//		System.out.println(password);
//		
//		auth.jdbcAuthentication().dataSource(dataSource)
//		.usersByUsernameQuery("select std_id , std_password, enabled from student where std_id=?")
//		.authoritiesByUsernameQuery("select std_id, role from student where std_id=?")
//		.passwordEncoder(passwordEncoder);
//					
//		
////		auth.inMemoryAuthentication()     // 驗證資訊存放於記憶體
////        .passwordEncoder(passwordEncoder)
////        .withUser("admin") 
////            .password(passwordEncoder.encode("admin12345678"))
////            .roles("ADMIN", "MEMBER")
////        .and()
////        .withUser("caterpillar")
////            .password(passwordEncoder.encode("12345678"))
////            .roles("MEMBER");
//
//		
//		
//		
//
//		
//		
//		
//		
//		
//	}
//
//}