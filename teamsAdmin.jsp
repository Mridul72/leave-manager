<%@ page import="java.util.ArrayList" %>
<%@ page import="model.TeamsList" %>
<%@ page import="model.Team" %>
<%@ page import="model.UsersList" %>
<%@ page import="model.User" %>
<%@ page import="java.util.Enumeration" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache" />
  <title>Gestion des équipes</title>
  <link rel="stylesheet" href="styles.css" type="text/css">
</head>
<body>
  
  <jsp:include page="header.jsp" />
  
  <jsp:include page="globalMenu.jsp" />
  
  <%! // layout methods
    String stringValue(String input, HttpServletRequest request) {
      Object o = request.getAttribute(input);
      return (o != null)? o.toString() : "";
    }
    int intValue(String input, HttpServletRequest request) {
      Object o = request.getAttribute(input);
      return (o != null)? Integer.valueOf(o.toString()) : 0;
    }
    Boolean isManager(int user_id, int team_id, TeamsList teamslist) {
      User u = teamslist.getTeam(team_id).getManagers().getUser(user_id);
      return (u == null)? false : true;
    }
    String teamManagers(UsersList managers, UsersList userslist) {
      String r = "";
      for (int i = 0 ; i < managers.getSize() ; i++) {
        User manager = managers.getItem(i);
        if (manager.getReplacement() != 0) {
          User replacement = userslist.getUser(manager.getReplacement());
          r += "(<del>" + manager.getSurname() + " ";
          r +=  manager.getName() + "</del>) ";
          r += replacement.getSurname() + " ";
          r += replacement.getName() + ", ";
        }
        else {
          r += manager.getSurname() + " ";
          r += manager.getName() + ", ";
        }
      }
      if (r != "") r = r.substring(0, r.lastIndexOf(", "));
      return r;
    }
    String teamMembers(int team_id, UsersList userslist) {
      String r = "";
      for (int i = 0 ; i < userslist.getSize() ; i++) {
        if (userslist.getItem(i).getTeamId() == team_id) {
          r += userslist.getItem(i).getSurname() + " ";
          r += userslist.getItem(i).getName() + ", ";
        }
      }
      if (r != "") r = r.substring(0, r.lastIndexOf(", "));
      return r;
    }
  %>
  <% // data recollection
    TeamsList teamslist = (TeamsList) request.getAttribute("teams");
    UsersList userslist = (UsersList) request.getAttribute("users");
    int team_id = intValue("team_id", request);
    String team_name = stringValue("team_name", request);
    String action = stringValue("action", request);
  %>
  
  <!-- ERROR MESSAGES -->
  <% 
    Object error = request.getAttribute("error");
    if (error != null) 
      out.println("<section id='error'>" + error.toString() + "</section>");
  %>
  <!-- FORM ADD/UPDATE TEAM -->
  <section>
    <header>
      <%
        if (action.equals("update")) out.println("Modifier une Equipe");
        else out.println("Ajouter un Equipe");
      %>
    </header>
    <form name="newteam" method="POST" action="teamsadmin.do" id="team_form">
      <input type="text" name="action" value="<%=action %>" id="action_field" />
      <input type="text" name="team_id" value="<%=team_id %>" id="id_field" />
      <label>Nom :</label>
      <input type="text" name="team_name" value="<%=team_name %>" />
      <label>Equipe supervisée par :</label>
      <ul class="checkedList">
      <% 
        for (int i = 0 ; i < userslist.getSize() ; i++) {
          User u = userslist.getItem(i);
          int id = u.getId();
          out.print("<li><label");
          if (i % 2 != 0) out.print(" class='odd'");
          out.print("><input type='checkbox' name='managers' value='" +id+ "'");
          if (team_id != 0 && isManager(id, team_id, teamslist)) 
            out.print(" checked");
          out.println("> " + u.getName() + " "
                           + u.getSurname() + "</label></li>");
        }
      %>
      </ul>
      <input type="submit" name="valider" />
    </form>
  </section>
  
  <!-- LIST OF TEAMS -->
  <script type="text/javascript">
    function upd(id, name) {
      document.getElementById("id_field").value = id;
      document.getElementById("action_field").value = "update";
      document.getElementById("team_form").submit();
    }
    function del(id, name) {
      if (confirm("Êtes vous sûr de vouloir supprimer l'équipe \"" 
                                                               + name + "\" ?")) {
        document.getElementById("id_field").value = id;
        document.getElementById("action_field").value = "delete";
        document.getElementById("team_form").submit();
      }
    }
  </script>
  
  <section>
    <header>Liste des Equipes</header>
    <table class="dataTable">
      <tr>
        <th>Equipe</th>
        <th>Supervisée par</th>
        <th>Membres</th>
      </tr>
      <% 
        for (int i = 0 ; i < teamslist.getSize() ; i++) {
          Team t = teamslist.getItem(i);
        %>
        <tr>
          <td><%=t.getName() %></td>
          <td><%=teamManagers(t.getManagers(), userslist) %></td>
          <td><%=teamMembers(t.getId(), userslist) %></td>
          <td class="button"><input type="button" value="modifier" 
            onclick="Javascript:upd(<%=t.getId() %>,'<%=t.getName() %>');"></td>
          <td class="button"><input type="button" value="supprimer"
            onclick="Javascript:del(<%=t.getId() %>,'<%=t.getName() %>');"></td>
        </tr>
        <%
        }
      %>
    </table>
  </section>

</body>
</html>