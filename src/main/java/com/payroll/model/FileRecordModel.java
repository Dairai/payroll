package com.payroll.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dairai on 6/11/2017.
 */
public class FileRecordModel {
    private String date;
    private String hours;
    private String employeeId;
    private String jobGroup;


    public FileRecordModel(){}

    public FileRecordModel(String date, String hours, String employeeId, String jobGroup){
        this.date = date;
        this.hours = hours;
        this.employeeId = employeeId;
        this.jobGroup = jobGroup;
    }

    public void setDate(String date){}
    public void setHours(String hours){}
    public void setEmployeeId (String employeeId){};
    public void setJobGroup (String jobGroup){};

    public String getDate() throws Exception{ return date;}
    public int getEmployeeID(){ return Integer.parseInt(employeeId);}
    public double getHours(){ return Double.parseDouble(hours);}
    public String getJobGroup(){ return jobGroup; }
}
