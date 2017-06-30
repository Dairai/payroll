package com.payroll.controller;

import com.payroll.utils.FileProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BaseController {

    private static final String VIEW_INDEX = "upload";
    private static final String VIEW_REPORT = "report";
    private static final String VIEW_LOGIN = "login";

    @Autowired
    UserDetailsService userDetailsService;

/*    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        //model.addAttribute("login", new Login());
        return VIEW_LOGIN;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String validateLogin(ModelMap model, @RequestParam("username") String username,
                                @RequestParam("password") String password) {

        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(user != null && user.isEnabled())
        {
            return VIEW_INDEX;
        }

        return VIEW_LOGIN;
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {

        return VIEW_INDEX;
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file
                                    ,ModelMap model){

        if (file.isEmpty()) {
            model.put("msg", "failed to upload file because its empty");
            return VIEW_INDEX;
        }

        FileProcess processingInstance = new FileProcess();
        try {
            if(!processingInstance.isExistingFile(file)){
                processingInstance.processFile(file);
                model.addAttribute("report",processingInstance.retrieveReport());
            }
            else{
                model.put("msg","the file exists!");
                return VIEW_INDEX;
            }
        }
        catch(Exception ex){
            model.put("msg","failed to process file");
            return VIEW_INDEX;
        }

        return VIEW_REPORT;
    }

    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public String showReport(ModelMap model){
        FileProcess processingInstance = new FileProcess();
        try{
        model.addAttribute("report",processingInstance.retrieveReport());
        }catch(Exception ex){
            model.put("msg", "failed to retrieve report");
            return VIEW_INDEX;
        }
        return VIEW_REPORT;
    }

}