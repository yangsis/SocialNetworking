<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>change password</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>reset password page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">reset password</p>
<form:form method="POST" action="/SocialNetworking/profile" modelAttribute = "registerModel">
<table id="lgn">
    <tr>
    <td>
    <tr><td>new Password: </td><td><input name="register_password" type="password"></td></tr>
    <input type = "hidden" name="register_email" value=${register_email}>
        <input type = "hidden" name="firstName" value=${firstName}>
            <input type = "hidden" name="lastName" value=${lastName}>
                <input type = "hidden" name="birthday" value=${birthday}>
                    <input type = "hidden" name="status" value=${status}>
             
                    
    
    <input type="submit" value="change password" class="container btn color-1 material-design"/>
    <div style="color:red">${errorOfinsert}</div>
     <div style="color:red">${errorOfinsert2}</div>
    </td>
    </tr>
</table> 
</form:form>


</body>
</html>