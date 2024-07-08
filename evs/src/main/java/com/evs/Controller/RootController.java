package com.evs.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    Logger logger=LoggerFactory.getLogger(HomeController.class);
    @ModelAttribute
public void getLoggedInUserDetails(Authentication authentication, Model model) {
    if(authentication==null){
        return;
    }
        String name = null;
        String email = null;

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            name = oauthToken.getPrincipal().getAttributes().get("name").toString();
            email = oauthToken.getPrincipal().getAttributes().get("email").toString();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            
                
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
            String uname=userDetails.getUsername();
            name="";
            for(int i=0;i<uname.length();i++){
                if(uname.charAt(i)=='@'){
                    break;
                }
                name+=uname.charAt(i);
            }
            // Assuming you have an email attribute in your UserDetails
            // If not, you need to fetch the email from your user service
             // Replace with actual logic to get email

        }

        logger.info("User logged in: {}", name);
        logger.info("User email: {}", email);

        model.addAttribute("username", name);
        model.addAttribute("email", email);
    }
}
