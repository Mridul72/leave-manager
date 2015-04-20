# Wiki of leaveManager project #

3rd year bachelor internship :
  * under the direction of : Yohan PARENT
  * carried out by : Aline HUF

Subject : development of a JEE application for leave management on distributed system


---


# Daily Shedule #

## preparation phase : ##
  * **01/04** : **First project meeting**
    * description of the features expected
    * language and technology to be employed (development platform, JEE, tomcat, MVC)
    * project management (Gantt diagram, specifications, project wiki, open source license, versioning)
    * **TODO** :
      * **make some tutorials to learn Java, JEE, ajax, ...**
      * **review open source licenses and select one**
      * **install the development platform (eclipse, tomcat, ...)**
      * **install a versioning system (git ?) and find a repository for project sources (Google Code ?)**
      * **install a wiki and keep a "diary" of the project**
      * **install software to establish a schedule (Gantt diagram)**

  * **03/04** :
    * [Review of open source licenses](OpenSourceLicencesReview.md)
    * Installation of "planner" from repository and testing of the program to do a Gantt diagram
    * [Setup of Eclipse and Tomcat7](EclipseTomcatSetup.md)
    * what is distributed architecture ? http://web.univ-pau.fr/~ecariou/cours/sd-m1/cours-architecture.pdf

  * **04/04 - 06/04** :
    * Study of tutorials from "zero" web site :
      * http://www.siteduzero.com/tutoriel-3-10601-apprenez-a-programmer-en-java.html
      * http://www.siteduzero.com/tutoriel-3-112219-apprenez-a-creer-des-applications-web-dynamiques-avec-jee.html

  * **09/04** :
    * Study of a tutorial about the use of ajax with JEE :
      * http://www.devarticles.com/c/a/Java/J2EE-and-AJAX-AJAX-with-Servlets/1/
      * I did not understand anything, I'll come back later ...
    * [Setup of a wiki and start filling it](WikiSetup.md)

  * **10/04** :
    * Creation of a google code project and [setup of Git](SetupOfGit.md): http://code.google.com/p/leave-manager/
    * Study of a tutorial about the use of Java with databases : http://jmdoudoux.developpez.com/cours/developpons/java/chap-persistence.php
    * creation of a [Lexicon](Lexicon.md) in this wiki to remember what is what because it becomes difficult for me to assimilate all this new vocabulary ...

  * **22/04** : **Second project meeting**
    * explanations about the use of databases and Java (JDBC as connector, use of a driver to communicate with the database, ...)
    * decision was made not to use frameworks (forget Hibernate/Criteria/JPA), but simply JDBC to understand the basics of communication between Java and the database. To avoid additional complications, it was decided to use MySQL as database.
    * suggestion to use a pooling system to avoid repeated disconnections and connections to the database
    * **TODO** :
      * **make some more tutorials to understand the use of JDBC/MySQL**
      * **make some more tutorials to understand how to use a pooling system with tomcat7 and MySQL**
      * **make a state of the art of softwares about leave management**
      * **find how to prevent eclipse to use tabs for indenting Java files ...** (solved for other file types)

  * **23/04** :
    * Rereading of the tutorial http://jmdoudoux.developpez.com/cours/developpons/java/chap-jdbc.php#jdbc-4 in the light of the explanations of Yohan
    * Installation of missing packages, configuration and first tests with the help of https://help.ubuntu.com/community/JDBCAndMySQL
    * [Summary of what has been done to use MySQL with Java](JDBCandMySQL.md)
    * Study of some tutorials to understand how to implement pooling (with no success) :
      * http://www.rndblog.com/how-to-set-up-a-mysql-connection-pool-in-java/
      * http://tomcat.apache.org/tomcat-7.0-doc/jndi-datasource-examples-howto.html
      * http://people.apache.org/~fhanik/jdbc-pool/jdbc-pool.html => is it better to use this ?
      * http://java.sun.com/developer/technicalArticles/J2EE/pooling/

  * **24/04** :
    * Finally the problem of tabs is resolved ! The setting is in Project, Properties, Java CodeStyle, Formatter, Configure WorkSpace, Edit, Indentation. Tip: Ctrl+Shift+F to re-indent

  * **26/04** :
    * http://www3.ntu.edu.sg/home/ehchua/programming/java/JavaWebDBApp.html => Sample pool implemented successfully but I don't understand how it works : I can not reuse the example in my own code... It is necessary to review the basics of how Tomcat works...

  * **27/04** :
    * Looking for a tool to achieve the design of the database with the Merise method (CDM, LRDM, PDM, SQL) :
      * http://bombela-dev.is-a-geek.org/MeriseAcide => unfortunately no longer available in compiled form and no makefile is provided...
      * https://launchpad.net/analysesi => graphics software in java. seems to provide the functionalities expected...

  * **01/05** :
    * Use of pooling JDBC/MySQL : after extensive testing, I finally have a vague idea of ​​how it works
    * I ask myself questions about how the codes can be protected by being in an XML file as in the example on this page : http://www3.ntu.edu.sg/home/ehchua/programming/java/JavaWebDBApp.html