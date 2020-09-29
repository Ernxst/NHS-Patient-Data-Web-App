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
    <title>Home</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="centred">
    <h1><span>Patient Data Web App</span></h1>
    <p class="hp">Welcome to the patient data web app.</p>
    <p class="hp">You are currently at the homepage, either import a dataframe file or view, analyse and search the default frame
        to get started.</p>
    <nav class="btn-group">
        <button class="button" onclick="window.location.href = '/patientList.html'"><span>View</span></button>
        <button class="button" onclick="window.location.href = '/analytics.html'"><span>Analytics</span></button>
        <button class="button" onclick="window.location.href = '/upload.html'"><span>Import</span></button>
        <button class="button" onclick="window.location.href = '/search.html'"><span>Search</span></button>
    </nav>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
