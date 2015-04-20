# Troubleshooting encoding utf-8 #

sources :
  * http://blog.sidu.in/2007/05/tomcat-and-utf-8-encoded-uri-parameters.html
  * http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#Add_Default_Character_Set_Filter
  * http://www.whirlycott.com/phil/2005/05/11/building-j2ee-web-applications-with-utf-8-support/

When recovering data from the database or at the store, I encountered encoding problems.
The utf-8 seems the encoding used by default Tomcat. To solve the problem, I had to check at each level that the encoding was utf-8.

I first modify the header of my JSPs :
```
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache" />
...
```

The fixed text appeared with accents but data from the base were badly encoded.

After a brief check, the problem seemed to come from the database itself, the query :
```
CREATE DATABASE leaveManagerDB
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
```
has not the expected effect, I can verify it with :
```
mysql> SET NAMES UTF8;
Query OK, 0 rows affected (0.00 sec)

mysql> SELECT * FROM teams ORDER by team_name;
+---------+-------------------------+
| team_id | team_name               |
+---------+-------------------------+
|       1 | Ã©quipe par dÃ©faut     |
|       2 | Equipe 1                |
|       3 | Autre Ã©quipe           |
+---------+-------------------------+
3 rows in set (0.00 sec)
```

The database was not in utf-8... I've drop the database and set :
```
CREATE DATABASE leaveManagerDB CHARACTER SET utf8 COLLATE utf8_general_ci;
```

Then :
```
mysql> SET NAMES UTF8;
Query OK, 0 rows affected (0.00 sec)

mysql> SELECT * FROM teams ORDER by team_name;
+---------+---------------------+
| team_id | team_name           |
+---------+---------------------+
|       1 | équipe par défaut   |
|       2 | Equipe 1            |
|       3 | Autre équipe        |
+---------+---------------------+
3 rows in set (0.01 sec)
```

But as mysql does not seem to work in utf-8 by default, the display of utf-8 was not done well, as I did not ask explicitly displaying utf-8, to arrange this, and to set definitely the encoding, I added a conf file with instructions :
`file:///etc/mysql/conf.d/mysqld_encoding.cnf`
```
[client]
default-character-set=utf8

[mysqld]
character-set-server=utf8
collation-server=utf8_unicode_ci
```

From there, the text display to work but the recovery of data entered by a user in a form was a problem : the data retrieved from the "controler" servlet  were not in utf-8. To fix this, I added a filter operating before setting the encoding ServletRequest and ServletResponse object.

The following filter is designed to force the encoding of the exchanges to utf-8 :

```
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetEncodingFilter implements Filter {
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
      throws IOException, ServletException {
    
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");
    
    chain.doFilter(req, resp);
  }

  public void destroy() {
  }
}
```