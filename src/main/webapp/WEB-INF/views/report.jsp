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
    <div class="container">
        <p>
            <a class="btn btn-info" href="${pageContext.request.contextPath}/">Load New Report</a>
        </p>
    </div>
    <div class="container">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Hours worked</th>
                    <th>Amount Paid</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${report}">
                    <tr>
                        <td>${employee.employeeId}</td>
                        <td>${employee.payPeriod}</td>
                        <td>$${String.format("%.2f", employee.amtPaid)}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
