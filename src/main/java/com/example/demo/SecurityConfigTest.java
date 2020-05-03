// package com.example.demo;

// import java.net.InetAddress;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

// import javax.sql.DataSource;

// import org.apache.catalina.User;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.*;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.UserDetailsService;


// @Configuration
// @EnableWebSecurity
// public class SecurityConfigTest {
    
// @Configuration
// @Order(1)
// public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
//     public App1ConfigurationAdapter() {
//         super();
//     }
 
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.antMatcher("/teacher/**")
//           .authorizeRequests()
//           .anyRequest()
//           .hasRole("ADMIN")
           
//           .and()
//           .formLogin()
//           .loginProcessingUrl("/login_t")
//           .failureUrl("http://localhost:3000")
//           .defaultSuccessUrl("http://localhost:3000/homepaget", true)
           
//           .and()
//           .logout()
//           .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//           .logoutSuccessUrl("http://localhost:3000")
           
//           .and()
//           .exceptionHandling()
//           .accessDeniedPage("/403")
           
//           .and()
//           .csrf().disable();
//     }
// }


// @Configuration
// @Order(2)
// public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
 
//     public App2ConfigurationAdapter() {
//         super();
//     }
 
//     protected void configure(HttpSecurity http) throws Exception {
//         http.antMatcher("/student/**")
//           .authorizeRequests()
//           .anyRequest()
//           .hasRole("USER")
           
//           .and()
//           .formLogin()
//           .loginProcessingUrl("/login_s")
//           .failureUrl("http://localhost:3000")
//           .defaultSuccessUrl("http://localhost:3000/homepages", true)
           
//           .and()
//           .logout()
//           .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//           .logoutSuccessUrl("http://localhost:3000")
          
//           .and()
//           .exceptionHandling()
//           .accessDeniedPage("/403")
           
//           .and()
//           .csrf().disable();
//     }
// }


// @Autowired
//  private DataSource dataSource;

//  @Autowired
//  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
//   final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//   final String password = passwordEncoder.encode("12345");
//   System.out.println(password);
  
//   auth.jdbcAuthentication().dataSource(dataSource)
//   .usersByUsernameQuery("select std_id , std_password, enabled from student where std_id=?")
//   .authoritiesByUsernameQuery("select std_id, role from student where std_id=?")
//   .passwordEncoder(passwordEncoder);

//   auth.jdbcAuthentication().dataSource(dataSource)
//   .usersByUsernameQuery("select teacher_id , teacher_password, enabled from teacher where teacher_id=?")
//   .authoritiesByUsernameQuery("select teacher_id, role from teacher where teacher_id=?")
//   .passwordEncoder(passwordEncoder);

  
//  }


// }