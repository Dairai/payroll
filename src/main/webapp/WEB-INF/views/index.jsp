<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Welcome!</h2>


    <div>
        <p>
            <a href="${pageContext.request.contextPath}/report">View Report</a>
        </p>
    </div>
    <form action="${pageContext.request.contextPath}/upload" method="POST" enctype="multipart/form-data" >

        <div class="form-group">
            <label>File input</label>
            <input type="file" name="file"/>
        </div>

        <br/><br/>
        <button type="submit" id="upload">Upload</button>
    </form>
    <p>
        <c:if test="${not empty msg}">
            Error: ${msg}
        </c:if>
    </p>
</body>
</html>
