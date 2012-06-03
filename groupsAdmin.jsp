<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Group" %>
<%@ page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache" />
  <title>Gestion des groupes</title>
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
    Boolean isValue(String input, String value, HttpServletRequest request) {
      Object o = request.getAttribute(input);
      if (o != null && o.toString().equals(value)) return true;
      return false;
    }
  %>
  
  <section>
    <header>
      <%
        if (!lastValue("group_name", request).equals(""))
          out.println("Modifier un Groupe");
        else out.println("Ajouter un Groupe");
      %>
    </header>
    <form name="newgroup" method="POST" action="groupsadmin.do?action=add" >
      <% 
        Object error = request.getAttribute("error");
        if (error != null) 
          out.println("<p class='error'>" + error.toString() + "</p>");
      %>
      <label>Nom :</label>
      <input type="text" name="group_name"
             <%=lastValue("group_name", request) %> />
      <label>Membres :</label>
      <ul class="checkedList">
      <% 
        ArrayList<User> userslist = (ArrayList<User>) request.getAttribute("users");
        for (int i = 0 ; i < userslist.size() ; i++) {
          String id = String.valueOf(userslist.get(i).getId());
          out.print("<li><label");
          if (i % 2 != 0) out.print(" class='odd'");
          out.print("><input type='checkbox' name='members' value='" + id + "'");
          if (isValue("group_id", id, request)) out.print(" checked");
          out.println("> " + userslist.get(i).getSurname() + " "
                           + userslist.get(i).getName() + "</label></li>");
        }
      %>
      </ul>
      <input type="submit" name="valider" />
    </form>
  </section>
  
  <% 
    
  %>
  <%!
    String groupMembers(int id, ArrayList<User> userslist) {
      String r = "";
      for (int i = 0 ; i < userslist.size() ; i++) {
        if (userslist.get(i).getGroupId() == id) {
          r += userslist.get(i).getSurname() + " ";
          r += userslist.get(i).getName() + ", ";
        }
      }
      if (r != "") r = r.substring(0, r.lastIndexOf(", "));
      return r;
    }
  %>
  
  <script type="text/javascript">
    function update(id) {
      document.location.href = "groupsadmin.do?action=update&id=" + id;
    }
    function del(id, name) {
      if (confirm("Êtes vous sûr de vouloir supprimer le groupe \"" 
                                                               + name + "\" ?")) {
        document.location.href = "groupsadmin.do?action=delete&id=" + id;
      }
    }
  </script>
  
  <section>
    <header>Liste des Groupes</header>
    <table class="dataTable">
      <tr>
        <th>Nom</th>
        <th>Membres</th>
  <% 
    ArrayList<Group> groupslist = (ArrayList<Group>) request.getAttribute("groups");
    for (int i = 0 ; i < groupslist.size() ; i++) {
  %>
      <tr>
        <td><%=groupslist.get(i).getName() %></td>
        <td><%=groupMembers(groupslist.get(i).getId(), userslist) %></td>
        <td class="button"><input type="button" value="modifier" 
          onclick="Javascript:update(<%=groupslist.get(i).getId() %>);"></td>
        <td class="button"><input type="button" value="supprimer"
          onclick="Javascript:del(<%=groupslist.get(i).getId() %>, 
                                  '<%=groupslist.get(i).getName() %>');"></td>
      </tr>
    <%
      }
    %>
    </table>
  </section>

</body>
</html>