<%--
  Created by IntelliJ IDEA.
  User: Dairai
  Date: 6/11/2017
  Time: 2:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Report</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Employee ID</th>
                <th>Hours worked</th>
                <th>Employee id</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="employee" items="${report}">
                <tr>
                    <td>${employee.employeeId}</td>
                    <td>${employee.payPeriod}</td>
                    <td>$${employee.amtPaid}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
