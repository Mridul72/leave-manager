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

<nav id="user">
  <header>Mon compte utilisateur</header>
  <ul>
    <li><a href="userstats.jsp" <%=isCurrentPage(c, "userStats.jsp") %>
      title="Récapitulatif des congés pris ou à prendre">Statistiques</a></li>
    <li><a href="#" 
      title="Demandes de congés (calendrier ou liste)">Mes congés</a></li>
    <li><a href="#" 
      title="Gestion des données personnelles">Mes infos</a></li>
    <li><a href="#" 
      title="Nouvelle demande de congé">Poser un congé</a></li>
  </ul>
</nav>

<nav id="admin">
  <header>Administration</header>
  <ul>
    <li><a href="#" 
      title="Récapitulatif des congés pris ou à prendre">Statistiques</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer un utilisateur">Gestion des utilisateurs</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer un groupe">Gestion des groupes</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer une équipe">Gestion des équipes</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer un type de congé">Gestion des types de congés</a></li>
    <li><a href="#" 
      title="Durée standard, premier jour de la semaine, etc...">Réglages globaux</a></li>
  </ul>
</nav>