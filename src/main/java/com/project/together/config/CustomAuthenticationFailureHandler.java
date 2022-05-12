package com.project.together.config;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException, ServletException, IOException {

        String msg = "Invaild Username or Password";

        if(exception instanceof BadCredentialsException){

        }
        else if(exception instanceof InsufficientAuthenticationException){
            msg = "Invalid Secret Key";
        }else if(exception instanceof InternalAuthenticationServiceException) {
            msg = "error.BadCredentials";
        } else if(exception instanceof DisabledException) {
            msg = "error.Disabled";
        } else if(exception instanceof CredentialsExpiredException) {
            msg = "error.CredentialsExpired";
        }



        setDefaultFailureUrl("/users/rejectForm");
        //setDefaultFailureUrl("/login?error=true&exception="+msg);

        super.onAuthenticationFailure(request,response,exception);
    }
}
