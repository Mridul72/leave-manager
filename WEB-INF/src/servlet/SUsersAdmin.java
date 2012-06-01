package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.UsersList;

public class SUsersAdmin extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    Object action = req.getParameter("action");

    if (action != null && action.toString().equals("add")) {
//      String user_name = req.getParameter("user_name").toString();
//      String surname = req.getParameter("surname").toString();
    } else if (action != null && action.toString().equals("delete")) {
      
    }
    
    try {
      UsersList list = new UsersList();
      req.setAttribute("users", list.getList());
      if (action != null && action.toString().equals("update")) {
        int id = Integer.parseInt(req.getParameter("id").toString());
        User u = list.getUser(id);
        req.setAttribute("user_id", u.getId());
        req.setAttribute("user_name", u.getName());
        req.setAttribute("surname", u.getName());
        req.setAttribute("gender", u.getGender());
      }
      RequestDispatcher dispatch = req.getRequestDispatcher("usersAdmin.jsp");
      dispatch.forward(req, resp);
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    } catch (ServletException ex) {
      System.err.println(ex.getMessage());
    }
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  
    doGet(req, resp);
  }
}