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

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

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
    @GetMapping("/Statistic")
    public String Statistic(Model model){
        DecimalFormat f = new DecimalFormat("##.00");
        String allCount = dataBaseController.getCountOfPatientOfCurrentUser(currentUser.getName());
        String sickCount = dataBaseController.getCountOfSick(currentUser.getName());
        String popularIllCount = dataBaseController.getMostPopularIllCount(currentUser.getName());
        String covidCount = dataBaseController.getCountOfSickOfCOVid(currentUser.getName());
        double percentOfSick = (Double.parseDouble(sickCount)/Double.parseDouble(allCount))*100;
        double percentOfPopular = (Double.parseDouble(popularIllCount)/Double.parseDouble(sickCount))*100;
        double percentOfCovid = (Double.parseDouble(covidCount)/Double.parseDouble(sickCount))*100;

        model.addAttribute("cPatient",allCount);
        model.addAttribute("cSick",sickCount);
        model.addAttribute("PopularIll",dataBaseController.getMostPopularIll(currentUser.getName()));
        model.addAttribute("cCovid",covidCount);

        model.addAttribute("pSick",f.format(percentOfSick));
        model.addAttribute("pPopular",f.format(percentOfPopular));
        model.addAttribute("pCovid",f.format(percentOfCovid));

        return "/Statistic";
    }
    @GetMapping("/AddInformation")
    public String addInformation(Model model){
        model.addAttribute("test","future information");
        return "/AddInformation";
    }
    @GetMapping("/AboutDoctor")
    public String DoctorInformation(Model model){
        String check="";
        if (currentUser.getDoctor().equals("doctor")){
            model.addAttribute("doctorInfo", dataBaseController.getInfoAboutDoctor(currentUser.getName()));
            check= String.valueOf(dataBaseController.getInfoAboutDoctor(currentUser.getName()).getEmail()==null);
            model.addAttribute("check",check);
        }
        if (!currentUser.getDoctor().equals("doctor")){
            model.addAttribute("doctorInfo", dataBaseController.getInfoAboutDoctor(currentUser.getDoctor()));
            check= String.valueOf(dataBaseController.getInfoAboutDoctor(currentUser.getDoctor()).getEmail()==null);
            model.addAttribute("check",check);
        }
        return "/AboutDoctor";
    }
    @GetMapping("MyDisease")
    public String MyDisease(Model model){
        model.addAttribute("info",dataBaseController.getDisease(currentUser.getNumber()));
        return "/MyDisease";
    }
    @GetMapping("/UserList")
    public String UserList(Model model) {
        model.addAttribute("users", dataBaseController.findAllPatientOfUser(currentUser.getName()));
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
        User user = new User();
        user=dataBaseController.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("disease", dataBaseController.getDisease(user.getNumber()));
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
            dataBaseController.registrationDoctor(name,email,number,password);
        return "redirect:/MainPage";
        }
        else {
            redirectAttributes.addFlashAttribute(
                    "redirect", "Введений ключ не правильний");
            return "redirect:/NewDoctor";
        }
    }

    @PostMapping("/SignIn")
    public String getSignIn(@RequestParam("email") String email,
                            @RequestParam("password") String password) {
        //currentUser = dataBaseController.logIn("Sergiy@gmail.com", "qweasdzxc");
        currentUser = dataBaseController.logIn("Monich17v@gmail.com", "qwe123asd");
        return "redirect:/MainPage";
    }
    @PostMapping("AddInformation")
    public String setInformation(@RequestParam("education") String education,
                                 @RequestParam("experience") String experience,
                                 @RequestParam("awards") String awards,
                                 @RequestParam("aboutme")String aboutme){
        String check = dataBaseController.checkInfoAbout(currentUser.getName());
        if(check == null) {
            dataBaseController.setInfo(currentUser.getName(),
                    currentUser.getEmail(),
                    currentUser.getNumber(),
                    education, experience,
                    awards,
                    aboutme);
        }
        else {
            dataBaseController.updateInfo(currentUser.getName(), education, experience, awards, aboutme);
        }
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
            dataBaseController.updateMeeting("true",id);
        }

        if (action.equals("cancel")) {
            dataBaseController.updateMeeting("false",id);
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
        String check = dataBaseController.checkDisease(disease1.getName(),currentUser.getName());
        if(check == null) {
            dataBaseController.setDisease(disease1);
        }
        else {
            dataBaseController.updateDisease(disease1);
        }
        return "redirect:/MainPage";
    }
}