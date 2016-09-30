<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group List Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>group list page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">group list</p>
<c:choose>
<c:when test = "${createInvalid != 'yes'}">
<form:form method="POST" action="/SocialNetworking/groupSetting" modelAttribute = "groupListModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="groupList_email" value=${groupList_email}>
	<input type = "hidden" name="groupList_passwod" value=${groupList_passwod}>
	<input type = "hidden" name="process_group" value=${process_group}>
    <input type="submit" value="Create New Group Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>
</c:when>
<c:otherwise>
<td>${errorOfGroupList }</td>
</c:otherwise>
</c:choose>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "groupListModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="groupList_email" value=${groupList_email}>
	<input type = "hidden" name="groupList_passwod" value=${groupList_passwod}>
	<input type = "hidden" name="process_group" value=${process_group}>
    <input type="submit" value="Home Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<c:forEach items="${groupList}" var="list">
	<tr>	
	<c:forEach items = "${list.value }" var = "image">
	<tr>${image.key}</tr>
		<c:forEach items = "${image.value}" var= "item">
			<c:choose>
			<c:when test = "${item.key == 'yes'}">
			<img src = ${item.value} alt="IMG DESC" width="100" height="100" />
			<form:form method="POST" action="/SocialNetworking/groupSetting" modelAttribute = "groupListModel">
				<table>		
					<tr>
					<td>
					<input type = "hidden" name="groupList_email" value=${groupList_email}>
					<input type = "hidden" name="groupList_passwod" value=${groupList_passwod}>
					<input type = "hidden" name="process_group" value=${list.key}>
					<input type="submit" value="Edit" class="container btn color-1 material-design"/>
					</td>
					</tr>
				</table> 
			</form:form>
			</c:when>
			</c:choose>
			<form:form method="POST" action="/SocialNetworking/post" modelAttribute = "groupListModel">
				<table>		
					<tr>
					<td>
					<input type = "hidden" name="groupList_email" value=${groupList_email}>
					<input type = "hidden" name="groupList_passwod" value=${groupList_passwod}>
					<input type = "hidden" name="process_group" value=${list.key}>
					<input type="submit" value="Post" class="container btn color-1 material-design"/>
					</td>
					</tr>
				</table> 
			</form:form>
			<c:choose>
			<c:when test = "${item.key == 'yes'}">
			<form:form method="POST" action="/SocialNetworking/groupList" modelAttribute = "groupListModel">
				<table>
					<tr>
					<td>
					<input type = "hidden" name="groupList_email" value=${groupList_email}>
					<input type = "hidden" name="groupList_passwod" value=${groupList_passwod}>
					<input type = "hidden" name="process_group" value=${list.key}>
					<input type="submit" value="Delete" class="container btn color-1 material-design"/>
					</td>
					</tr>
				</table> 
			</form:form>
			</c:when>
			</c:choose>
		</c:forEach>
	</tr>
	</c:forEach>
</c:forEach>


</body>
</html>