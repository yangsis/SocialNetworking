<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group Setting Page</title>
</head>
<body>
<h2>Spring Page Redirection</h2>
<p>You can choose 4 members</p>
<form:form method="POST" action="/SocialNetworking/inbox" modelAttribute = "chatSettingsModel" >
<table>
    <tr>
    <td>
     <tr><td><input type = "hidden" name="chat_email" value = ${chat_email}></td></tr>
     <tr><td><input type = "hidden" name="chat_password" value = ${chat_password}></td></tr>
     <tr>    
     <select name="partner">
   	 <option value="${partner}" selected="selected">${partner}</option>
   	 <c:forEach items="${friend}" var ="friend">
   	 	<option value="${friend}">${friend}</option>
   	 </c:forEach>
  	</select>
     </tr>
    <input type="submit" value="Done"/>
    </td>
    </tr>
</table> 
</form:form>
<td>${errorOfcreateChat }</td>
</body>
</html>