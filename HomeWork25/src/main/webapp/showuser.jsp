<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<h2>${user.name}</h2>

<dl class="dl-horizontal">
    <dt>Идентификатор:</dt>
    <dd>${user.id}</dd>
    <dt>Логин:</dt>
    <dd>${user.login}</dd>
    <dt>Имя:</dt>
    <dd>${user.name}</dd>
    <dt>Заблокирован?:</dt>
    <dd><c:if test="${user.isLock==true}">Да</c:if>
        <c:if test="${user.isLock==false}">Нет</c:if>
    </dd>
    <dt>Описание:</dt>
    <dd>${user.fullName}</dd>
</dl>
</body>
</html>

<br>
<a href="/main.jsp">Главная страница</a>
