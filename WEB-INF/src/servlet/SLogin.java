package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

public class SLogin extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    // retrieves the identifiers
    String login = req.getParameter("login");
    String password = req.getParameter("password");
    // check identifiers
    if (User.checkPassword(login, password)) {
      // they are stored in our session object
      HttpSession session = req.getSession();
      session.setAttribute("login", login);
      session.setAttribute("password", password);
      // now we will use this object to another servlet
      req.setAttribute("error", "Login ok: welcome admin !");
      req.getRequestDispatcher("index.jsp").forward(req, resp);
      //resp.sendRedirect(resp.encodeURL("showhome.do"));
    } else {
      // login fail, return to the login page
      req.setAttribute("error", "Login failed: login or password incorrect");
      req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  
    doGet(req, resp);
  }
}