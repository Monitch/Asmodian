package com.example.demo.Controller;


import com.example.demo.CurrentUser.CurrentUser;
import com.example.demo.DBController.DataBaseController;
import com.example.demo.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String SignUp(Model model){
        model.addAttribute("users",new User());
        return "/SignUp";
    }

    @PostMapping("/SignUp")
    public String getSignUp(@ModelAttribute("users") User user) {
        dataBaseController.registration(user);
        return  "redirect:/MainPage";
    }

    @PostMapping("/SignIn")
    public String getSignIn( @RequestParam("email") String email,
                            @RequestParam("password") String password){
        String lel;
        lel = dataBaseController.logIn(email,password);
        currentUser.setEmail(lel);

        return  "redirect:/MainPage";
    }
    @GetMapping("/MainPage")
    public String MainPAge(Model model){
        model.addAttribute("users", dataBaseController.index());
        model.addAttribute("currentUser",currentUser.getEmail());
        return "/MainPage";
    }
}