# Multilingual application #

The first source I consulted (http://java.sun.com/developer/technicalArticles/Intl/MultilingualJSP/) was not very clear or easy to understand...
But I found a much clearer tutorial explaining step by step the implementation : http://jmdoudoux.developpez.com/cours/developpons/java/chap-i18n.php#i18n-2

I first created files containing the words or sentences :

```
# file : LMTexts_en.properties
login_title = Connection to the application
login_text = Login :
password_text = Password :
login_error = Login failed: login or password incorrect 
```

```
# file : LMTexts_fr.properties
login_title = Connexion à l'application
login_text = Indentifiant :
password_text = Mot de passe :
login_error = Connexion impossible: Indentifiant ou Mot de passe incorrect
```

I dont forget to create a copy of LMTexts\_en.properties named LMTexts.properties which become the default language.

I've search a moment to find where I must put the files to make it works. I put them into the package `/webapps/[myAppli]/WEB-INF/src/lang/`.

But as the files must be deployed along with the application, it is necessary to stop and restart Tomcat so they are well taken into account: Tomcat rattled by stating that language files were not found...

Then I can call a sentence into my JSP like that :

```
<% 
  // get language of the client
  Locale locale = request.getLocale(); 
  // find the language file
  ResourceBundle res = ResourceBundle.getBundle("lang.LMTexts", locale); 
%>

<%=res.getObject("login_text") // print the text 
%>
```

It's quite simple after all !