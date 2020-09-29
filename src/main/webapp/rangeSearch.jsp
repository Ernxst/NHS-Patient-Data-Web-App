<%--
  Created by IntelliJ IDEA.
  User: ernest
  Date: 14/03/2020
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Range Search</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Range Search</h2>
    <p>Select a column from the dropdown menu and enter a minimum and maximum value to perform a range search.</p>
    <div class="container">
        <form method="POST" action="rangesearch.html?beginsearch=true">
            <div class="dropdown" style="width:200px">
                <select name="columnName" id="columnName">
                    <%
                        String[] columnNames = (String[]) request.getAttribute("columnNames");
                        for (String column : columnNames) {
                    %>
                    <option value="<%=column%>"><%=column%>
                    </option>
                    <%}%>
                </select>
            </div>
            <input type="text" id="minvalue" name="minvalue" placeholder="Enter minimum value here"/>
            <input type="text" id="maxvalue" name="maxvalue" placeholder="Enter maximum value here"/>
            <div class="btn-group">
                <input type="submit" class="button" value="Search"/><span></span>
                <button type="button" class="button" onclick="window.location.href = '/search.html'"><span>Back</span>
                </button>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
