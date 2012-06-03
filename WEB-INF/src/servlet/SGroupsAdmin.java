package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Group;
import model.GroupsList;
import model.UsersList;

public class SGroupsAdmin extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    Object action = req.getParameter("action");

    if (action != null && action.toString().equals("add")) {
//      String[] res = request.getParameterValues("members" );
//      for (int i = 0; i < res.length; ++i){
//        System.out.println(res[i]);
//      } 
    } else if (action != null && action.toString().equals("delete")) {
      
    }
    
    try {
      GroupsList groups = new GroupsList();
      req.setAttribute("groups", groups.getList());
      if (action != null && action.toString().equals("update")) {
        int id = Integer.parseInt(req.getParameter("id").toString());
        Group g = groups.getGroup(id);
        req.setAttribute("group_id", g.getId());
        req.setAttribute("group_name", g.getName());
      }
      UsersList users = new UsersList();
      users.addAll();
      req.setAttribute("users", users.getList());
      RequestDispatcher dispatch = req.getRequestDispatcher("groupsAdmin.jsp");
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