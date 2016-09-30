<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Friend List Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>friendlist page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">update friend</p>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "friendListModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="friendList_email" value=${friendList_email}>
	<input type = "hidden" name="friendList_password" value=${friendList_password}>
	<input type = "hidden" name="friendList_process_email" value=${friendList_process_email}>
	<input type = "hidden" name="friendList_process" value=${friendList_process}>
    <input type="submit" value="Home Page"  class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>


<c:forEach items="${friendList}" var="list">
	    <tr>
	        <td>email: </td><td>${list.key}</td>
	        <c:forEach items="${list.value}" var="insideList">
	        <tr>
		        <c:forEach items="${insideList.value}" var = "deepList">
		        <img src = ${deepList.key} alt="IMG DESC" width="100" height="100" />
			        <c:forEach items="${deepList.value}" var = "finalList">
		        		<td> details:  </td><td>${finalList}</td>
		       		 </c:forEach>
		        </c:forEach>
	        </tr>
	        <c:choose>
	        	<c:when test = "${insideList.key == 'no'}">
	        	<form:form method="POST" action="/SocialNetworking/friendList" modelAttribute = "friendListModel">
	        		<input type = "hidden" name="friendList_email" value=${friendList_email}>
					<input type = "hidden" name="friendList_password" value=${friendList_password}>
					<input type = "hidden" name="friendList_process_email" value=${list.key}>
					<input type = "hidden" name="friendList_process" value="add">
					<input type="submit" value="Add Friend"  class="container btn color-1 material-design"/>
	        	</form:form>
	        	<form:form method="POST" action="/SocialNetworking/friendList" modelAttribute = "friendListModel">
	        		<input type = "hidden" name="friendList_email" value=${friendList_email}>
					<input type = "hidden" name="friendList_password" value=${friendList_password}>
					<input type = "hidden" name="friendList_process_email" value=${list.key}>
					<input type = "hidden" name="friendList_process" value="delete">
					<input type="submit" value="Delete Request"  class="container btn color-1 material-design"/>
	        	</form:form>
	        	</c:when>
	        	<c:otherwise>
	        	<form:form method="POST" action="/SocialNetworking/friendList" modelAttribute = "friendListModel">
	        		<input type = "hidden" name="friendList_email" value=${friendList_email}>
					<input type = "hidden" name="friendList_password" value=${friendList_password}>
					<input type = "hidden" name="friendList_process_email" value=${list.key}>
					<input type = "hidden" name="friendList_process" value="delete">
					<input type="submit" value="Delete Friend"  class="container btn color-1 material-design"/>
	        	</form:form>
	        	</c:otherwise>
	        </c:choose>

	        </c:forEach>
	    </tr>
	</c:forEach>




</body>
</html>