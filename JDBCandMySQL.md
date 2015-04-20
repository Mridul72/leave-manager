# How to connect My Servlet to a MySQL Database #

As explained in https://help.ubuntu.com/community/JDBCAndMySQL I have installed some packages :
  * `sudo apt-get install mysql-server`
  * `sudo apt-get install mysql-client`
  * `sudo apt-get install libmysql-java`

Then I have created a database to use with Java (leaveManagerDB) and created a user with access to that table with :
  * `grant all privileges on [database].* to [user]@localhost identified by "[password]";`
  * `flush privileges;`

Apparently, with tomcat7 and MySQL 5.1.61 it's not necessary to add `CLASSPATH=".:/usr/share/java/mysql.jar"` into /etc/environment ...
But it's necessary to add `/usr/share/java/mysql-connector-java.jar` into Eclipse : into Properties/Java Build Path, and Libraries tab.

Then I've done my first steps to test what is explained in http://jmdoudoux.developpez.com/cours/developpons/java/chap-jdbc.php#jdbc-6-3 :

This code is dirty I know... but it's just a test.

```
    package dao;
    
    import java.sql.*;
    import java.util.Properties;
    
    public class BddAccess {
    
      private static void affiche(String message) {
        System.out.println(message);
      }
    
      private static void arret(String message) {
        System.err.println(message);
        System.exit(99);
      }
      
      public static void main(java.lang.String[] args) {
         Connection c = null;
         Statement stmt = null;
         String request = "";
         int nbMaj = 0;
         ResultSet results = null;
         
         // driver loading    
         try {
           Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException e) {
           arret("Could not load the driver jdbc:mysql");
         }
    
         // connexion to database
         affiche("connexion to database");
         try {
            String DBurl = "jdbc:mysql://127.0.0.1/leaveManagerDB";
            Properties p = new Properties();
            p.put("user","alinehuf");
            p.put("password","secret");
            c = DriverManager.getConnection(DBurl, p);
         } catch (SQLException e) {
            arret("fail to connect to database");
         }
         
         // test table already exists
         affiche("articles exists ?");
         request = "DESCRIBE articles";         
         try {            
            stmt = c.createStatement();
            nbMaj = stmt.executeUpdate(request);
            if (nbMaj == 0) {
              
              // creation of articles table    
              affiche("no, I create articles table.");
              request = "CREATE TABLE articles (" +
                        "  id INT(5) not null AUTO_INCREMENT," +
                        "  article CHAR(10)," +
                        "  nombre INT(5)," +
                        "  PRIMARY KEY(id))";
              
              try {
                stmt = c.createStatement();
                // for treatment of DDL type (Data Definition Langage), 
                // as the creation of a table, executeUpdate return 0
                stmt.executeUpdate(request);
              } catch (SQLException e) {
                  e.printStackTrace();
              }
              // end of creation of articles table
   
            } else affiche("yes");
         } catch (SQLException e) {
             e.printStackTrace();
         }
         
         // inserting a record in the table articles     
         affiche("record creation");    
         request = "INSERT INTO articles VALUES (null, 'passoire', 4)";
         try {
            stmt = c.createStatement();
            nbMaj = stmt.executeUpdate(request);
            affiche("updates number = "+nbMaj);
         } catch (SQLException e) {
             e.printStackTrace();
         }
         
         // creation and execution of the request    
         affiche("creation and execution of the request");
         request = "SELECT * FROM articles";    
         try {
            stmt = c.createStatement();
            results = stmt.executeQuery(request);
         } catch (SQLException e) {
            arret("Anomaly when executing the query");
         }
         
         // reading of the data returned    
         affiche("reading of the data returned");
         try {
            ResultSetMetaData rsmd = results.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = results.next();
    
            while (encore) {
               for (int i = 1; i <= nbCols; i++)
                  System.out.print(results.getString(i) + " ");
               System.out.println();
               encore = results.next();
            }
    
            results.close();
         } catch (SQLException e) {
            arret(e.getMessage());
         }
    
         affiche("end of job");
         System.exit(0);
      }
    }
```