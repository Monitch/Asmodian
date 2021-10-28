package com.example.demo.Controller;


import com.example.demo.CurrentUser.CurrentUser;
import com.example.demo.DBController.DataBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AsmodianController {

    //jdbc:postgresql://localhost:5432/Asmodian db url
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("text", "Some Text");
        return "redirect:/test";
    }

    @GetMapping("/test")
    public String test(Model model){
        //model.addAttribute("name", currentUser.getEmail());
        DataBaseController dataBaseController = new DataBaseController();
        model.addAttribute("name", dataBaseController.getEmail());
        model.addAttribute("email", dataBaseController.getName());
        return "/test";
    }
    @GetMapping("/da")
    public String da(Model model){
        DataBaseController dataBaseController = new DataBaseController();
        dataBaseController.testDB();
        model.addAttribute("name", dataBaseController.getName());
        model.addAttribute("email", dataBaseController.getEmail());
        model.addAttribute("dada", currentUser.getEmail());
        return "/da";
    }
    @PostMapping("/test")
    public String getValue(@RequestParam("email") String email, Model model) {
        currentUser.setEmail(email);
        model.addAttribute("text",currentUser.getEmail());
        return  "redirect:/da";
    }

}