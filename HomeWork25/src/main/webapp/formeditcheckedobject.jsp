<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Редактирование объекта проверки</h1>
<form method="post" action="${pageContext.request.contextPath}/editcheckedobject" autocomplete="off">
    <div class="form-group">
        <label for="id">Идентификатор</label>
        <input name="id" type="number" class="form-control" id="id" readonly value="${checkedobject.id}">
    </div>
    <div class="form-group">
        <label for="name">Название</label>
        <input name="name" type="text" class="form-control" id="name" required="required" value="${checkedobject.name}">
    </div>
    <div class="form-group">
        <label for="descr">Описание</label>
        <input name="descr" type="text" class="form-control" id="descr" value="${checkedobject.descr}">
    </div>
    <input type="hidden" name="_method" value="put">
    <button type="submit" class="btn btn-primary">Применить</button>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/allcheckedobject">Отменить</a>
</form>
