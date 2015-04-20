FAQ / PROBLEMS / TO DO :

MD5 : http://respawner.fr/blog/index.php?post/2008/09/03/Generation-d-un-MD5-avec-Java


**GRAVE: The web application [/leaveManager] appears to have started a thread named [Statement Cancellation Timer](MySQL.md) but has failed to stop it. This is very likely to create a memory leak.**

this reflects the fact that the application server Tomcat stops in force. During development of the application, if the server is started, it may be that changing context causes the server to crash. In this case, the current threads of execution remain active and cause this alert.


---

**JUnit : no connexion to database...**

**Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial
echec : pas de connexion**

The creation of a context don't seams to work... I do not want to go through the browser for the tests... how to do it ?
Test classes must also be declared in the context to allow access to their resources. To do: find out how to report JUnit classes in the context.

but what is the syntax ?

try the question on a dedicated IRC... no response...


---

**When using the system by several users simultaneously, how to avoid errors due to the simultaneous modification of the same data? How to test that the data has changed between when it was presented to the user and the time it has validated its amendment?**

We can not always prevent the human user to do just about anything. We must accept that not everything can be took over by the software.

It is possible to protect a set of sql queries so that they are not interrupted and cancel all if one fails, but we can not prevent a user from changing what another has just correct.


---

**Kesako ???**

Allow multithreading

<%@ isThreadSafe=« true » %>

This means that a servlet can be distributed to multiple users simultaneously. With "false" one could block the servlet so it can be used by a single user at a time.


---

TO DO

make a filter for each page FLogin, plus a filter FAdminOnly or FManagerOnly for certain pages.
=> Redirect to login page if no session ID
=> Redirect to home if insufficient rights to a page


---

Home:
integrate a menu based on the user's rights
display information about the user


---

to read for personal culture :
  * Spring http://ego.developpez.com/spring/
  * swing for server part / gwt for web interface ?