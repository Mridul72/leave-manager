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
      title="Récapitulatif des congés pris ou à prendre">Mes statistiques</a></li>
    <li><a href="#" 
      title="Demandes de congés (calendrier ou liste)">Mes congés</a></li>
    <li><a href="#" 
      title="Gestion des données personnelles">Mes infos</a></li>
    <li><a href="#" 
      title="Nouvelle demande de congé">Poser un congé</a></li>
  </ul>
  <ul class="admin">
    <header>Administration</header>
    <li><a href="#"
      title="Récapitulatif des congés pris ou à prendre">Statistiques</a></li>
    <li><a href="usersadmin.do" <%=isCurrentPage(c, "usersAdmin.jsp") %>
      title="Gestion des utilisateur (Ajouter/Modifier/Supprimer)">Utilisateurs</a></li>
    <li><a href="#" 
      title="Gestion des groupes (Ajouter/Modifier/Supprimer)">Groupes</a></li>
    <li><a href="#" 
      title="Gestion des équipes (Ajouter/Modifier/Supprimer)">Équipes</a></li>
    <li><a href="#" 
      title="Gestion des types de congés (Ajouter/Modifier/Supprimer)">Types de congés</a></li>
    <li><a href="#" 
      title="Durée standard, premier jour de la semaine, etc...">Réglages globaux</a></li>
  </ul>
</nav>