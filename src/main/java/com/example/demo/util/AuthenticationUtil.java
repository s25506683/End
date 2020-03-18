package com.example.demo.util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthenticationUtil{

    private Authentication authentication;


    public AuthenticationUtil(){
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUserName(){
        return authentication.getName();
    }




}