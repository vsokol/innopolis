<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Смена пароля:</h1>
<br>
<form method=post action="change_password">
    <div class="form-group">
        <div class="input-group">
            <span class="input-group-addon"><strong>Логин:</strong></span>
            <input name="login" type="text" class="form-control" size="15" id="login" readonly value="${user.login}">
        </div>
    </div>
    <div class="form-group">
        <div class="input-group">
            <span class="input-group-addon"><strong>Пароль:</strong></span>
            <input name="password" type="password" class="form-control" size="50" id="password" required
                   value="${user.password}">
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Применить</button>
    <button type="reset" class="btn btn-default">Сбросить</button>
</form>
</html>