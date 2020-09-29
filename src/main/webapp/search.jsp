<%--
  Created by IntelliJ IDEA.
  User: ernest
  Date: 14/03/2020
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Search DataFrame</title>
    <script src="scripts.js"></script>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
<h2>Search</h2>
    <p>Please choose a searching method below.</p>
    <div class="btn-group">
        <button class="button" onclick="window.location.href = '/keywordsearch.html'"><span>Keyword Search</span></button>
        <button class="button" onclick="window.location.href = '/rangesearch.html'"><span>Range Search</span></button>
        <button class="button" onclick="window.location.href = '/boundssearch.html'"><span>Bounds Search</span></button>
        <button class="button" onclick="goBack()"><span>Back</span></button>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
