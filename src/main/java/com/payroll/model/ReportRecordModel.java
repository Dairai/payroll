package com.payroll.model;

/**
 * Model for storing retrieved report records
 * Created by Dairai on 6/11/2017.
 */
public class ReportRecordModel {

    private int employeeId;
    private String payPeriod;
    private Double amtPaid;

    public ReportRecordModel(){}

    public ReportRecordModel(int employeeId,String payPeriod, Double amtPaid)
    {
        this.amtPaid = amtPaid;
        this.payPeriod = payPeriod;
        this.employeeId = employeeId;
    }
    public int getEmployeeId(){return employeeId;}
    public String getPayPeriod() {return payPeriod;}
    public Double getAmtPaid() {return amtPaid;}

    public void setEmployeeId(int employeeId){this.employeeId = employeeId;}
    public void setPayPeriod(String groupId){this.payPeriod = payPeriod;}
    public void setAmtPaid(Double amtPaid){this.amtPaid = amtPaid;}


}
