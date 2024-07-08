package com.evs.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.evs.dao.UserDao;
import com.evs.entity.Provider;
import com.evs.entity.User;
import com.evs.helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserDao userDao;

    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthenticationSuccessHandler");

        var oauth2Token = (OAuth2AuthenticationToken) authentication;
        String clientId = oauth2Token.getAuthorizedClientRegistrationId();
        logger.info("Client ID: " + clientId);

        var user = (DefaultOAuth2User) oauth2Token.getPrincipal();
        String email = user.getAttribute("email") != null ? user.getAttribute("email").toString() : null;
        String name = user.getAttribute("name") != null ? user.getAttribute("name").toString() : null;
        String picture = user.getAttribute("picture") != null ? user.getAttribute("picture").toString() : null;

        // if(clientId.equalsIgnoreCase("google")){
        //     //google

        // }else if(clientId.equalsIgnoreCase("github")){

        // }else{
        //     logger.info("OAuthenticationSuccessHandler:Unkonwn provider");
        // }

        

        if (email != null && name != null) {
            // Create user and save in db
            User u = new User();
            u.setEmail(email);
            u.setName(name);
            u.setProfilePic(picture);
            u.setProvider(clientId.equalsIgnoreCase("google") ? Provider.GOOGLE : Provider.GITHUB);
            u.setEnabled(true);
            u.setRoleList(List.of(AppConstants.ROLE_USER));

            User existingUser = userDao.findByEmail(email).orElse(null);
            if (existingUser == null) {
                userDao.save(u);
                logger.info("User Saved: " + email);
            } else {
                logger.info("User already exists: " + email);
            }
        } else {
            logger.error("One or more attributes are null: email=" + email + ", name=" + name);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required user attributes are missing");
            return;
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }
}
