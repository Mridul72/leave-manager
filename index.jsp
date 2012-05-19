<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connexion à l'application</title>
</head>
<body>
  <form name="session" method="POST" action="checklogin.do" />
    <% 
      Object error = request.getAttribute("error");
      if (error != null) 
        out.println("<p class='error'>" + error.toString() + "</p>");
    %>
    <label>Login :</label>
    <input type="text" name="login" 
      <%
        Object login = request.getAttribute("login");
        if (login != null) 
          out.println("value='" + login.toString() + "'");
      %> 
    />
    <label>Password : </</label>
    <input type="password" name="password" />
     
    <input type="submit" name="valider" />
  </form>
</body>
</html>