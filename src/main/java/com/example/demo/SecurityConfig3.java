package com.example.demo;

import java.net.InetAddress;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig3 extends WebSecurityConfigurerAdapter {

 public SecurityConfig3() {
        super();
    }
 
 @Override
 protected void configure(HttpSecurity http) throws Exception {
  InetAddress ip;
  ip = InetAddress.getLocalHost();
  System.out.println(ip.getHostAddress());
  


  http.httpBasic().and().authorizeRequests()     //例外處理
  .antMatchers("/css/**", "/index", "/logintest").permitAll()
  //.antMatchers("/teacher/downloadExcel/Rollcall/**").permitAll()
  .antMatchers("/teacher/**").hasRole("ADMIN")
  .and().rememberMe().tokenValiditySeconds(600).and().cors()
  // .anyRequest().denyAll()    //除了上述條件以外全部擋住
  .and().csrf().disable() //關掉跨網站的請求(避免回傳錯誤403
  .formLogin().loginPage("/login").defaultSuccessUrl("https://rollsup.im.fju.edu.tw:3000/loading").failureUrl("https://rollsup.im.fju.edu.tw:3000/").permitAll()
  .and()
  .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("https://rollsup.im.fju.edu.tw:3000/");
  
  
 }

 @Autowired
 private DataSource dataSource;

 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  String password = passwordEncoder.encode("12345");
  System.out.println(password);
  
  auth.jdbcAuthentication().dataSource(dataSource)
  .usersByUsernameQuery("select teacher_id , teacher_password, enabled from teacher where teacher_id=?")
  .authoritiesByUsernameQuery("select teacher_id, role from teacher where teacher_id=?")
  .passwordEncoder(passwordEncoder);
     
  
  
  
 }

}

