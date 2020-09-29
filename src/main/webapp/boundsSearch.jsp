<%--
  Created by IntelliJ IDEA.
  User: ernest
  Date: 14/03/2020
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Bounds Search</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Bound Search</h2>
    <p>Select a column in the dropdown menu and choose either find minimum or maximum.</p>
    <div>
        <form action="boundssearch.html?beginsearch=true" method="post">
            <div class="dropdown" style="width:250px">
                <select name="columnName" id="columnName">
                    <%
                        String[] columnNames = (String[]) request.getAttribute("columnNames");
                        for (String column : columnNames) {
                    %>
                    <option name="<%=column%>" value="<%=column%>"><%=column%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="bounds-btn-group">
                <input type="submit" name="min" value="Find Minimum" id="min" class="button"
                       onclick="window.location.href = '/boundssearch.html?bound=min'"><span></span>
                <input type="submit" name="max" value="Find Maximum" id="max" class="button"
                       onclick="window.location.href = '/boundssearch.html?bound=max'"/><span></span>
                <button type="button" class="button" onclick="window.location.href = '/search.html'"><span>Back</span>
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
