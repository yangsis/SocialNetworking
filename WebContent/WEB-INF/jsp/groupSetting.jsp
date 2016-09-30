<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Group Setting Page</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>profile setting page</h1>
<p id="dtl">You can choose 4 members</p>
<p id="logn">create group</p>
<form:form method="POST" action="/SocialNetworking/groupList" modelAttribute = "groupSettingsModel" enctype="multipart/form-data">
<table id="lgn">
    <tr>
    <td>
     <tr><td>Group Name: </td><td><input name="groupName" type="textbox" value = ${groupName}></td></tr>
     <tr><td>Description: </td><td><input name="description" type="textbox" value = ${description}></td></tr>
     <tr><td><input type = "hidden" name="groupSetting_email" value = ${groupSetting_email}></td></tr>
     <tr><td><input type = "hidden" name="groupSetting_password" value = ${groupSetting_password}></td></tr>
     <tr><td><input type = "hidden" name="groupOwner" value = ${groupOwner}></td></tr>
     <tr><td><input type = "hidden" name="groupID" value = ${groupID}></td></tr>
     <tr><td><input type = "hidden" name="oldGroupUser" value = ${oldGroupUser}></td></tr>
     <tr><td><input type = "hidden" name="postList" value = ${postList}></td></tr>
     <tr><td>Picture: </td><td><input name="groupPicture" type="file" value = ${groupPicture}></td></tr>
     <tr><td><input type = "hidden" name="user1" value = ${user1}></td></tr>
      <tr><td><input type = "hidden" name="user2" value = ${user2}></td></tr>
       <tr><td><input type = "hidden" name="user3" value = ${user3}></td></tr>
        <tr><td><input type = "hidden" name="user4" value = ${user4}></td></tr>
     <tr>    
     <select name="user1">
   	 <option value="${user1}" selected="selected">${user1}</option>
   	 <c:forEach items="${group }" var ="groupList1">
   	 	<option value="${groupList1}">${groupList1}</option>
   	 </c:forEach>
  	</select>
     </tr> 
     <tr>    
     <select name="user2">
   	 <option value="${user2}" selected="selected">${user2}</option>
   	 <c:forEach items="${group }" var ="groupList2">
   	 	<option value="${groupList2}">${groupList2}</option>
   	 </c:forEach>
  	</select>
     </tr> 
     <tr>    
     <select name="user3">
   	 <option value="${user3}" selected="selected">${user3}</option>
   	 <c:forEach items="${group }" var ="groupList3">
   	 	<option value="${groupList3}">${groupList3}</option>
   	 </c:forEach>
  	</select>
     </tr> 
     <tr>    
     <select name="user4">
   	 <option value="${user4}" selected="selected">${user4}</option>
   	 <c:forEach items="${group }" var ="groupList4">
   	 	<option value="${groupList4}">${groupList4}</option>
   	 </c:forEach>
  	</select>
     </tr>
    <input type="submit" value="Done" class="container btn color-1 material-design" />
    </td>
    </tr>
</table> 
</form:form>
</body>
</html>