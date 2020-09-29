<%@ page import="uk.ac.ucl.dataframe.Patient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.TreeMap" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Viewing all Patients</title>
    <script src="scripts.js"></script>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Patients (A-Z)</h2>
    <p>Below is an expandable list of all patients. Click on a letter to expand it and click on a patient to view their
        data.</p>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.css" rel="stylesheet"
          type="text/css"/>
    <nav class="c-list">
        <%
            TreeMap<Character, List<Patient>> patients = (TreeMap<Character, List<Patient>>) request.getAttribute("patientNames");
            for (Character character : patients.keySet()) {
        %>
        <details class="group">
            <summary><%=character%>
            </summary>
            <div class="members">
                <%
                    for (Patient patient : patients.get(character)) {
                        String id = patient.getId();
                        String href = "patient.html?id=" + id;
                        String fullName = patient.getFullName();
                %>
                <a class="member" href="<%=href%>" title="<%=id%>"><span><%=fullName%></span></a>
                <% } %>
            </div>
        </details>
        <% } %>
    </nav>
    <div class="btn-group">
        <button id="topBtn" class="button" onclick="toTop()"><span>Back to Top</span></button>
        <button class="button" onclick="window.location.href = '/download?filename=sample.csv'">
            <span>Export as CSV</span></button>
        <button class="button" onclick="window.location.href = '/download?filename=sample.json'">
            <span>Export as JSON</span></button>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
