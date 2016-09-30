<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>profile page</h1>
<p id="dtl">Social Media Web Application</p>

<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "profileModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="profile_email" value=${profile_email}>
	<input type = "hidden" name="profile_password" value=${profile_password}>
    <input type="submit" value="home Page" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>
<form:form method="POST" action="/SocialNetworking/profileSetting" modelAttribute = "profileModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="profile_email" value=${profile_email}>
	<input type = "hidden" name="profile_password" value=${profile_password}>
    <input type="submit" value="Profile Setting" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<form:form method="POST" action="/SocialNetworking/resetpassword" modelAttribute = "profileModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="profile_email" value=${profile_email}>
	<input type = "hidden" name="profile_password" value=${profile_password}>
    <input type="submit" value="resetpassword" class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>

<tr>
        <td>profile_email</td>
        <td>${profile_email}</td>
    </tr>
    <tr>
        <td>firstName</td>
        <td>${firstName}</td>
    </tr>
    <tr>
        <td>lastName</td>
        <td>${lastName}</td>
    </tr>
     <tr>
        <td>birthday</td>
        <td>${birthday}</td>
    </tr>
     <tr>
        <td>status</td>
        <td>${status}</td>
    </tr>

<%-- <div>${profile_email} </div> --%>
<%-- <div>${firstName}</div> --%>
<%-- <div>${lastName}</div> --%>
<%-- <div>${birthday} </div> --%>
<%-- <div>${status}</div> --%>
<img src = ${picture} alt="IMG DESC" width="100" height="100" / >


</body>
</html>