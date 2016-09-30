<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inbox Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>inbox page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">chat</p>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "inboxModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="inbox_email" value=${inbox_email}>
	<input type = "hidden" name="inbox_password" value=${inbox_password}>
	<input type = "hidden" name="process_chat" value=${process_chat}>
	
    <input type="submit" value="Home Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/inbox" modelAttribute = "inboxModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="inbox_email" value=${inbox_email}>
	<input type = "hidden" name="inbox_password" value=${inbox_password}>
	<input type = "hidden" name="process_chat" value=${process_chat}>
    <input type="submit" value="Reload Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<c:choose>
<c:when test = "${createInvalid != 'yes' }">
<form:form method="POST" action="/SocialNetworking/createChat" modelAttribute = "inboxModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="inbox_email" value=${inbox_email}>
	<input type = "hidden" name="inbox_password" value=${inbox_password}>
	<input type = "hidden" name="process_chat" value=${process_chat}>
    <input type="submit" value="Create New Message" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>
</c:when>
<c:otherwise>
<td>${errorOfInboxList }</td>
</c:otherwise>
</c:choose>

<c:forEach items="${inboxList}" var="list">
	<tr>	
	<c:forEach items = "${list.value }" var = "name">
		<c:forEach items = "${name.value}" var= "image">
			<tr>${name.key}</tr>
			<img src = ${image.value} alt="IMG DESC" width="100" height="100" />
			<c:choose>
			<c:when test = "${image.key == 'yes'}">			
			<form:form method="POST" action="/SocialNetworking/inbox" modelAttribute = "inboxModel">
				<table>		
					<tr>
					<td>
					<input type = "hidden" name="inbox_email" value=${inbox_email}>
					<input type = "hidden" name="inbox_password" value=${inbox_password}>
					<input type = "hidden" name="process_chat" value=${list.key}>
					<input type="submit" value="delete" class="container btn color-1 material-design"/>
					</td>
					</tr>
				</table> 
			</form:form>
			</c:when>
			</c:choose>
			<form:form method="POST" action="/SocialNetworking/chatPage" target = "_blank" modelAttribute = "inboxModel">
				<table>		
					<tr>
					<td>
					<input type = "hidden" name="inbox_email" value=${inbox_email}>
					<input type = "hidden" name="inbox_passwod" value=${inbox_passwod}>
					<input type = "hidden" name="process_chat" value=${list.key}>
					<input type="submit" value="chat" class="container btn color-1 material-design"/>
					</td>
					</tr>
				</table> 
			</form:form>
		</c:forEach>
	</tr>
	</c:forEach>
</c:forEach>
</body>
</html>