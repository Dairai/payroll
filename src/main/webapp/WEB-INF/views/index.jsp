<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome!</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

    <div class="container">
        <h2><small>Submit report!</small></h2>

        <div>
            <p>
                <a class="btn btn-info" href="${pageContext.request.contextPath}/report">View Report</a>
            </p>
        </div>
        <div>
            <form action="${pageContext.request.contextPath}/upload"
                  method="POST" enctype="multipart/form-data" >
                <fieldset>
                    <div class="form-group">
                        <label class="control-label" for="file_input">Choose File</label>
                        <div>
                            <input type="file" name="file" id="file_input" class="form-control-file"/>
                        </div>
                    </div>
                    <button type="submit" id="upload" class="btn btn-default">Upload</button>
                </fieldset>
            </form>
        </div>
        <div>
            <p class="text-danger">
                <c:if test="${not empty msg}">
                    Error: ${msg}
                </c:if>
            </p>
        </div>
    </div>
</body>
</html>
