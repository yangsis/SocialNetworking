<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Friend Search Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>friend search page</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">search friend</p>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "friendSearchModel">
<table>
    <tr>
    <td>
    <input type = "hidden" name="friendSearchModel_email" value=${friendSearchModel_email}>
	<input type = "hidden" name="friendSearchModel_password" value=${friendSearchModel_password}>
	<input type = "hidden" name="friendSearchModel_search_email" value=${friendSearchModel_search_email}>
	<input type = "hidden" name="friendSearchModel_search_yes" value=${friendSearchModel_search_yes}>
    <input type="submit" value="Home Page"  class="container btn color-1 material-design"/>
    </td>
    </tr>
</table> 
</form:form>


<form:form method="POST" action="/SocialNetworking/friendSearch" modelAttribute = "friendSearchModel">
<table id="lgn">
    <tr>
    <td>
    <input type = "hidden" name="friendSearchModel_email" value=${friendSearchModel_email}>
	<input type = "hidden" name="friendSearchModel_password" value=${friendSearchModel_password}>
	<input type = "hidden" name="friendSearchModel_search_yes" value="no">
	<tr><td>Friend Email: </td><td><input name="friendSearchModel_search_email" type="textbox" value = ${friendSearchModel_search_email}></td></tr>
    <input type="submit" value="Search" class="container btn color-1 material-design"/>
    <div style="color:red">${errorOfSearch}</div>
    </td>
    </tr>
</table> 
</form:form>

<c:choose>
<c:when test = "${friendSearchModel_search_email != null && friendSearchModel_search_yes == 'yes'}"> 
<p id="lgn">search result</p>
<tr><td>First Name: </td><td><input name="friendSearch_firstName" type="textbox" value = ${friendSearch_firstName}></td></tr>
<tr><td>Last Name: </td><td><input name="friendSearch_lastName" type="textbox" value = ${friendSearch_lastName}></td></tr>
<tr><td>Date of Birth: </td><td><input name="friendSearch_birthday" type="textbox" value = ${friendSearch_birthday}></td></tr>
<tr><td>Status: </td><td><input name="friendSearch_status" type="textbox" value = ${friendSearch_status}></td></tr>
<td>Picture: </td><img src = ${friendSearch_picture} alt="IMG DESC" width="100" height="100" / >
<form:form method="POST" action="/SocialNetworking/friendSearch" modelAttribute = "friendSearchModel">
<table id="lgn">
				<input type = "hidden" name="friendSearchModel_email" value=${friendSearchModel_email}>
				<input type = "hidden" name="friendSearchModel_password" value=${friendSearchModel_password}>               
                <input type = "hidden" name="friendSearchModel_search_email" value = ${friendSearchModel_search_email}>              
                <input type = "hidden" name="friendSearchModel_search_yes" value= ${friendSearchModel_search_yes}>    	
                <tr><td colspan="2" align="right"><input type="submit" value="Add Friend" class="container btn color-1 material-design"></td></tr>
            </table> 
</form:form>



</c:when>
</c:choose>
</body>
</html>