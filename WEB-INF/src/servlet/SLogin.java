package servlet;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.UserRights;

public class SLogin extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    Locale locale = req.getLocale(); 
    ResourceBundle res = ResourceBundle.getBundle("lang.LMTexts", locale);
    
    try {
      // retrieves the identifiers
      String login = req.getParameter("login");
      String password = req.getParameter("password");
      int user_id;
      // check identifiers
      if (!login.isEmpty() && !password.isEmpty()
          && (user_id = Login.checkPassword(login, password)) != -1) {
        // create session
        HttpSession session = req.getSession();
        UserRights rights = new UserRights(user_id);
        session.setAttribute("r", rights);
        // redirect to personal stats
        req.getRequestDispatcher("showhome.do").forward(req, resp);
      } else {
        // login fail, return to the login page
        req.setAttribute("error", res.getObject("login_error"));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
      }
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    } catch (ServletException ex) {
      System.err.println(ex.getMessage());
    } catch (NullPointerException ex) {
      System.err.println(ex.getMessage());
    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  
    doGet(req, resp);
  }
}