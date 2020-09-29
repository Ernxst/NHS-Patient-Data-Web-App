<%@ page import="java.util.*" %><%--

A bit 'hacky' in some areas but I just wanted everything to work...

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TreeMap<String, LinkedHashMap<String, Integer>> data = (TreeMap<String, LinkedHashMap<String, Integer>>) request.getAttribute("data");
%>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Analytics for DataFrame</title>
    <script src="scripts.js"></script>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Analytics for DataFrame</h2>
    <p>Below is a collection of data regarding the dataframe. Clicking a cell will perform a search of the dataframe for
        that value in the column and take you to the search results page.</p>
    <div class="analytics-grid">
        <%
            for (Map.Entry<String, LinkedHashMap<String, Integer>> column : data.entrySet()) {
                String header = column.getKey();
                LinkedHashMap<String, Integer> columnData = column.getValue();
                String cellType = "cell";
                if (columnData.size() / 4 > 3) {
                    cellType = "large-cell";
                }
                else if (columnData.size() / 4 < 2) {
                    cellType = "small-cell";
                }

        %>
        <div class="<%=cellType%>">
            <div class="column"><%=header%>
            </div>
            <div class="content">
            <%
                for (Map.Entry<String, Integer> fields : columnData.entrySet()) {
                    String fieldName = fields.getKey();
                    Integer fieldValue = fields.getValue();
            %>
                <div class="cell-column">
                    <a class="custom-header"><%=fieldName%>
                    </a>
                    <a class="custom-value"><%=fieldValue%>
                    </a>
                    <%
                        switch (header) {
                            case "AGE DISTRIBUTION":
                                String min, max;
                                switch (fieldName) {
                                    case "N/A":
                                        min = "-1";
                                        max = "-1";
                                        break;
                                    case "100+":
                                        min = "100";
                                        max = "200";
                                        break;
                                    default:
                                        String[] ranges = fieldName.split("-");
                                        min = ranges[0];
                                        max = ranges[1];

                                }
                    %>
                    <form method="post" action="rangesearch.html?beginsearch=true">
                        <select name="columnName">
                            <option selected="selected" value="age">
                            </option>
                        </select>
                        <input type="text" name="minvalue" value="<%=min%>">
                        <input type="text" name="maxvalue" value="<%=max%>">
                        <input class="hidden-input" type="submit">
                    </form>
                    <%
                            break;
                        case "AGES":
                            String id = "";
                            switch (fieldName) {
                                case "Youngest":
                                    id = "max";
                                    break;
                                case "Oldest":
                                    id = "min";
                                    break;
                                default:

                            }
                    %>
                    <form action="boundssearch.html?beginsearch=true" method="post">
                        <select name="columnName">
                            <option selected="selected" value="age">
                            </option>
                        </select>
                        <input class="hidden-input" type="submit" name="<%=id%>" id="<%=id%>"
                               onclick="window.location.href = '/boundssearch.html?bound=<%=id%>'">
                    </form>
                    <%
                            break;
                        default:
                    %>

                    <form method="post" action="keywordsearch.html?beginsearch=true">
                        <input type="text" name="<%=header%>_keyword" value="<%=fieldName%>">
                        <input type="checkbox" name="include_<%=header%>" checked>
                        <input class="hidden-input" type="submit">
                    </form>
                    <%}%>
                </div>
            <%}%>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <div class="btn-group">
        <button class="button" onclick="goBack()">Back</button>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
