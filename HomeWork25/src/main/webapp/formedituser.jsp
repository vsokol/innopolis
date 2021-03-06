<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Изменение информации пользователя</h1>
<form method="post" action="${pageContext.request.contextPath}/edituser" autocomplete="off">
    <div class="form-group">
        <div class="input-group">
            <span class="input-group-addon"><strong>Идентификатор:</strong></span>
            <input name="id" type="number" class="form-control" id="id" readonly value="${user.id}">
        </div>
    </div>
    <div class="form-group">
        <div class="input-group">
            <span class="input-group-addon"><strong>Логин:</strong></span>
            <input name="login" type="text" class="form-control" size="15" id="login" required="required" value="${user.login}">
        </div>
    </div>
    <div class="form-group">
        <div class="input-group">
            <span class="input-group-addon"><strong>Имя:</strong></span>
            <input name="name" type="text" class="form-control" size="50" id="name" required="required" value="${user.name}">
        </div>
    </div>
    <div class="form-group">
        <div class="input-group">
            <span class="input-group-addon"><strong>Описание:</strong></span>
            <input name="fullName" type="text" class="form-control" id="fullName" value="${user.fullName}">
        </div>
    </div>
    <input type="hidden" name="_method" value="put">
    <button type="submit" class="btn btn-primary">Применить</button>
    <button type="reset" class="btn btn-default">Сбросить</button>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/allusers">Отменить</a>
</form>