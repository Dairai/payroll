package com.payroll.utils;

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
 * Class that handles the processing of a data file
 * Created by Dairai on 6/11/2017.
 */
public class FileProcess {

    /**
     * Passes in the uploaded file and adds it to the Mongo and SQL database
     * @param file
     * @throws Exception
     */
    public void processFile(MultipartFile file) throws Exception
    {
        processMongo(file);
        processSQL(file);
    }

    /**
     * Checks to to see if there is an existing file with the same name as the new file
     * @param file
     * @return boolean
     */
    public Boolean isExistingFile(MultipartFile file)
    {
        FileMongoRepo mongoRepo = new FileMongoRepo();
        return mongoRepo.fileExists(file.getOriginalFilename());
    }

    /**
     * Adds the file to storage in Mongo
     * @param file
     * @throws IOException
     */
    public void processMongo(MultipartFile file) throws IOException{
        PayrollFileModel newFile = new PayrollFileModel();
        newFile.setName(file.getOriginalFilename());
        newFile.setUploadDate(new Date());
        newFile.setData(file.getBytes());
        FileMongoRepo mongoRepo = new FileMongoRepo();
        mongoRepo.storeFile(newFile);
    }

    /**
     * Adds the file to storage in MySQL
     * @param file
     * @throws Exception
     */
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

    /**
     * Retrieves the latest report for display
     * @return
     * @throws Exception
     */
    public List<ReportRecordModel> retrieveReport() throws
            Exception
    {
        FileSqlRepo sqlRepo = new FileSqlRepo();
        return sqlRepo.retrieveReport();
    }
}
