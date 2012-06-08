<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
  Locale locale = request.getLocale(); 
  ResourceBundle res = ResourceBundle.getBundle("lang.LMTexts", locale);
%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache" />
  <title><%=res.getObject("login_title") %></title>
  <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>

  <jsp:include page="header.jsp" />

  <% 
    Object o;
    o = request.getAttribute("error");
    String error = (o != null) ? "<p id='error'>" + o.toString() + "</p>" : "";
    o = request.getParameter("login");
    String login = (o != null) ? o.toString() : "";
  %>

  <form name="checklogin" method="POST" action="checklogin.do" />
    <%=error %>
    
    <label><%=res.getObject("login_text") %></label>
    <input type="text" name="login" value="<%=login %>" />
    
    <label><%=res.getObject("password_text") %></label>
    <input type="password" name="password" />
    
    <input type="submit" name="valider" />
  </form>

</body>
</html>