<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>post Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>post page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">post</p>
<form:form method="POST" action="/SocialNetworking/groupList" modelAttribute = "postModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="post_email" value=${post_email}>
	<input type = "hidden" name="post_password" value=${post_password}>
    <input type="submit" value="Return To Group List Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/post" modelAttribute = "postModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="post_email" value=${post_email}>
	<input type = "hidden" name="post_password" value=${post_password}>
	<input type = "hidden" name="group_id" value=${group_id}>
    <input type="submit" value="Reload Post Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<table class="display" cellspacing="0" width="100%">
<tbody>
	<thead>
            <tr>
                <th>Picture</th>
                <th>User</th>
                <th>Time</th>
                <th>Post</th>
                <th>Liker</th>
                <th>Do you like?</th>
            </tr>
        </thead>
	
			
			<c:forEach items = "${main}" var = "main">
			<form:form method="POST" action="/SocialNetworking/post" modelAttribute = "postModel">
			<input type = "hidden" name="post_email" value=${post_email}>
			<input type = "hidden" name="post_password" value=${post_password}>
			<input type = "hidden" name="group_id" value=${group_id}>
			<input type = "hidden" name="newliker" value=${post_email}>
			<input type = "hidden" name="post_id" value=${main.key}>
			<tr>
				<td>
				<img src = ${picture[main.key]} alt="IMG DESC" width="50" height="50" />
				</td>
				<c:forEach items = "${main.value}" var = "info">
				<td>${info}</td>
				</c:forEach>
				<c:forEach items = "${liker[main.key]}" var = "liker">
				<td>${liker}</td>
				</c:forEach>
				<td><input type="submit" value="You like this post!" class="container btn color-1 material-design"/></td>
			</tr>
			</form:form>
			</c:forEach>
	
</tbody>
</table>

<form:form method="POST" action="/SocialNetworking/post" modelAttribute = "postModel">
<table>
    <tr>
    <td>
    		<input type = "hidden" name="post_email" value=${post_email}>
			<input type = "hidden" name="post_password" value=${post_password}>
			<input type = "hidden" name="group_id" value=${group_id}>
			<input type = "hidden" name="newliker" value=${newliker}>
			<th>Your Post</th>
			<input name="text" type="textbox" style="width: 300px; height:300px">
			<input type="submit" value="submit your post" class="container btn color-1 material-design"/>
			
    </td>
    </tr>
</table> 
</form:form>


</body>
</html>