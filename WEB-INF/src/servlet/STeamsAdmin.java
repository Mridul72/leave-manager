package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Team;
import model.TeamsList;
import model.User;
import model.UsersList;

public class STeamsAdmin extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    Object action = req.getParameter("action");
    UsersList users = new UsersList();
    users.addAll();
    req.setAttribute("users", users);
    if (action != null && action.toString().equals("delete")) {
      
    }
    if (action != null && action.toString().equals("add")) {
      Team team = new Team();
      String team_name = req.getParameter("team_name");
      if (!verif(team_name, req, resp)) return;
      team.setName(team_name);
      String[] m = req.getParameterValues("managers");
      UsersList managers = new UsersList();
      for (int i = 0; i < m.length; i++){
        int user_id = Integer.valueOf(m[i]);
        User u = users.getUser(user_id);
        managers.addUser(u);
      }
      team.setManagers(managers);
      team.putTeamIntoDB();
    }
    else if (action != null && action.toString().equals("update")) {

    }
    redirect(req, resp);
  }

  private boolean verif(String team_name, HttpServletRequest req, 
                                       HttpServletResponse resp) {
    String expreg = "[\\p{Alnum}-']{3,}";
    if (!team_name.matches(expreg)) {
      req.setAttribute("error", "le nom de votre équipe ne doit contenir" +
                       " que des caractères alphanumériques ou ' et -");
      req.setAttribute("action", req.getParameter("action"));
      req.setAttribute("team_id", req.getParameter("team_id"));
      req.setAttribute("team_name", req.getParameter("team_name"));
      String[] managers = req.getParameterValues("managers");
      req.setAttribute("managers", managers);
      
      redirect(req, resp);
      return false;
    }
    return true;
  }
  
  private void redirect(HttpServletRequest req, HttpServletResponse resp) {
    try {
      TeamsList teams = new TeamsList();
      req.setAttribute("teams", teams);
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