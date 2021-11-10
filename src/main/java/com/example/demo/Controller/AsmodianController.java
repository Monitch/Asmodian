package com.example.demo.Controller;


import com.example.demo.CurrentUser.CurrentUser;
import com.example.demo.DBController.DataBaseController;
import com.example.demo.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class AsmodianController {

    CurrentUser currentUser = new CurrentUser();
    DataBaseController dataBaseController = new DataBaseController();

    @RequestMapping("/")
    public String index() {
        return "redirect:/SignIn";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", dataBaseController.findById(id));
        return "/UserShow";
    }

    @GetMapping("/MeetingPage")
    public String MeetingPage(Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute(
                "meetingList",dataBaseController.getAllMeetingOfCurrentUser(currentUser.getEmail()));
        return "/MeetingPage";
    }

    @GetMapping("/AddMeeting")
    public String AddMeeting(Model model) {
        model.addAttribute("test", "Future addingpage");
        return "/AddMeeting";
    }
    @GetMapping("/ApproveMeeting")
    public String ApproveMeeting(Model model){
        model.addAttribute("test","ApprovingMeeting");
        return "/ApproveMeeting";
    }

    @GetMapping("/SignIn")
    public String SignIn() {
        return "/SignIn";
    }

    @GetMapping("/UserList")
    public String UserList(Model model) {
        model.addAttribute("users", dataBaseController.index());
        return "/UserList";
    }

    @GetMapping("/SignUp")
    public String SignUp(Model model) {
        ArrayList<String> doctorList;
        doctorList = dataBaseController.getAllDoctor();
        model.addAttribute("users", new User());
        model.addAttribute("doctorList", doctorList);
        return "/SignUp";
    }

    @PostMapping("UserList")
    public String UserListPost(@RequestParam("name") String name,
                               Model model) {
        model.addAttribute("users", dataBaseController.findByName(name));
        return "UserList";

    }

    @PostMapping("/SignUp")
    public String getSignUp(@ModelAttribute("users") User user) {
        dataBaseController.registration(user);
        currentUser.setEmail(user.getEmail());
        currentUser.setDoctor(user.getDoctor());
        return "redirect:/MainPage";
    }

    @PostMapping("/SignIn")
    public String getSignIn(@RequestParam("email") String email,
                            @RequestParam("password") String password) {
        currentUser = dataBaseController.logIn(email, password);
        return "redirect:/MainPage";
    }

    @GetMapping("/MainPage")
    public String MainPAge(Model model,
                           RedirectAttributes redirectAttributes) {
        model.addAttribute("users", dataBaseController.index());
        model.addAttribute("currentUser", currentUser);
        if (!currentUser.getEmail().equals("DADA"))
            return "/MainPage";
        else {
            redirectAttributes.addFlashAttribute(
                    "redirect", "Введені данні не правильні");
            return "redirect:/SignIn";
        }
    }
}