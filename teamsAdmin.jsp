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
    Boolean isManager(int user_id, HttpServletRequest request) {
      String[] managers = (String[]) request.getAttribute("managers");
      if (managers != null) {
        for (int i = 0 ; i < managers.length ; i++)
          if (Integer.valueOf(managers[i]) == user_id) return true;
      }
      return false;
    }
    String teamManagers(UsersList managers, UsersList userslist, int team_id) {
      String r = "";
      ArrayList<Integer> ids = new ArrayList<Integer>();
      for (int i = 0 ; i < managers.getSize() ; i++) {
        User manager = managers.getItem(i);
        ids.add(manager.getId());
        // if the manager is replaced
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
      if (r != "") r = r.substring(0, r.lastIndexOf(", ")); //suppress last coma
      // ids of the managers for update
      r += "<input type='hidden' id='managers" + team_id + "' value='[";
      for (int i = 0 ; i < ids.size() ; i++) {
        if (i != 0) r += ",";
        r += ids.get(i);
      }
      r += "]'>";
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
    String action = (String) request.getAttribute("action");
    if (action == null) action = "add";
    Object error = request.getAttribute("error");
  %>
  
  <!-- FORM ADD/UPDATE TEAM -->
  <section <%= (error != null) ? "" : "style='display:none;'" %>
           id="form_section">
    <header id="form-title">Ajouter une équipe</header>
    <%= (error != null)? "<p id='error'>" + error.toString() + "</p>" : 
                         "<p id='error'></p>" %>
    <form name="newteam" method="POST" action="teamsadmin.do" id="team_form">
      <input type="text" name="action" value=""  id="action_field" />
      <input type="text" name="team_id" value="" id="id_field" />
      <label id="team_name_label">Nom :</label>
      <input type="text" name="team_name" value="" id="name_field" />
      <label>Equipe supervisée par :</label>
      <ul class="checkedList">
      <% 
        for (int i = 0 ; i < userslist.getSize() ; i++) {
          User u = userslist.getItem(i);
          int id = u.getId();
          out.print("<li><label");
          if (i % 2 != 0) out.print(" class='odd'");
          out.print("><input type='checkbox' id='user" + id + "' ");
          out.print("name='managers' value='" + id + "'>");
          out.println(u.getName() + " " + u.getSurname() + "</label></li>");
        }
      %>
      <!-- REFILLING OF FORM IF NECESSARY   -->
      <script type="text/javascript">
      <% 
        if (team_id != 0 && team_name != "") {
          out.println("document.getElementById('action_field').value = '" +
                      action + "'");
          out.println("document.getElementById('id_field').value = '" +
                      team_id + "'");
          out.println("document.getElementById('name_field').value = '" +
                      team_name + "'");
          for (int i = 0 ; i < userslist.getSize() ; i++) {
            int id = userslist.getItem(i).getId();
            if (isManager(id, request)) {
              out.println("document.getElementById('user" + 
                          id + "').checked = true");
            }
          }
        }
      %>
      </script>
      </ul>
      <input type="submit" name="valider" />
    </form>
  </section>
  
  <!-- ACTIONS IN CASE OF ADD / UPDATE / DELETE -->
  <script type="text/javascript">
    function upd(id, name) {
      document.getElementById("form_section").style.display = "block";
      document.getElementById("error").style.display = "none";
      document.getElementById("team_name_label").innerHTML = "Equipe \"" + 
                              name + "\" - Nouveau nom :"
      document.forms['newteam'].reset();
      document.getElementById("action_field").value = "update";
      document.getElementById("form-title").innerHTML = "Modifier une équipe"
      document.getElementById("id_field").value = id;
      document.getElementById("name_field").value = name;
      var ids = eval(document.getElementById("managers" + id).value);
      for(var i= 0; i < ids.length; i++) // check managers
        document.getElementById("user" + ids[i]).checked = true
    }
    function add() {
      document.getElementById("form_section").style.display = "block";
      document.getElementById("error").style.display = "none";
      document.getElementById("team_name_label").innerHTML = "Nom :"
      document.forms['newteam'].reset();
      document.getElementById("action_field").value = "add";
      document.getElementById("form-title").innerHTML = "Ajouter une équipe"
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
  
  <!-- LIST OF TEAMS -->
  <section>
    <header>Liste des Equipes</header>
    <table class="dataTable">
      <tr>
        <th>Equipe</th>
        <th>Supervisée par</th>
        <th>Membres</th>
        <th class="button"><input type="button" value="ajouter" 
            onclick="Javascript:add();"></th>
      </tr>
      <% 
        for (int i = 0 ; i < teamslist.getSize() ; i++) {
          Team t = teamslist.getItem(i);
        %>
        <tr>
          <td><%=t.getName() %></td>
          <td><%=teamManagers(t.getManagers(), userslist, t.getId()) %></td>
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