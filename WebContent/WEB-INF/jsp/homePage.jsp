<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>home page</h1>
<p id="dtl">Social Media Web Application</p>
<form:form method="POST" action="/SocialNetworking/profile" modelAttribute = "homeModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="home_email" value=${home_email}>
	<input type = "hidden" name="home_password" value=${home_password}>
    <input type="submit" value="Profile Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/friendList" modelAttribute = "homeModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="home_email" value=${home_email}>
	<input type = "hidden" name="home_password" value=${home_password}>
    <input type="submit" value="Friend List Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/friendSearch" modelAttribute = "homeModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="home_email" value=${home_email}>
	<input type = "hidden" name="home_password" value=${home_password}>
    <input type="submit" value="Friend Search Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/groupList" modelAttribute = "homeModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="home_email" value=${home_email}>
	<input type = "hidden" name="home_password" value=${home_password}>
    <input type="submit" value="Group Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/inbox" modelAttribute = "homeModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="home_email" value=${home_email}>
	<input type = "hidden" name="home_password" value=${home_password}>
    <input type="submit" value="Inbox Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/loginPage" modelAttribute = "homeModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="home_email" value=${home_email}>
	<input type = "hidden" name="home_password" value=${home_password}>
    <input type="submit" value="sign out" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

</body>
</html>