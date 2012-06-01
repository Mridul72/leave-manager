<%
  String c = request.getRequestURI();
  c = c.substring(c.lastIndexOf("/") + 1);
%>
<%! 
String isCurrentPage(String current, String page) {
  if (page.equals(current)) return "class='selected'";
  else return "";
}
%> 

<nav>
  <ul class="user">
    <header>Mon compte utilisateur</header>
    <li><a href="userstats.do" <%=isCurrentPage(c, "userStats.jsp") %>
      title="R�capitulatif des cong�s pris ou � prendre">Mes statistiques</a></li>
    <li><a href="#" 
      title="Demandes de cong�s (calendrier ou liste)">Mes cong�s</a></li>
    <li><a href="#" 
      title="Gestion des donn�es personnelles">Mes infos</a></li>
    <li><a href="#" 
      title="Nouvelle demande de cong�">Poser un cong�</a></li>
  </ul>
  <ul class="admin">
    <header>Administration</header>
    <li><a href="#"
      title="R�capitulatif des cong�s pris ou � prendre">Statistiques</a></li>
    <li><a href="usersadmin.do" <%=isCurrentPage(c, "usersAdmin.jsp") %>
      title="Gestion des utilisateur (Ajouter/Modifier/Supprimer)">Utilisateurs</a></li>
    <li><a href="#" 
      title="Gestion des groupes (Ajouter/Modifier/Supprimer)">Groupes</a></li>
    <li><a href="#" 
      title="Gestion des �quipes (Ajouter/Modifier/Supprimer)">�quipes</a></li>
    <li><a href="#" 
      title="Gestion des types de cong�s (Ajouter/Modifier/Supprimer)">Types de cong�s</a></li>
    <li><a href="#" 
      title="Dur�e standard, premier jour de la semaine, etc...">R�glages globaux</a></li>
  </ul>
</nav>