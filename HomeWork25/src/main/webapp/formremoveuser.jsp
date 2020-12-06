<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h3>Вы действительно хотите удалить пользователя "${user.name}" с логином "${user.login}"?</h3>

<form method="post" action="${pageContext.request.contextPath}/removeuser?${user.id}"
      autocomplete="off">
    <input type="hidden" name="id" value="${user.id}">
    <input type="hidden" name="_method" value="delete">
    <button type="submit" class="btn btn-primary">Удалить</button>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/allusers">Отменить</a>
</form>
