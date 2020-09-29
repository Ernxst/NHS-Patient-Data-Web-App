<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.dataframe.Patient" %>
<%@ page import="java.util.TreeMap" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Search Result</h2>
    <%
        TreeMap<Character, List<Patient>> patients = (TreeMap<Character, List<Patient>>) request.getAttribute("result");
        if (patients.size() != 0) { %>
    <p>These are all the patients matching the search criteria.</p>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.css" rel="stylesheet"
          type="text/css"/>
    <nav class="c-list">
        <%
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
    <% } else {
    %>
    <p>Nothing found. Please either broaden the search criteria or try another search term. </p>
    <%}%>
    <div class="btn-group">
        <button class="button" onclick="toTop()"><span>Back to Top</span></button>
        <button class="button" onclick="goBack()"><span>Back</span></button>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>