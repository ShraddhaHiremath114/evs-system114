package com.evs.Controller;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.evs.entity.EvStation;
import com.evs.forms.EvForm;
import com.evs.helper.Message;
import com.evs.helper.MessageType;
import com.evs.service.EvService;
import com.evs.service.ImageService;
import com.evs.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EvController {
    @Autowired
    private ImageService imageService;

    Logger logger=LoggerFactory.getLogger(EvController.class);
    @Autowired
    private EvService evService;

    @GetMapping("/admin/addev")
    public String showAddEvForm(Model model) {
        model.addAttribute("EvForm", new EvForm());
        return "addev"; // Name of your Thymeleaf template
    }

    @PostMapping("/admin/do-addev")
    public String doAddEv(@Valid @ModelAttribute("EvForm") EvForm evForm, BindingResult result,HttpSession httpSession) {
        if (result.hasErrors()) {
            return "addev";
        }

        String fileURL=imageService.uploadImage(evForm.getEvimage());
        logger.info("file info:{}",evForm.getEvimage().getOriginalFilename());
        EvStation evStation=new EvStation();
        evStation.setEvname(evForm.getEvname());
        evStation.setAddress(evForm.getAddress());
        evStation.setCity(evForm.getCity());
        evStation.setLatitude(evForm.getLatitude());
        evStation.setLongitude(evForm.getLongitude());
        evStation.setPincode(evForm.getPincode());
        evStation.setState(evForm.getState());
        evStation.setEvimg(fileURL);
        // Add EV station logic
        EvStation saved=evService.saveEvStation(evStation);
        Message message = Message.builder().content("EV Added Successfully!").type(MessageType.green).build();
        httpSession.setAttribute("message", message);
        return "redirect:/admin/addev";
    }
    @GetMapping("/viewev")
    public String viewEV(Model model){
        List<EvStation> list=evService.getAllEvStations();
        model.addAttribute("list",list);
        return "viewEv";
    }

    @GetMapping("/admin/actionev")
    public String actionEv(Model model){
        List<EvStation> list=evService.getAllEvStations();
        model.addAttribute("list",list);
        return "actionev";
    }

    @GetMapping("/admin/deletedata/{id}")
    public String deletedata(@PathVariable("id") int id){
        evService.deleteEvStation(id);
        logger.info("ev {} deleted",id);
        return "redirect:/admin/actionev";
    }

    @GetMapping("/admin/evdata/view/{id}")
    public String viewdata(@PathVariable("id") int id,Model model){
       
       Optional<EvStation> ev=evService.getEvStationById(id);
        EvStation evdata=ev.get();
        EvForm evForm=new EvForm();
        
        evForm.setEvname(evdata.getEvname());
        evForm.setAddress(evdata.getAddress());
        evForm.setCity(evdata.getCity());
        evForm.setPincode(evdata.getPincode());
        evForm.setState(evdata.getState());
        evForm.setLatitude(evdata.getLatitude());
        evForm.setLongitude(evdata.getLongitude());
        evForm.setEvimg(evdata.getEvimg());
        // String img=evdata.getEvimage();
        // evForm.setEvimage(img);
        // evForm.setEvimage(evdata.getEvimage());
       model.addAttribute("EvForm", evForm);
       model.addAttribute("evid", id);
        
        return "upviewev";
    }

    @GetMapping("/nearerloc")
    public String nearerLocation(Model model){
        List<EvStation> list=evService.getAllEvStations();
        model.addAttribute("nearlist",list);
        return "nearloc";
    }

    @GetMapping("/getnearerloc")
    public String getneare(Model model){
        model.addAttribute("list", evService.getAllEvStations());
        return "maindistance";
    }

    @PostMapping("/admin/update/{id}")
public String update(@PathVariable("id") int id, @ModelAttribute EvForm evForm, Model model) {
    // Retrieve the EvStation instance by ID
    Optional<EvStation> optionalEvStation = evService.getEvStationById(id);
    if (optionalEvStation.isPresent()) {
        EvStation evStation = optionalEvStation.get();
        
        // Update the EvStation instance with values from EvForm
        evStation.setEvname(evForm.getEvname());
        evStation.setAddress(evForm.getAddress());
        evStation.setCity(evForm.getCity());
        evStation.setPincode(evForm.getPincode());
        evStation.setState(evForm.getState());
        evStation.setLatitude(evForm.getLatitude());
        evStation.setLongitude(evForm.getLongitude());
        evStation.setEvimg(evForm.getEvimg());

        String fileName=UUID.randomUUID().toString();
        String imgurl=imageService.uploadImage(evForm.getEvimage());
        evStation.setEvimg((imgurl));
        
        // Save the updated EvStation instance
        Optional<EvStation> updatedEvStation = evService.updateEvStationData(evStation);

        // Add the updated EvStation to the model
        model.addAttribute("evStation", updatedEvStation);
        
        // Return the name of the view to be rendered
        return "redirect:/admin/actionev"; // Replace with the actual view name
    } else {
        // Handle the case where the EvStation with the given ID is not found
        model.addAttribute("error", "EV Station not found");
        return "errorView"; // Replace with the actual error view name
    }
}

}
