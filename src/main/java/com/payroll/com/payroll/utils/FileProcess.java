package com.payroll.com.payroll.utils;

import com.opencsv.CSVReader;
import com.payroll.model.PayrollFileModel;
import com.payroll.model.FileRecordModel;
import com.payroll.model.ReportRecordModel;
import org.springframework.web.multipart.MultipartFile;
import com.payroll.data.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dairai on 6/11/2017.
 */
public class FileProcess {

    public void processFile(MultipartFile file) throws Exception
    {
        processMongo(file);
        processSQL(file);
    }

    public Boolean isExistingFile(MultipartFile file)
    {
        FileMongoRepo mongoRepo = new FileMongoRepo();
        return mongoRepo.fileExists(file.getOriginalFilename());
    }

    public void processMongo(MultipartFile file) throws IOException{
        PayrollFileModel newFile = new PayrollFileModel();
        newFile.setName(file.getOriginalFilename());
        newFile.setUploadDate(new Date());
        newFile.setData(file.getBytes());
        FileMongoRepo mongoRepo = new FileMongoRepo();
        mongoRepo.storeFile(newFile);
    }

    public void processSQL(MultipartFile file) throws Exception{
        File convFile = new File( file.getOriginalFilename());
        file.transferTo(convFile);
        ArrayList<FileRecordModel> rows = new ArrayList<>();

        String[] nextLine;

        try (FileReader fileReader = new FileReader(convFile);
             CSVReader reader =
                     new CSVReader(fileReader, ',', '\'', 1);) {
            reader.readNext(); //skip first line
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[0].contains("report")) break;

                rows.add(
                        new FileRecordModel(
                                nextLine[0],
                                nextLine[1],
                                nextLine[2],
                                nextLine[3]));
            }
        }

        FileSqlRepo sqlRepo = new FileSqlRepo();
        sqlRepo.storeFile(rows);
        sqlRepo.processData();
    }


    public List<ReportRecordModel> retrieveReport() throws
            Exception
    {
        FileSqlRepo sqlRepo = new FileSqlRepo();
        return sqlRepo.retrieveReport();
    }
}
