<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/WEB-INF/date.tld" prefix="datetag" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<myTags:template>
    <jsp:attribute name="header">
        <h1>Объекты проверки</h1>
        (<datetag:DateTag plus="0"/>)
    </jsp:attribute>
  <jsp:body>
    <ul>
      <li><a href="${pageContext.request.contextPath}/allcheckedobject">Список объектов проверки</a></li>
      <li><a href="${pageContext.request.contextPath}/addcheckedobject">Добавить новый объект проверки</a></li>
    </ul>
  </jsp:body>
</myTags:template>