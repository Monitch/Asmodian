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

    CurrentUser currentUser = new CurrentUser();
    DataBaseController dataBaseController = new DataBaseController();

    @RequestMapping("/")
    public String index() {
        return "redirect:/SignIn";
    }
    @GetMapping("/SignIn")
    public String SignIn(){
        return "/SignIn";
    }
    @GetMapping("/SignUp")
    public String SignUp(){
        return "/SignUp";
    }

    @PostMapping("/SignUp")
    public String getSignUp( @RequestParam("email") String email,
                             @RequestParam("password") String password){
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        return  "redirect:/MainPage";
    }

    @PostMapping("/SignIn")
    public String getSignIn( @RequestParam("email") String email,
                            @RequestParam("password") String password){
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        return  "redirect:/MainPage";
    }
    @GetMapping("/MainPage")
    public String MainPAge(Model model){
        model.addAttribute("users", dataBaseController.index());
        return "/MainPage";
    }
}