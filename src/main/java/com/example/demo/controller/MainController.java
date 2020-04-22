package com.example.demo.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.entity.Main;
import com.example.demo.util.AuthenticationUtil;

import org.apache.catalina.connector.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.joran.conditional.ElseAction;

@Controller
public class MainController {

    // @CrossOrigin
    // @RequestMapping("/login")
    // public String login(HttpServletResponse response) {

    //     response.addHeader("Access-Control-Allow-Origin", "*");
    //     response.addHeader("Location", "http://localhost:3000/homepages");
    //     response.setStatus(302);
    //     return ("test");
    // }



    @PostMapping(value = "/login")
    @ResponseBody
    public String index() {
        
        System.out.println("in Login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("in Login2" + authentication.getPrincipal());
        // if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            
        //     System.out.println("user role2");
        // }

        
        



        
        
        return "ROLE_ADMIN=";
    }


//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
// if (!(authentication instanceof AnonymousAuthenticationToken)) {
//    UserDetails userPrincipal = (UserDetails)authentication.getPrincipal(); 
//    System.out.println("User principal name =" + userPrincipal.getUsername()); 
//    System.out.println("Is user enabled =" + userPrincipal.isEnabled());
// }


    // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //  String r= auth.getAuthorities().toString();
    //  System.out.println("the value of role is  "+r);


    

    // }

    // @GetMapping(value = "/login")
    // public String index2() {
    //     System.out.println("in Login get");
    //     System.out.println(SecurityContextHolder.getContext().toString());
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     System.out.println("in Login2 get" + authentication.getPrincipal());
    //     if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
    //         System.out.println("user role2");
    //     }

    //     return "index.html";
    // }

}


