package com.payroll.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.payroll.utils.ConfigManager;

import com.payroll.model.FileRecordModel;
import com.payroll.model.ReportRecordModel;

/**
 * Class to handle the data access to the relational database
 * Created by Dairai on 6/11/2017.
 */
public class FileSqlRepo {

    /**
     * Gets a new connection to access the database
     * @return Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private Connection getSQLDatabase() throws SQLException,ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn =
                DriverManager.
                        getConnection(ConfigManager.getInstance().getSetting(ConfigManager.MYSQL_URL_KEY));
        return conn;
    }

    /**
     * adds report to the relational database
     * @param reportList
     * @throws Exception
     */
    public void storeFile(List<FileRecordModel> reportList) throws Exception
    {
        Connection connection = getSQLDatabase();
        PreparedStatement statement =
                connection.prepareStatement
                        ("call sp_insert_report_data (?,?,?,?)");


        for(FileRecordModel model: reportList)
        {
            statement.setString(1,model.getDate());
            statement.setInt(2,model.getEmployeeID());
            statement.setDouble(3,model.getHours());
            statement.setString(4,model.getJobGroup());
            statement.addBatch();
        }

        statement.executeBatch();
        connection.close();
    }

    /**
     * Executes stored procedures on the database to create a new report
     * @throws Exception
     */
    public void processData() throws Exception{
        Connection connection = getSQLDatabase();
        CallableStatement cStmt = connection.prepareCall("{call sp_process_report_data()}");
        cStmt.execute();

        cStmt = connection.prepareCall("{call sp_generate_report()}");
        cStmt.execute();

        connection.close();
    }

    /**
     * Retrieves the latest report from the database
     * @return List
     * @throws Exception
     */
    public List<ReportRecordModel> retrieveReport() throws Exception{

        ArrayList<ReportRecordModel> report = new ArrayList<>();
        Connection connection = getSQLDatabase();
        CallableStatement cStmt = connection.prepareCall("{call sp_get_report()}");
        if(cStmt.execute()){
            ResultSet rSet = cStmt.getResultSet();
            while(rSet.next())
            {
                report.add(
                        new ReportRecordModel(
                                rSet.getInt("employee"),
                                rSet.getString("pay_period"),
                                rSet.getDouble("amount_paid"))
                );
            }
        }
        connection.close();
        return report;
    }

}
