<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    private String str = "PageTitle: ";
    public String createTitle(String arg) {
        return str + arg;
    }
%>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title><%= createTitle((String) request.getAttribute("PageTitle"))%></title>
</head>
    <body>
        <div class="container">
            <c:import url="${PageBody}"/>
        </div>
    </body>
</html>
