<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h3>Вы действительно хотите удалить объект проверки "${checkedobject.name}"?</h3>

<form method="post" action="${pageContext.request.contextPath}/removecheckedobject?${checkedobject.id}"
      autocomplete="off">
    <input type="hidden" name="id" value="${checkedobject.id}">
    <input type="hidden" name="_method" value="delete">
    <button type="submit" class="btn btn-primary">Удалить</button>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/allcheckedobject">Отменить</a>
</form>
