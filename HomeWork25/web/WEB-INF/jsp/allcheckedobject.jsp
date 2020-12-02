<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Checked Object list</h2>


<ul>
   <c:forEach items="${checkedobjects}" var="checkedobject">
      <li><a href='showcheckedobject?id=${checkedobject.id}'>${checkedobject.name}</a></li>
   </c:forEach>
</ul>

</body>
</html>