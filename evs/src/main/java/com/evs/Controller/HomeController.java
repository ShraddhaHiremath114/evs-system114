package com.evs.Controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.evs.entity.User;
import com.evs.forms.UserForm;
import com.evs.helper.Message;
import com.evs.helper.MessageType;
import com.evs.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    Logger logger=LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

    @RequestMapping("/service")
    public String service(){
        return "service";
    }

    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model m){
        UserForm userForm=new UserForm();
        m.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult br,HttpSession session){
        if(br.hasErrors()){
            return "register";
        }
        User user=new User();
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        user.setPhone(userForm.getPhone());

        User savedUser=userService.saveUser(user);
Message message = Message.builder().content("Registration Done Successfully!").type(MessageType.green).build();
        session.setAttribute("message",message);
        return "redirect:/register";
    }

    @RequestMapping("/admin/dashboard")
    public String adminDashboard(){
        return "adminDashboard";
    }

    @RequestMapping("/user/dashboard")
    public String userDashboard() {
        // String name = null;
        // String email = null;

        // if (authentication instanceof OAuth2AuthenticationToken) {
        //     OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        //     name = oauthToken.getPrincipal().getAttributes().get("name").toString();
        //     email = oauthToken.getPrincipal().getAttributes().get("email").toString();
        // } else if (authentication.getPrincipal() instanceof UserDetails) {
            
                
            
        //     UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //     email = userDetails.getUsername();
        //     String uname=userDetails.getUsername();
        //     name="";
        //     for(int i=0;i<uname.length();i++){
        //         if(uname.charAt(i)=='@'){
        //             break;
        //         }
        //         name+=uname.charAt(i);
        //     }
        //     // Assuming you have an email attribute in your UserDetails
        //     // If not, you need to fetch the email from your user service
        //      // Replace with actual logic to get email

        // }

        // logger.info("User logged in: {}", name);
        // logger.info("User email: {}", email);

        // model.addAttribute("username", name);
        return "userDashboard";
    }
    @RequestMapping("/user/profile")
    public String userProfile(){
        return "userProfile";
    }
}
