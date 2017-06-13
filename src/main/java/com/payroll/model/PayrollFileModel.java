package com.payroll.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by Dairai on 6/11/2017.
 */
public class PayrollFileModel {
    private int id;
    private String name;
    private Date uploadDate;
    private byte[] data;


    public PayrollFileModel(){
        Random rn = new Random();
        id = rn.nextInt();
    }

    public void setName(String name) { this.name = name; }

    public String getName(){return name;}

    public int getId() {return id; }

    public void setUploadDate(Date uploadDate) { this.uploadDate = uploadDate;}

    public Date getUploadDate(){return uploadDate;}

    public void setData(byte[] data) { this.data = Arrays.copyOf(data, data.length);}

    public byte[] getData() {return data;}
}
