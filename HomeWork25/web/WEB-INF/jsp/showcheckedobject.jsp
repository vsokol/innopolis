<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<a href='allcheckedobject'> <- Go back to mobiles list</a>
<h2>${checkedobject.name}</h2>
<h3>params</h3>
<ul>
    <li>Name - ${checkedobject.name}</li>
    <li>Description - ${checkedobject.descr}</li>
</ul>
</body>
</html>