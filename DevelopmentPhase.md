# Daily Shedule #

## development phase : ##

  * **26/05** :
    * writing code: identification system, session creation, exchange security through HTTPS
    * Adding SSL security on Tomcat seams to be simple, but I met some difficulties : HttpsWithTomcat

  * **28/05** :
    * writing code: creation of an object UserRigths to manage access privileges. Creation of an access filter.

  * **29/05** :
    * small adding. Just the inclusion of a global menu and a header into the JSPs. Conversion of the JSPs into HTML5 and adding of a CSS3 style-sheet.

  * **01/05** :
    * writing code: creation of objets User, UsersList, Group, GroupsList, Team, TeamsList

  * **03/05** : **small project meeting**
    * Explanations about SSL : safety must be foremost among the server and the user therefore between apache and the client's browser ...
    * **TODO** :
      * **configure SSL between apache and the client's browser**
      * **lay out the specifications and features list**

  * **04/05** :
    * writing code: administration page of teams almost complete (JSP), it remains to implement the actions delete and update into the servlet.
    * Adding of a security for requests sent to the database : use of PreparedStatement and creation of an object SafeQuery

  * **05/05** :
    * some difficulties with the interactivity of the web pages : is J2EE only working on the server side ? What makes it more powerful than PHP in this case ?

  * **06/05** :
    * Extensive use of JavaScript to handle the interactivity of web pages... this is not satisfactory ... the code becomes unreadable. Should go and look at jQuery maybe ?

  * **07/05** :
    * Study of internationalization : http://jmdoudoux.developpez.com/cours/developpons/java/chap-i18n.php#i18n-2

  * **08/05** :
    * Editing pages to include the internationalization...
    * this time it is final : the code is unreadable ! Java code to format the data from the database, plus the javascript code also generated in part by java, more internationalization into the java code, into javascript and local methods in the JSP : even I I can not find my way...
    * There must be a method to organize all this stuff and make them more readable...
    * study of some JQuery tutorials :
      * http://www.w3schools.com/jquery/default.asp
      * http://www.siteduzero.com/tutoriel-3-160891-jquery-ecrivez-moins-pour-faire-plus.html
    * a small dose of JQuery, it's then more readable... but I think that something can be done to simplify the display of data with Java.

  * **09/05** :  **Fifth project meeting**
    * Long discussion about the apache configuration
    * Solution found for UTF-8 problem of encoding of database
    * Problem of unit tests discussed, the solution is always to find
    * Explanation about the division of labor between the servlet and JSP: need to redesign pages teamsAdmin.jsp and STeamsAdmin.java
    * Discussion about the use of AJAX or not... conclusion on the fact that it is not useful in this current case. Perhaps it will be useful somewhere else in the application...
    * **TODO** :
      * **find how to do these f...g unit tests**
      * **configure Apache to have a SSL connection between client and Apache and redirection to tomcat.**
      * **redesign slightly the teamsAdmin pages**

  * **10/05** :
    * Apache is set up and the SSL connection is established.

  * **12/05** :
    * junit : solution not found... trying to do test with a servlet.

  * **15/05** :
    * un problème pour obtenir un texte avec des accents partir d'un formulaire est apparu, recherche d'une solution :
      * http://www.whirlycott.com/phil/2005/05/11/building-j2ee-web-applications-with-utf-8-support/
