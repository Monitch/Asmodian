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
    @GetMapping("/SignIn")
    public String SignIn() {
        return "/SignIn";
    }

    @GetMapping("/SignUp")
    public String SignUp(Model model) {
        ArrayList<String> doctorList;
        doctorList = dataBaseController.getAllDoctor();
        model.addAttribute("users", new User());
        model.addAttribute("doctorList", doctorList);
        return "/SignUp";
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
    @GetMapping("/UserList")
    public String UserList(Model model) {
        model.addAttribute("users", dataBaseController.index());
        return "/UserList";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", dataBaseController.findById(id));
        return "/UserShow";
    }
    @GetMapping("/MeetingPage")
    public String MeetingPage(Model model) {
        model.addAttribute("currentUser", currentUser);
        if (currentUser.getDoctor().equals("doctor")){
            model.addAttribute(
                    "meetingList",dataBaseController.getAllMeetingOfCurrentDoctor(currentUser.getName()));
        }
        if (!currentUser.getDoctor().equals("doctor")) {
            model.addAttribute(
                    "meetingList", dataBaseController.getAllMeetingOfCurrentUser(currentUser.getEmail()));
        }

        return "/MeetingPage";
    }

    @GetMapping("/AddMeeting")
    public String AddMeeting(Model model) {
        return "/AddMeeting";
    }

    @PostMapping("/SignUp")
    public String getSignUp(@ModelAttribute("users") User user) {
        dataBaseController.registration(user);
        currentUser.setName(user.getName());
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

    @PostMapping("UserList")
    public String UserListPost(@RequestParam("name") String name,
                               Model model) {
        model.addAttribute("users", dataBaseController.findByName(name));
        return "UserList";
    }

    @PostMapping("MeetingPage")
    public String approveMeeting(@RequestParam(value="action", required=true) String action,
                                 @RequestParam (value = "id",required = true) int id){
        if (action.equals("save")) {
            dataBaseController.UpdateMeeting("true",id);
        }

        if (action.equals("cancel")) {
            dataBaseController.UpdateMeeting("false",id);
        }
        return "redirect:/MeetingPage";
    }
    @PostMapping("/AddMeeting")
    public String AddNewMeeting(@RequestParam ("data") String date){
        dataBaseController.addNewMeeting(currentUser.getName(),
                currentUser.getEmail(),
                currentUser.getDoctor(),
                date);

        return "redirect:/MeetingPage";
    }
}