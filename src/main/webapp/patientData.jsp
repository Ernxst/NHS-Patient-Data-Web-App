<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: ernest
  Date: 12/03/2020
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    LinkedHashMap<String, String> patientData = (LinkedHashMap<String, String>) request.getAttribute("patientData");
    String fullName = (String) request.getAttribute("fullName");
    String id = (String) request.getAttribute("id");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Viewing Patient <%=fullName%>
    </title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Data for patient <%=fullName%>
    </h2>
    <p>Currently showing the stored data for patient <%=fullName%>. To edit any field of data, active editing mode by clicking "Edit" at the bottom of the page.</p>
    <div class="grid">
        <%
            for (Map.Entry<String, String> columnData : patientData.entrySet()) {
                String columnName = columnData.getKey();
                String data = columnData.getValue();
        %>
        <div class="cell">
            <div class="column"><%=columnName%>
            </div>
            <div class="content">
                <input type="text" readonly placeholder="<%=data%>"/>
            </div>
        </div>
        <% } %>
    </div>
    <div class="btn-group">
        <button class="button" onclick="window.location.href = 'editpatient.html?id=<%=id%>'"><span>Edit</span></button>
        <button class="button" onclick="window.location.href = 'patientList.html'"><span>Back</span></button>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
