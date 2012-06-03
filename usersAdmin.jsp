<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache" />
  <title>Gestion des utilisateurs</title>
  <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>
  
  <jsp:include page="header.jsp" />
  
  <jsp:include page="globalMenu.jsp" />
  
  <%!
    String lastValue(String input, HttpServletRequest request) {
      Object o = request.getAttribute(input);
      String s = "";
      if (o != null) s = "value='" + o.toString() + "'";
      return s;
    }
    String isValue(String input, String value, HttpServletRequest request) {
      Object o = request.getAttribute(input);
      String s = "";
      if (o != null && o.toString().equals(value)) s = "selected";
      return s;
    }
  %>
  
  <section>
    <header>
      <%
        if (!lastValue("user_name", request).equals(""))
          out.println("Modifier un Utilisateur");
        else out.println("Ajouter un Utilisateur");
      %>
    </header>
    <form name="newuser" method="POST" action="usersadmin.do?action=add" >
      <% 
        Object error = request.getAttribute("error");
        if (error != null) 
          out.println("<p class='error'>" + error.toString() + "</p>");
      %>
      <label>Nom :</label>
      <input type="text" name="user_name"
             <%=lastValue("user_name", request) %> />
      <label>Prénom : </label>
      <input type="text" name="surname"
             <%=lastValue("surname", request) %> />
      <label>Genre : </label>
      <select name="gender">
        <option value="f" <%=isValue("gender", "f", request) %>>femme</option>
        <option value="m" <%=isValue("gender", "m", request) %>>homme</option>
      </select>
      <label>Email : </label>
      <input type="text" name="email"
             <%=lastValue("email", request) %> />
      <label>Date de prise de fonctions : </label>
      <input type="text" name="taking_office"
             <%=lastValue("taking_office", request) %> />
      <label>Date de cessation de service : </label>
      <input type="text" name="termination"
             <%=lastValue("termination", request) %> />
             
      <input type="submit" name="valider" />
    </form>
  </section>
  
  <script type="text/javascript">
    function update(id) {
      document.location.href = "usersadmin.do?action=update&id=" + id;
    }
    function del(id) {
      if (confirm("Êtes vous sûr de vouloir supprimer cet utilisateur ?")) {
        document.location.href = "usersadmin.do?action=delete&id=" + id;
      }
    }
  </script>
  
  <section>
    <header>Liste des Utilisateurs</header>
    <table class="dataTable">
      <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Genre</th>
        <th>Email</th>
        <th>Prise de fonctions</th>
        <th>Fin de contrat</th>
        <th>Remplacé par</th>
        <th>Groupe</th>
        <th>Rattaché au service</th>
  <% 
    ArrayList<User> userslist = (ArrayList<User>) request.getAttribute("users");
    for (int i = 0 ; i < userslist.size() ; i++) {
  %>
      <tr>
        <td><%=userslist.get(i).getName() %></td>
        <td><%=userslist.get(i).getSurname() %></td>
        <td><%=userslist.get(i).getGender() %></td>
        <td><%=userslist.get(i).getEmail() %></td>
        <td><%=userslist.get(i).getTakingOffice() %></td>
        <td><% Object t = userslist.get(i).getTermination();
               if (t != null) out.print(t); %></td>
        <td><% int r = (int) userslist.get(i).getReplacement();
               if (r != 0) out.print(r); %></td>
        <td><%=userslist.get(i).getGroupName() %></td>
        <td><%=userslist.get(i).getTeamName() %></td>
        <td class="button"><input type="button" value="modifier" 
          onclick="Javascript:update(<%=userslist.get(i).getId() %>);"></td>
        <td class="button"><input type="button" value="supprimer"
          onclick="Javascript:del(<%=userslist.get(i).getId() %>);"></td>
      </tr>
    <%
      }
    %>
    </table>
  </section>

</body>
</html>