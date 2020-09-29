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
    <title>Editing Patient <%=fullName%>
    </title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Editing data for patient <%=fullName%>
    </h2>
    <p>Enter the new data for a column in the entry. The original value is shown in the entry when left empty. To remove
        a piece
        of data, enter N/A - leaving an entry blank has no effect on the dataframe.</p>
    <form action="editpatient.html?id=<%=id%>" method="post">
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
                <input type="text" name="<%=columnName%>" placeholder="<%=data%>" class="content-input"/>
                </div>
            </div>
            <% } %>
        </div>
        <div class="btn-group">
            <input type="submit" value="Save Changes"/><span></span>
            <button class="button" onclick="window.location.href = 'patient.html?id=<%=id%>'">
                <span>Discard Changes</span></button>
        </div>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
