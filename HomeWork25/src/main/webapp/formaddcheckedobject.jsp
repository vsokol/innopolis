<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="checkedobject" class="store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject" />
<c:set target="${checkedobject}" property="name" value="Объект №" />
<jsp:setProperty name="checkedobject" property="descr" value="без описания" />

<h1>Добавление нового объекта проверки</h1>
<form method="post" action="${pageContext.request.contextPath}/addcheckedobject" autocomplete="off">
    <div class="form-group">
        <label for="name">Название</label>
        <input name="name" type="text" class="form-control" id="name" required="required" value="<jsp:getProperty name="checkedobject" property="name" />">
    </div>
    <div class="form-group">
        <label for="descr">Описание</label>
        <input name="descr" type="text" class="form-control" id="descr" value="<jsp:getProperty name="checkedobject" property="descr" />">
    </div>
    <button type="submit" class="btn btn-primary">Применить</button>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/allcheckedobject">Отменить</a>
</form>
