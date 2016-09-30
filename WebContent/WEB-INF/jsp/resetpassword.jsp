<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>check answer page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">answer question</p>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "registerModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="register_email" value=${register_email}>
	<input type = "hidden" name="register_password" value=${register_password}>
    <input type="submit" value="home page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/resetpassword" modelAttribute = "registerModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="register_email" value=${register_email}>
	<input type = "hidden" name="register_password" value=${register_password}>
	

	
<p>security question is</p>
<div>${securityQ} </div>
<p>please answer question</p>

<tr><td>security answer is: </td><td><input name="securityA" type="textbox"></td></tr>
<tr><td colspan="2" align="right"><input type="submit" value="validate" class="container btn color-1 material-design"></td></tr>
            <div style="color:red">${errorOflogin}</div>
    </td>
    </tr>
</table> 
</form:form>
</body>
</html>