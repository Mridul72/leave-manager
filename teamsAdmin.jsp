<%@ page import="java.util.ArrayList" %>
<%@ page import="model.TeamsList" %>
<%@ page import="model.Team" %>
<%@ page import="model.UsersList" %>
<%@ page import="model.User" %>
<%@ page import="java.util.Enumeration" %>
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
  <title><%=res.getObject("team_title") %></title>
  <link rel="stylesheet" href="styles.css" type="text/css">
  <script type="text/javascript" src="jquery-1.7.1.min.js"></script>
</head>
<body>
  
  <jsp:include page="header.jsp" />
  
  <jsp:include page="globalMenu.jsp" />
  
  <% // data recollection
    TeamsList teamslist = (TeamsList) request.getAttribute("teams");
    UsersList userslist = (UsersList) request.getAttribute("users");
    String error = request.getAttribute("error").toString();
  %>
  
  <!-- FORM ADD/UPDATE TEAM -->
  <section id="form_section"
    <% if (error.equals("")) { %> style="display:none;" <% } %>>
    <header id="form_title"><%=res.getObject("add_a_team_text") %></header>
    <p id="error"><%=error %></p>
    
    <form name="newteam" method="POST" action="teamsadmin.do" id="team_form" accept-charset ="utf-8">
      <input type="hidden" name="action" id="action_field"
             value="<%=request.getAttribute("action") %>" />
      <input type="hidden" name="team_id" id="id_field"
             value="<%=request.getAttribute("team_id") %>" />
      <label id="team_name_label"><%=res.getObject("name_text") %></label>
      <input type="text" name="team_name" value="" id="name_field" 
             value="<%=request.getAttribute("team_name") %>" />
      <div class="MultiSelectTransfert">
        <select id="A" multiple>
        <% // filling the select with the list of users
          for (int i = 0 ; i < userslist.getSize() ; i++) {
            User u = userslist.getItem(i);
            out.print("<option id='user" + u.getId() + "' value='" + u.getId());
            out.print("'>" + u.getName() + " " + u.getSurname() + "</option>");
          }
        %>
        </select>
        <div class="Bbuttons">
          <input type="button" onclick="JavaScript: move('A', 'B')" value=">>">
          <input type="button" onclick="JavaScript: move('B', 'A')" value="<<">
        </div>
        <div class="B">
          <label><%=res.getObject("requested_managers_text") %></label>
          <select name="reqmanagers" id="B" multiple>
          </select>
        </div>
        <div class="Cbuttons">
          <input type="button" onclick="JavaScript: move('A', 'C')" value=">>">
          <input type="button" onclick="JavaScript: move('C', 'A')" value="<<">
        </div>
        <div class="C">
          <label><%=res.getObject("optional_managers_text") %></label>
          <select name="optmanagers" id="C" multiple>
          </select>
        </div>
      </div>
      <script type="text/javascript">
        function move(from, to) { // moving an option from a select to another
          $('#' + from + ' option:selected').each(function(i) {
            $(this).remove().appendTo('#' + to).attr("selected",false);
          });
          sortSelect(to);
        }
        function sortSelect(id) { // sorting the options
          var options = [];
          $('#' + id + ' option').each(function(i) {
            options.push({
              val: $(this).val(),
              text: $(this).text()
            });
          });
          options.sort(function(a, b){
            if(a.text > b.text) return 1;
            else if (a.text == b.text) return 0;
            else return -1;
          });
          $('#' + id + ' option').each(function(i) {
            $(this).val(options[i].val).text(options[i].text);
          });
        }
      </script>
      <input type="button" class="submit" value="Valider" 
             onclick="JavaScript:send()"/>
    </form>
  </section>
  
    
  <!-- REFILLING OF FORM IF NECESSARY   -->
  <script type="text/javascript">
  <% 
    String[] managers = (String[]) request.getAttribute("reqmanagers");
    if (managers != null)
      for (int i = 0 ; i < managers.length ; i++)
        out.println("$('#user" + managers[i] + "').remove().appendTo('#B');");
    managers = (String[]) request.getAttribute("optmanagers");
    if (managers != null)
      for (int i = 0 ; i < managers.length ; i++)
        out.println("$('#user" + managers[i] + "').remove().appendTo('#C');");
  %>
  </script>
  
  <!-- ACTIONS IN CASE OF ADD / UPDATE / DELETE -->
  <script type="text/javascript">
    function prepare() {
      $('#form_section').css('display','block');
      $('#error').css('display','none');
      $('#name_field').val("");
      $('#B option, #C option').each(function(i) {
        $(this).remove().appendTo('#A');
      });
    }
    function upd(id, name) {
      prepare();
      $('#form_title').html("<%=res.getObject("upade_a_team_text") %>");
      $('#action_field').val("update");
      $('#id_field').val(id);
      $('#team_name_label').html('<%=res.getObject("team_text") %> '
                               + '&quot;' + name + '&quot; - '
                               + '<%=res.getObject("new_name_text") %> :');
      $('#name_field').val(name);
      $.each(eval($('#reqmanagers' + id).val()),function(i, id) {
        $('#user' + id).remove().appendTo('#B');
      });
      $.each(eval($('#optmanagers' + id).val()),function(i, id) {
        $('#user' + id).remove().appendTo('#C');
      });
    }
    function add() {
      prepare();
      $('#form_title').html('<%=res.getObject("add_a_team_text") %>');
      $('#action_field').val("add");
      $('#team_name_label').html('<%=res.getObject("name_text") %>');
    }
    function del(id, name) {
      if (confirm("<%=res.getObject("delete_team_confirm") %> "
                  + "\"" + name + "\" ?")) {
        $('#action_field').val("delete");
        $('#id_field').val(id);
        $('#team_form').submit();
      }
    }
    function send(id, name) {
      $('#B option, #C option').each(function(i) {
        $(this).attr('selected', "selected");
      });
      $('#team_form').submit();
    }
  </script>
  
  <%! // layout methods
    String teamManagers(HttpServletRequest request,Team t,UsersList userslist,
                        ResourceBundle res) {
      String s = "";
      String optids = "";
      String reqids = "";
      UsersList reqmanagers = t.getReqManagers();
      UsersList optmanagers = t.getOptManagers();
      int i;
      for (i = 0 ; i < reqmanagers.getSize() ; i++) {
        reqids += reqmanagers.getItem(i).getId() + ",";
        s += manager(userslist, reqmanagers.getItem(i), res);
      }
      for (i = 0 ; i < optmanagers.getSize() ; i++) {
        optids += optmanagers.getItem(i).getId() + ",";
        s += manager(userslist, optmanagers.getItem(i), res);
      }
      if (s != "") s = s.substring(0, s.lastIndexOf(", "));
      if (reqids != "") reqids = reqids.substring(0, reqids.lastIndexOf(","));
      if (optids != "") optids = optids.substring(0, optids.lastIndexOf(","));
      s += "<input type='hidden' id='reqmanagers" + t.getId() + "' ";
      s += "value='[" + reqids + "]'>";
      s += "<input type='hidden' id='optmanagers" + t.getId() + "' ";
      s += "value='[" + optids + "]'>";
      return s;
    }
    String manager(UsersList userslist, User manager, ResourceBundle res) {
      String s = "";
      s += manager.getSurname() + " " + manager.getName();
      // if the manager is replaced
      if (manager.getReplacement() != 0) {
        User replacement = userslist.getUser(manager.getReplacement());
        s += " <small>(" + res.getObject("replaced_by_text") + " ";
        s += replacement.getSurname() + " ";
        s += replacement.getName() + ")</small>";
      }
      return s + ", ";
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
  
  <!-- LIST OF TEAMS -->
  <section>
    <header><%=res.getObject("teams_list_text") %></header>
    <table class="dataTable">
      <tr>
        <th><%=res.getObject("team_text") %></th>
        <th><%=res.getObject("managed_by_text") %></th>
        <th><%=res.getObject("members_text") %></th>
        <th class="button"><input type="button" 
            value="<%=res.getObject("add_text") %>" 
            onclick="Javascript:add();"></th>
      </tr>
      <% // filling the table of teams
        for (int i = 0 ; i < teamslist.getSize() ; i++) {
          Team t = teamslist.getItem(i);
        %>
        <tr>
          <td><%=t.getName() %></td>
          <td><%=teamManagers(request, t, userslist, res) %></td>
          <td><%=teamMembers(t.getId(), userslist) %></td>
          <% if (t.getId() != 1) { %>
          <td class="button"><input type="button" 
              value="<%=res.getObject("update_text") %>" 
            onclick="Javascript:upd(<%=t.getId() %>,'<%=t.getName() %>');"></td>
          <td class="button"><input type="button"
              value="<%=res.getObject("delete_text") %>"
            onclick="Javascript:del(<%=t.getId() %>,'<%=t.getName() %>');"></td>
          <% } %>
        </tr>
        <%
        }
      %>
    </table>
  </section>

</body>
</html>