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
    <title>Keyword Search</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Keyword Search</h2>
    <p>Enter data in a column's corresponding entry and select the checkbox to include it in the search. Selecting the
        checkbox for a field and leaving the entry blank assumes the keyword to be 'N/A'.</p>
    <%
        String[] columnNames = (String[]) request.getAttribute("columnNames");
        for (String column : columnNames) {

    %>
    <form method="post" action="keywordsearch.html?beginsearch=true">
        <div class="container">
            <label class="label"><%=column%>
            </label>
            <input type="text" name="<%=column%>_keyword" placeholder="Enter search keyword for column <%=column%>"/>
                <input id="check" type="checkbox" class="custom-checkbox" name="include_<%=column%>">
        </div>
        <%
            }
        %>
        <div class="btn-group">
            <button type="submit" class="button"><span>Search</span></button>
            <button type="button" class="button" onclick="window.location.href = '/search.html'"><span>Back</span>
            </button>
        </div>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
