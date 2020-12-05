<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table">
    <thead>
    <tr>
        <th>Ид.</th>
        <th>Логин</th>
        <th>Имя</th>
        <th>Заблокирован</th>
        <th>Описание</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td scope="row">${user.id}</td>
            <td>${user.login}</td>
            <td>${user.name}</td>
            <td>${user.isLock}</td>
            <td>${user.fullName}</td>
            <td><a href="${pageContext.request.contextPath}/showuser?id=${user.id}">Просмотр</a></td>
            <td><a href="${pageContext.request.contextPath}/lockuser?id=${user.id}">Заблокировать</a></td>
            <td><a href="${pageContext.request.contextPath}/unlockuser?id=${user.id}">Разблокировать</a></td>
            <td><a href="${pageContext.request.contextPath}/changeuserpassword?id=${user.id}">Изменить пароль</a></td>
            <td><a href="${pageContext.request.contextPath}/edituser?id=${user.id}">Изменить</a></td>
            <td><a href="${pageContext.request.contextPath}/removeuser?id=${user.id}">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/adduser" type="button" class="btn btn-primary">Добавить пользователя</a>

<br>
<br>
<a href="/main.jsp">Главная страница</a>