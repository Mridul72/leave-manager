<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache" />
  <title>Connexion à l'application</title>
  <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>

  <jsp:include page="header.jsp" />

  <form name="checklogin" method="POST" action="checklogin.do" />
    <% 
      Object error = request.getAttribute("error");
      if (error != null) 
        out.println("<p class='error'>" + error.toString() + "</p>");
    %>
    <label>Login :</label>
    <input type="text" name="login" 
      <%
        Object login = request.getParameter("login");
        if (login != null) 
          out.println("value='" + login.toString() + "'");
      %> 
    />
    <label>Password : </label>
    <input type="password" name="password" />
     
    <input type="submit" name="valider" />
  </form>

</body>
</html>