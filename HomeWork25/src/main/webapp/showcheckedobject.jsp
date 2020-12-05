<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <body>
        <h2>${checkedobject.name}</h2>
    <ul class="list-group">
        <li class="list-group-item">${checkedobject.id}</li>
        <li class="list-group-item">${checkedobject.name}</li>
        <li class="list-group-item">${checkedobject.descr}</li>
    </ul>
    </body>
</html>

<br>
<a href="/main.jsp">Главная страница</a>
