# Configuring Apache server with Tomcat #

It was necessary that I reexamine from scratch how to configure Apache because my knowledge was starting almost zero and the little I knew was wrong ...

Yohan advised me a few pages to complete the configuration of my apache server with Tomcat :
  * http://www.lea-linux.org/documentations/index.php/Apache_et_Tomcat
  * http://tomcat.apache.org/tomcat-3.3-doc/mod_jk-howto.html

## Apache ##

First thing, where to find what I seek :
  * logs : `/var/log/apache2`

  * configuration files : `/etc/apache2`
  * `apache2.conf` : general configuration, inside are includes of other config files
  * `ports.conf` : contains ports on which Apache listen - Example: Listen 80
  * `mods-available/*` : modules available
  * `mods-enabled/*` : place a symbolic link to that file to activate them. To create a link you can use the command: `% sudo a2enmod [module name]`

## Tomcat ##

Where to find what I seek :
  * logs : `$CATALINA_BASE/logs`
  * configuration files : `$CATALINA_BASE/conf/`

  * Port Configuration: server.xml allows you to configure ports to use.
communication with the server via mod\_jk Apache is on port 8009.

## to configure apache with tomcat ##

On apache.org I can read : "mod\_jk is a replacement to the elderly mod\_jserv. It is a completely new Tomcat-Apache plug-in that handles the communication between Tomcat and Apache."

So I have to install it to allow Tomcat and Apache to communicate.
```
$ aptitude install libapache2-mod-jk
```

Configuration of mod\_jk : I must create a file `/etc/apache2/workers.properties` or update it. It should look like this :
```
#
# workers.tomcat_home should point to the location where you
# installed tomcat. This is where you have your conf, webapps and lib
# directories.
#
workers.tomcat_home=/usr/lib/apache-tomcat
#
# workers.java_home should point to your Java installation. Normally
# you should have a bin and lib directories beneath it.
#
workers.java_home=/usr/lib/jdk
ps=/
#
# The workers that your plugins should create and work with
# 
worker.list=ajp13_worker
#
# Defining a worker named ajp13_worker and of type ajp13
# Note that the name and the type do not have to match.
#
worker.ajp13_worker.port=8009
worker.ajp13_worker.host=localhost
worker.ajp13_worker.type=ajp13
#
# Specifies the load balance factor when used with
# a load balancing worker.
# Note:
#  ----> lbfactor must be > 0
#  ----> Low lbfactor means less work done by the worker.
worker.ajp13_worker.lbfactor=1
```

Tell Apache workers.properties file path and other useful information : I must update the file `/etc/apache2/mods-available/jk.load`, it must contain:
```
# Load the mod_jk module
LoadModule jk_module /usr/lib/apache2/modules/mod_jk.so
# Path to the file workers.properties
JkWorkersFile /etc/apache2/workers.properties
# File containing the shared memory
JkShmFile	/var/log/apache2/mod_jk.shm
# Log file for mod_jk
JkLogFile	/var/log/apache2/mod_jk.log
# Information level : [info/error/debug]
JkLogLevel    debug
# Name format for log files
JkLogStampFormat "[%a %b %d %H:%M:%S %Y] "
# JkOptions indicates to send the size of the SSL key
JkOptions +ForwardKeySize +ForwardURICompat -ForwardDirectories
# Format of the query log
JkRequestLogFormat "%w %V %T"
# When the path of the requested web pages correspond to the following diagrams,
# Apache must transfer them via Tomcat ajp13_worker (adapt as needed)
JkMount /[myApplication]/* ajp13_worker
```

Now I need to tell Apache to send the queries to ajp13\_worker : it's necessary to add the ajp13\_worker into the definition of the virtual host :
```
<VirtualHost *:80>
...
JkMount /mon-appli/* ajp13_worker
</VirtualHost>
```

I want to use SSL so I add it too into VirtualHost for port 443 :

```
<VirtualHost *:443>
...
JkMount /mon-appli/* ajp13_worker
</VirtualHost>
```

I verify that the SSL module is activated : `$ a2enmod ssl`

And reboot Tomcat and Apache :
```
$ /usr/lib/apache-tomcat/bin/shutdown.sh
$ /usr/lib/apache-tomcat/bin/startup.sh
$ /etc/init.d/apache2 restart
```