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
      title="R�capitulatif des cong�s pris ou � prendre">Statistiques</a></li>
    <li><a href="#" 
      title="Demandes de cong�s (calendrier ou liste)">Mes cong�s</a></li>
    <li><a href="#" 
      title="Gestion des donn�es personnelles">Mes infos</a></li>
    <li><a href="#" 
      title="Nouvelle demande de cong�">Poser un cong�</a></li>
  </ul>
</nav>

<nav id="admin">
  <header>Administration</header>
  <ul>
    <li><a href="#" 
      title="R�capitulatif des cong�s pris ou � prendre">Statistiques</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer un utilisateur">Gestion des utilisateurs</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer un groupe">Gestion des groupes</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer une �quipe">Gestion des �quipes</a></li>
    <li><a href="#" 
      title="Ajouter/Modifier/Supprimer un type de cong�">Gestion des types de cong�s</a></li>
    <li><a href="#" 
      title="Dur�e standard, premier jour de la semaine, etc...">R�glages globaux</a></li>
  </ul>
</nav>