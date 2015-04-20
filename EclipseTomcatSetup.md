# General info #

I have learned to my cost that for Java, nothing must be installed from the repositories. It is much easier to download the files directly on the official web sites and copy them once unzipped directly on its hard drive.

# Tomcat7 Setup #

I've download Tomcat7 from http://tomcat.apache.org/ , extract it and rename the folder tomcat7. Then I move it into `/opt/`.

I need to add me as a authorized user into the file `tomcat7/conf/tomcat-users.xml`

`$ sudo nano /opt/tomcat7/conf/tomcat-users.xml`

My file looks like this (I give to root all the available rights) :

```
<tomcat-users>
  <role rolename="manager-gui"/>
  <role rolename="manager-script"/>
  <role rolename="manager-jmx"/>
  <role rolename="manager-status"/>
  <role rolename="admin-gui"/>
  <role rolename="admin-script"/>
  <user username="root" password="root" roles="manager-gui,manager-script,
                                               manager-jmx,manager-status,
                                               admin-gui,admin-script"/>
</tomcat-users>
```

I've encounter some troubles due to a limit of rights onto the tomcat7 folder. To fix it I've give the `/opt/tomcat7` folder to the user group tomcat7 and add my own user account into the tomcat7 group.

From there, I was able to start tomcat with `/opt/tomcat7/bin/startup.sh` and stop it with `/opt/tomcat7/bin/shutdown.sh` and I could publish the servlets into `/opt/tomcat7/webapps`.

# Eclipse setup #

I've download Eclipse from http://www.eclipse.org/downloads/ , I've choose the version : `Eclipse IDE for Java EE Developers, 211 MB, linux 32bits`.

I've extracted it into `/opt/` and add a shortcut to `/opt/eclipse/eclipse` into my home to launch it.

# Tomcat plugin for Eclipse #

The tomcat plugin for Eclipse can be found on http://www.eclipsetotale.com/tomcatPlugin.html#A3

I've download the `tomcatPluginV33.zip` file and extracted it into eclipse/plugins/ directory and restarted Eclipse.

I could immediately notice new icons to start / restart and stop the tomcat server.