<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table">
   <thead>
      <tr>
         <th>Идентификатор</th>
         <th>Название</th>
         <th>Описание</th>
      </tr>
   </thead>
   <tbody>
      <c:forEach items="${checkedobjects}" var="checkedobject">
         <tr>
            <td scope="row">${checkedobject.id}</td>
            <td>${checkedobject.name}</td>
            <td>${checkedobject.descr}</td>
            <td><a href="${pageContext.request.contextPath}/showcheckedobject?id=${checkedobject.id}">Ссылка</a></td>
            <td><a href="${pageContext.request.contextPath}/editcheckedobject?id=${checkedobject.id}">Изменить</a></td>
            <td><a href="${pageContext.request.contextPath}/removecheckedobject?id=${checkedobject.id}">Удалить</a></td>
         </tr>
      </c:forEach>
   </tbody>
</table>

<br>
<a href="/">Главная страница</a>