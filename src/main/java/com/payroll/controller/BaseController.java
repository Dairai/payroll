package com.payroll.controller;

import com.payroll.com.payroll.utils.FileProcess;
import com.payroll.model.PayrollFileModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {

    private static final String VIEW_INDEX = "index";
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {

        return VIEW_INDEX;
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file
                                    ,ModelMap model){

        if (file.isEmpty()) {
            model.put("msg", "failed to upload file because its empty");
            return "index";
        }

        FileProcess processingInstance = new FileProcess();
        try {
            if(!processingInstance.isExistingFile(file)){
                processingInstance.processFile(file);
                model.addAttribute("report",processingInstance.retrieveReport());
            }
            else{
                model.put("msg","the file exists!");
                return "index";
            }
        }
        catch(Exception ex){
            model.put("msg","failed to process file");
            return "/index";
        }

        return "report";
    }

    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public String showReport(ModelMap model){
        FileProcess processingInstance = new FileProcess();
        try{
        model.addAttribute("report",processingInstance.retrieveReport());
        }catch(Exception ex){
            model.put("msg", "failed to retrieve report");
            return "index";
        }
        return "report";
    }

}