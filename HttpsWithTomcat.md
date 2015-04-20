# Using HTTPS between Apache and Tomcat #

The information was found on http://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html and simplified explanation on http://java4it.blogspot.fr/2007/01/how-to-tomcat-configure-with-https.html

## Create a keystore for the certificate ##

```
%JAVA_HOME%/bin/keytool -genkey -alias tomcat -keyalg RSA \
  -keystore /path/to/my/keystore
```


I've found my %JAVA\_HOME% on my Xubuntu in `/usr/lib/jvm/java-6-openjdk/jre`

Keytool asks for some informations and creates the keystore file
By default, the key is created in the home directory of the user under which it is run. -keystore permits to specify a different location or filename.

## Edit the Tomcat Configuration File ##

As I have no experience in the use of SSL, I decided to let Tomcat handle SSL implementation and choose between JSSE and APR.

In $CATALINA\_BASE/conf/server.xml I uncomment the lines :

```
<Connector
   port="8443" maxThreads="200"
   scheme="https" secure="true" SSLEnabled="true"
   keystoreFile="${user.home}/.keystore" keystorePass="[password]"
   clientAuth="false" sslProtocol="TLS"/>
```

and I indicates the path to the keystore file and password

But I discover that Tomcat use APR for SSL :
```
<Listener className="org.apache.catalina.core.AprLifecycleListener" SSLEngine="on" />
```

So I modify the connector to force the protocol to define a non-blocking Java SSL Coyote HTTP/1.1 Connector on port 8443 :
```
    <Connector protocol="org.apache.coyote.http11.Http11NioProtocol" port="8443" 
               SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true"
               keystoreFile="${user.home}/.keystore" 
               keystorePass="secret" URIEncoding="UTF-8"
               clientAuth="false" sslProtocol="TLS" />
```

## Edit the web.xml ##

I added the declaration of a new constraint :

```
<security-constraint>
  <display-name>SecureConnection</display-name>
  <web-resource-collection>
    <web-resource-name>HTTPS Connection</web-resource-name>
    <url-pattern>/pageToSecure</url-pattern>
    <http-method>GET</http-method>
    <http-method>POST</http-method>
  </web-resource-collection>
  <user-data-constraint>
    <transport-guarantee>CONFIDENTIAL</transport-guarantee>
  </user-data-constraint>
</security-constraint>
```

## Troubleshooting ##

I reboot Tomcat and then... it doesn't work !!! :/

When asking for a secure page, nothing happens, the browser seems to search indefinitely...

**edit :** Apparently it is not possible to connect apache and tomcat with SSL if the connection to apache itself is not secure ... it seems logical ! ...^^

Now it works perfectly !