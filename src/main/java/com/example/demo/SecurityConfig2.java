package com.example.demo;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
@Order(2)
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

 public SecurityConfig2() {
        super();
    }
 
 @Override
 protected void configure(final HttpSecurity http) throws Exception {
  InetAddress ip;
  ip = InetAddress.getLocalHost();
  System.out.println(ip.getHostAddress());
  // http
  //  .antMatcher("/login")
  //  .authorizeRequests()
  //   .anyRequest().hasRole("USER")
  //   .and()
  //  .httpBasic()
  //  .and().rememberMe().tokenValiditySeconds(600).and().cors();
  
  http
   .antMatcher("/student/**")
   .authorizeRequests()
    .anyRequest().hasRole("USER")
    .and()
   .httpBasic()
   .and().rememberMe().tokenValiditySeconds(600).and().cors()
   .and().csrf().disable();
   //.and()
   //.authorizeRequests()
   // .anyRequest().authenticated()
   // .antMatchers("/student/**").hasRole("USER")
    //.and()
   //.formLogin().loginPage("/login").defaultSuccessUrl("http://localhost:3000/homepages").failureUrl("http://localhost:3000").permitAll()
   // .and()
   // .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("http://localhost:3000");


  // http.httpBasic().and().authorizeRequests()     //例外處理
  // .antMatchers("/css/**", "/index", "/logintest").permitAll()
  // .antMatchers("/teacher/**").hasRole("ADMIN")
  // .antMatchers("/student/**").hasRole("USER")
  // .and().rememberMe().tokenValiditySeconds(600).and().cors()
  // // .anyRequest().denyAll()    //除了上述條件以外全部擋住
  // .and().csrf().disable()  //關掉跨網站的請求(避免回傳錯誤403
  // .formLogin().loginPage("/login").defaultSuccessUrl("http://localhost:3000/homepages").failureUrl("http://localhost:3000").permitAll()
  // .and()
  // .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("http://localhost:3000");
  
  // .formLogin().disable();

 }

 @Autowired
 private DataSource dataSource;

 @Autowired
 public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
  final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  final String password = passwordEncoder.encode("12345");
  System.out.println(password);
  
  auth.jdbcAuthentication().dataSource(dataSource)
  .usersByUsernameQuery("select std_id , std_password, enabled from student where std_id=?")
  .authoritiesByUsernameQuery("select std_id, role from student where std_id=?")
  .passwordEncoder(passwordEncoder);

  
 }

}

// @EnableWebSecurity
// public class SecurityConfig2 {
//  @Autowired
//  private DataSource dataSource;
   
//  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
//   final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//   final String password = passwordEncoder.encode("12345");
//   System.out.println(password);
  
//   auth.jdbcAuthentication().dataSource(dataSource)
//   .usersByUsernameQuery("select std_id , std_password, enabled from student where std_id=?")
//   .authoritiesByUsernameQuery("select std_id, role from student where std_id=?")
//   .passwordEncoder(passwordEncoder);
//  }
//   @Configuration
//   @Order(1)                                                        
//   public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//       protected void configure(HttpSecurity http) throws Exception {
//           http
//               .antMatcher("/student/**")                               
//               .authorizeRequests()
//                   .anyRequest().hasRole("USER")
//                   .and()
//               .httpBasic();
//       }
//   }    

//   @Configuration                                                   
//   public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

//       @Override
//       protected void configure(final HttpSecurity http) throws Exception {
//           http
//               .authorizeRequests()
//                   .anyRequest().authenticated()
//                   .and()
//             .formLogin().loginPage("/login").defaultSuccessUrl("http://localhost:3000/homepages").failureUrl("http://localhost:3000").permitAll()
//    .and()
//    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("http://localhost:3000");

//       }
//   }
// }