<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%
  String c = request.getRequestURI();
  c = c.substring(c.lastIndexOf("/") + 1);

  Locale locale = request.getLocale(); 
  ResourceBundle res = ResourceBundle.getBundle("lang.LMTexts", locale);
%>
<%! 
String isCurrentPage(String current, String page) {
  if (page.equals(current)) return "class='selected'";
  else return "";
}
%> 

<nav>
  <ul class="user">
    <header><%=res.getObject("menu_user") %></header>
    <li><a href="userstats.do" <%=isCurrentPage(c, "userStats.jsp") %>
      title="<%=res.getObject("user_stats_infos") %>"
      ><%=res.getObject("user_stats") %></a></li>
    <li><a href="#" 
      title="<%=res.getObject("user_cal_infos") %>"
      ><%=res.getObject("user_cal") %></a></li>
    <li><a href="#" 
      title="<%=res.getObject("user_perso_infos") %>"
      ><%=res.getObject("user_perso") %></a></li>
    <li><a href="#" 
      title="<%=res.getObject("user_query_infos") %>"
      ><%=res.getObject("user_query") %></a></li>
  </ul>
  <ul class="admin">
    <header><%=res.getObject("menu_admin") %></header>
    <li><a href="#"
      title="<%=res.getObject("admin_stats_infos") %>"
      ><%=res.getObject("admin_stats") %></a></li>
    <li><a href="usersadmin.do" <%=isCurrentPage(c, "usersAdmin.jsp") %>
      title="<%=res.getObject("admin_users_infos") %>"
      ><%=res.getObject("admin_users") %></a></li>
    <li><a href="groupsadmin.do" <%=isCurrentPage(c, "groupsAdmin.jsp") %>
      title="<%=res.getObject("admin_groups_infos") %>"
      ><%=res.getObject("admin_groups") %></a></li>
    <li><a href="teamsadmin.do" <%=isCurrentPage(c, "teamsAdmin.jsp") %>
      title="<%=res.getObject("admin_teams_infos") %>"
      ><%=res.getObject("admin_teams") %></a></li>
    <li><a href="#" 
      title="<%=res.getObject("admin_leave_infos") %>"
      ><%=res.getObject("admin_leave") %></a></li>
    <li><a href="#" 
      title="<%=res.getObject("admin_settings_infos") %>"
      ><%=res.getObject("admin_settings") %></a></li>
  </ul>
</nav>