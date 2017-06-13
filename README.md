Payroll is a simple payroll management and reporting web application building using java and the Spring MVC framework. It leverages a
Mongo db to store the file as a document and an SQL database to store the records and create a report. Future enhancements will include enabling HTTPS and preventaing cross fite scripting in addition to UI enhancements

**Setting up the application:**

1. Setup a Mongo (The Mongo database should contain a collectoin called reports within which the information would be stored. )
2. Set up MySQL database (The script to create the database tables for the mysql database can be found in the application root folder called mysql_db.sql)
3. Update the Config.properties file with the database parameters 
3. Install Maven and Tomcat 9 on local machine
4. Create a Tomcat build configuration
5. Populate the pay_period table in the SQL database example row as below:

 =====================================================
 | 2014-01-01 | 2014-01-15 | 01/01/2014 - 15/01/2015 |
 =====================================================
 | 2014-01-16 | 2014-02-31 | 16/01/2014 - 31/01/2014 |
 =====================================================

**Running the application**

1. Run the new created build configuration
2. Navigate to http://localhost:8080/waveapps

Achievements

I have limited experience with MVC frameworks, especially non-ASP.NET ones. I did not want to have to figure out how to port it over to MacOS/Linux so I opted to go with a platform that would work better on either of those operating systems. I am proud that I was able to learn about and build a web application using Spring MVC in a short amount of time. This is also useful as I am exploring Javascript front end frameworks and creating more and more web applications. 
