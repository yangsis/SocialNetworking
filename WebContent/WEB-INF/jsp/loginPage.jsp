<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login and Register</title>
<link href="<c:url value="/resources/css/stylesheets_page1.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/button_stylesheets.css" />" rel="stylesheet">
</head>
<body>
<h1>Friend list</h1>
<p id="dtl">Social Media Web Application</p>
<p id="logn">Login</p>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "loginModel">
<table id="lgn">
                <tr><td>User Name: </td><td><input name="login_email" type="textbox"></td></tr>
                <tr><td>Password: </td><td><input name="login_password" type="textbox"></td></tr>
                <tr><td colspan="2" align="center"><input type="submit" value="Submit" class="container btn color-1 material-design" ></td></tr>
            </table>  
            <div style="color:red">${errorOflogin}</div>
</form:form>
<p id="rgstr">Register</p>
<form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "registerModel">
<table id="rgtr">
                <tr><td>First Name: </td><td><input name="firstName" type="textbox"></td></tr>
                <tr><td>Last Name: </td><td><input name="lastName" type="textbox"></td></tr>
                <tr><td>Email: </td><td><input name="register_email" type="textbox"></td></tr>
                <tr><td>Password: </td><td><input name="register_password" type="password"></td></tr>
                <tr><td>Date of Birth: </td><td><input name="birthday" type="textbox"></td></tr>
                <tr><td>Status: </td><td><input name="status" type="textbox"></td></tr>
                <tr><td>Security Question: </td><td><input name="securityQ" type="textbox"></td></tr>
                <tr><td>Security Answer: </td><td><input name="securityA" type="textbox"></td></tr>
                <tr><td colspan="2" align="center"><input type="submit" value="Register" class="container btn color-1 material-design"></td></tr>
            </table>  
            <div style="color:red">${errorOfregister}</div>
</form:form>
<p id="fg">forget password</p>
<form:form method="POST" action="/SocialNetworking/loginPage" modelAttribute = "registerModel">
<table id="lgn">
                
                <tr><td>Email: </td><td><input name="register_email" type="textbox"></td></tr>
                <tr><td>Security Question: </td><td><input name="securityQ" type="textbox"></td></tr>
                <tr><td>Security Answer: </td><td><input name="securityA" type="textbox"></td></tr>
                <tr><td colspan="2" align="right"><input type="submit" value="forget password" class="container btn color-1 material-design"></td></tr>
            </table>  
            <div style="color:red">${errorOffinding}</div>
</form:form>

</body>
</html>


<%-- <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> --%>
<!-- <html> -->
<!-- <head> -->
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!-- <title>Login and Register</title> -->
<!-- </head> -->
<!-- <body> -->
<!-- <h2>Spring Page Redirection</h2> -->
<!-- <p>Click below button to redirect the result to new page</p> -->
<%-- <form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "loginModel"> --%>
<!-- <table> -->
<!--                 <tr><td>User Name: </td><td><input name="login_email" type="textbox"></td></tr> -->
<!--                 <tr><td>Password: </td><td><input name="login_password" type="textbox"></td></tr> -->
<!--                 <tr><td colspan="2" align="right"><input type="submit" value="Submit"></td></tr> -->
<!--             </table>   -->
<%--             <div style="color:red">${errorOflogin}</div> --%>
<%-- </form:form> --%>

<%-- <form:form method="POST" action="/SocialNetworking/homePage" modelAttribute = "registerModel"> --%>
<!-- <table> -->
<!--                 <tr><td>First Name: </td><td><input name="firstName" type="textbox"></td></tr> -->
<!--                 <tr><td>Last Name: </td><td><input name="lastName" type="textbox"></td></tr> -->
<!--                 <tr><td>Email: </td><td><input name="register_email" type="textbox"></td></tr> -->
<!--                 <tr><td>Password: </td><td><input name="register_password" type="password"></td></tr> -->
<!--                 <tr><td>Date of Birth: </td><td><input name="birthday" type="textbox"></td></tr> -->
<!--                 <tr><td>Status: </td><td><input name="status" type="textbox"></td></tr> -->
<!--                 <tr><td>Security Question: </td><td><input name="securityQ" type="textbox"></td></tr> -->
<!--                 <tr><td>Security Answer: </td><td><input name="securityA" type="textbox"></td></tr> -->
<!--                 <tr><td colspan="2" align="right"><input type="submit" value="Register"></td></tr> -->
<!--             </table>   -->
<%--             <div style="color:red">${errorOfregister}</div> --%>
<%-- </form:form> --%>


            
<!-- </body> -->
<!-- </html> -->
