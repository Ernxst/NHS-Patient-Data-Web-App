<%--
  Created by IntelliJ IDEA.
  User: ernest
  Date: 16/03/2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Import</title>
    <script src="scripts.js"></script>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Import DataFrame</h2>
    <p>Select a dataframe file to upload and view:</p>
    <form action="patientList.html" method="POST" enctype="multipart/form-data">
        <div class="btn-group">
            <p>Select a CSV or JSON file:</p>
            <label class="button" onclick="addListener()" id="custom-file-label">
                <input type="file" id="myfile" name="myfile"/>
                Choose File
            </label>
            <input class="button" type="submit" value="Upload File">
        </div>
        <label id="selected-file">Selected file: None</label>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
