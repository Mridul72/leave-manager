package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Team;
import model.TeamsList;
import model.UsersList;

public class STeamsAdmin extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    try {
      Object action = req.getParameter("action");
      if (req.getAttribute("teams") == null) {
        System.err.println("re");
        TeamsList teams = new TeamsList();
        req.setAttribute("teams", teams);
      }
      if (req.getAttribute("users") == null) {
        UsersList users = new UsersList();
        users.addAll();
        req.setAttribute("users", users);
      }

//    if (action != null && action.toString().equals("add")) {
//      String[] res = request.getParameterValues("members" );
//      for (int i = 0; i < res.length; ++i){
//        System.out.println(res[i]);
//      } 
//    } else if (action != null && action.toString().equals("delete")) {
//      
//    }
    


      if (action != null && action.toString().equals("update")) {
        int id = Integer.parseInt(req.getParameter("team_id").toString());
        Team t = ((TeamsList) req.getAttribute("teams")).getTeam(id);
        req.setAttribute("action", "update");
        req.setAttribute("team_id", t.getId());
        req.setAttribute("team_name", t.getName());
      }

      RequestDispatcher dispatch = req.getRequestDispatcher("teamsAdmin.jsp");
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