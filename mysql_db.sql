CREATE DATABASE payroll
	CHARACTER SET latin1
	COLLATE latin1_swedish_ci;
	
CREATE TABLE payroll.group_rate (
  group_id VARCHAR(210) DEFAULT NULL,
  rate DOUBLE DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 8192
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

CREATE TABLE payroll.pay_period (
  start_date DATE DEFAULT NULL,
  end_date DATE DEFAULT NULL,
  pay_period_txt VARCHAR(255) DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 273
CHARACTER SET latin1
COLLATE latin1_swedish_ci;


CREATE TABLE payroll.payroll_data (
  formatted_date DATE DEFAULT NULL,
  hours_worked DOUBLE DEFAULT NULL,
  employee_id INT(11) DEFAULT NULL,
  job_group VARCHAR(10) DEFAULT NULL,
  report_date VARCHAR(255) DEFAULT NULL
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

CREATE TABLE payroll.report_output (
  employee INT(11) DEFAULT NULL,
  pay_period VARCHAR(255) DEFAULT NULL,
  amount_paid DOUBLE DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 1260
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

CREATE PROCEDURE payroll.sp_generate_report()
BEGIN

  truncate table report_output;
  
  insert into report_output
  select payroll_data.employee_id,pay_period_txt, sum(hours_worked) * group_rate.rate from payroll_data 
  JOIN pay_period 
  on payroll_data.formatted_date between pay_period.start_date AND pay_period.end_date
  JOIN group_rate
  on group_rate.group_id = payroll_data.`job_group`
  group by pay_period.pay_period_txt, payroll_data.employee_id;
   
END;

CREATE PROCEDURE payroll.sp_get_report()
BEGIN
  select employee, pay_period, amount_paid from report_output order by employee;
END;

CREATE PROCEDURE payroll.sp_insert_report_data(IN reportdate VARCHAR(255), IN employeeId INT, IN hours DOUBLE, IN jobgroup VARCHAR(10))
BEGIN
    Insert into payroll_data (report_date,employee_id, hours_worked,job_group) VALUES (reportdate,employeeId,hours,jobgroup);
END;

CREATE PROCEDURE payroll.sp_process_report_data()
BEGIN
   UPDATE payroll.payroll_data SET formatted_date = STR_TO_DATE(report_date, '%d/%m/%Y') WHERE formatted_date IS NULL;

   IF NOT EXISTS (select * from pay_period where NOW() < end_date ) THEN
    insert into pay_period (start_date, end_date) VALUES(
      STR_TO_DATE(Concat('1,',MONTH(NOW()),',', YEAR(NOW())),'%d,%m,%Y'),
      STR_TO_DATE(Concat('15,',MONTH(NOW()),',', YEAR(NOW())),'%d,%m,%Y')
      ),
        (
      STR_TO_DATE(Concat('16,',MONTH(NOW()),',', YEAR(NOW())),'%d,%m,%Y'),
      STR_TO_DATE(Concat(LAST_DAY(NOW()),',',MONTH(NOW()),',', YEAR(NOW())),'%d,%m,%Y')
        );
   END IF;

   UPDATE payroll.pay_period SET pay_period_txt = CONCAT(DATE_FORMAT(start_date,'%d/%m/%Y'),'-',DATE_FORMAT(end_date,'%d/%m/%Y')) WHERE pay_period_txt IS NULL;

END;