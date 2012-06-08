package servlet;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
    
    Locale locale = req.getLocale(); 
    ResourceBundle res = ResourceBundle.getBundle("lang.LMTexts", locale);
    Object action = req.getParameter("action");
    UsersList users = new UsersList();
    users.addAll();
    req.setAttribute("users", users);
    
    if (action != null && action.toString().equals("delete")) {
      int team_id = Integer.valueOf(req.getParameter("team_id"));
      if (team_id == 1) {
        req.setAttribute("error", res.getObject("delete_default_error"));
        redirect(req, resp);
      }
      for (int i = 0 ; i < users.getSize() ; i ++) {
        if (users.getItem(i).getTeamId() == team_id) {
          req.setAttribute("error", res.getObject("delete_used_error"));
          redirect(req, resp);
        }
      }
      Team.delete(team_id);
    }
    else if (action != null && action.toString().equals("update")) {
      int team_id = Integer.valueOf(req.getParameter("team_id"));
      if (team_id == 1) {
        req.setAttribute("error", res.getObject("update_default_error"));
        redirect(req, resp);
      }
      String team_name = req.getParameter("team_name");
      if (!verif(team_name, req, resp, res)) return;
      Team team = new Team();
      team.setId(team_id);
      team.setName(team_name);
      collectManagers(req, team, users);
      team.updateTeamIntoDB();
    }
    else if (action != null && action.toString().equals("add")) {
      String team_name = req.getParameter("team_name");
      if (!verif(team_name, req, resp, res)) return;
      Team team = new Team();
      team.setName(team_name);
      collectManagers(req, team, users);
      team.addTeamIntoDB();
    }

    redirect(req, resp);
  }

  private boolean verif(String team_name, HttpServletRequest req, 
                        HttpServletResponse resp, ResourceBundle res) {

    String expreg = "[\\p{Alnum}-']{3,}";
    if (!team_name.matches(expreg)) {
      if (team_name.length() < 3)
        req.setAttribute("error", res.getObject("name_too_short_error"));
      else
        req.setAttribute("error", res.getObject("wrong_chars_error"));
      
      req.setAttribute("action", req.getParameter("action"));
      req.setAttribute("team_id", req.getParameter("team_id"));
      req.setAttribute("team_name", req.getParameter("team_name"));
      String[] optmanagers = req.getParameterValues("optmanagers");
      req.setAttribute("optmanagers", optmanagers);
      String[] reqmanagers = req.getParameterValues("reqmanagers");
      req.setAttribute("reqmanagers", reqmanagers);
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
  
  private void collectManagers(HttpServletRequest req, Team team, 
                                                               UsersList users){
    String[] rm = req.getParameterValues("reqmanagers");
    UsersList reqmanagers = new UsersList();
    if (rm != null)
      for (int i = 0; i < rm.length; i++){
        int user_id = Integer.valueOf(rm[i]);
        User u = users.getUser(user_id);
        reqmanagers.addUser(u);
      }
    team.setReqManagers(reqmanagers);
    String[] om = req.getParameterValues("optmanagers");
    UsersList optmanagers = new UsersList();
    if (om != null)
      for (int i = 0; i < om.length; i++){
        int user_id = Integer.valueOf(om[i]);
        User u = users.getUser(user_id);
        optmanagers.addUser(u);
      }
    team.setOptManagers(optmanagers);
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  
    doGet(req, resp);
  }
}