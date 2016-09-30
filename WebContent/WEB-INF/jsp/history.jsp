<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>History Page</title>
</head>
<body>
<h2>History</h2>
<p>Click below button to redirect the result to new page</p>
<c:forEach items = "${list}" var = "item">
<p>${item} </p>
</c:forEach>
</body>
</html>