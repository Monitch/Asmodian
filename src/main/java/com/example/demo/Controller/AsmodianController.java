package com.example.demo.Controller;


import com.example.demo.CurrentUser.CurrentUser;
import com.example.demo.DBController.DataBaseController;
import com.example.demo.Model.Disease;
import com.example.demo.Model.ListForDropDown;
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

    @GetMapping("/NewDoctor")
    public String newDoctor(){
        return "NewDoctor";
    }
    @GetMapping("/SignUp")
    public String SignUp(Model model) {
        ArrayList<ListForDropDown> doctorList;
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
    @GetMapping("/AddInformation")
    public String addInformation(Model model){
        model.addAttribute("test","future information");
        return "/AddInformation";
    }
    @GetMapping("/AboutDoctor")
    public String DoctorInformation(Model model){
        model.addAttribute("test","future doctor information");
        return "/AboutDoctor";
    }
    @GetMapping("MyDisease")
    public String MyDisease(Model model){
        model.addAttribute("info",dataBaseController.getDisease(currentUser.getNumber()));
        return "/MyDisease";
    }
    @GetMapping("/UserList")
    public String UserList(Model model) {
        model.addAttribute("users", dataBaseController.index());
        return "/UserList";
    }
    @GetMapping ("/SetDisease")
    public String setDisease(Model model){
        model.addAttribute("test", "Діагноз тест");
        ArrayList<ListForDropDown> patientList;
        patientList = dataBaseController.getAllPatient(currentUser.getName());
        model.addAttribute("patientList", patientList);
        return "/SetDisease";
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
        currentUser.setNumber(user.getNumber());
        currentUser.setDoctor(user.getDoctor());
        return "redirect:/MainPage";
    }
    @PostMapping("/NewDoctor")
    public String setNewDoctor(@RequestParam("key") String key ,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("number") String number,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes){
        System.out.println(key+name+number+password);
        if(key.equals("Winter")) {

            currentUser.setName(name);
            currentUser.setEmail(email);
            currentUser.setNumber(number);
            currentUser.setDoctor("doctor");
        return "redirect:/MainPage";}
        else {
            redirectAttributes.addFlashAttribute(
                    "redirect", "Введений ключ не правильний");
            return "redirect:/NewDoctor";
        }
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
    @PostMapping("/SetDisease")
    public String setDiseaseForPatient(@RequestParam("numberPatient")  String numberPatient,
                                       @RequestParam("disease") String disease,
                                       @RequestParam("medicine") String medicine){
        Disease disease1 = new Disease();
        disease1.setName(dataBaseController.getUserByNumber(numberPatient));
        disease1.setNumber(numberPatient);
        disease1.setDoctor(currentUser.getName());
        disease1.setDisease(disease);
        disease1.setMedicine(medicine);
        dataBaseController.setDisease(disease1);
        return "redirect:/MainPage";
    }
}