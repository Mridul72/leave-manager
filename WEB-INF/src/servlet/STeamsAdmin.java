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
    // set language
    Locale locale = req.getLocale(); 
    ResourceBundle res = ResourceBundle.getBundle("lang.LMTexts", locale);
    // data recollection
    Object o = req.getParameter("action");
    String action = (o != null) ? o.toString() : "";
    o = req.getParameter("team_id");
    int team_id = (o != null) ? Integer.valueOf(o.toString()) : 0;
    o = req.getParameter("team_name");
    String team_name = (o != null) ? o.toString() : "";
    String error = "";
    // refresh usersList
    UsersList users = new UsersList();
    users.addAll();
    req.setAttribute("users", users);
    // DELETE
    if (action.toString().equals("delete")) {
      if (team_id == 1)
        error = res.getObject("delete_default_error").toString();
      for (int i = 0 ; i < users.getSize() ; i ++) {
        if (users.getItem(i).getTeamId() == team_id)
          error = res.getObject("delete_used_error").toString();
      }
      if (error.equals("")) {
        Team.delete(team_id);
        team_id = 0;
        team_name = "";
        action = "";
      }
    }
    // UPDATE
    else if (action.toString().equals("update")) {
      if (team_id == 1)
        error = res.getObject("update_default_error").toString();
      else error = verif(team_name, res);
      if (error.equals("")) {
        Team team = new Team();
        team.setId(team_id);
        team.setName(team_name);
        collectManagers(req, team, users);
        team.updateTeamIntoDB();
        team_id = 0;
        team_name = "";
        action = "";
      }
    }
    // ADD
    else if (action.toString().equals("add")) {
      error = verif(team_name, res);
      if (error.equals("")) {
        Team team = new Team();
        team.setName(team_name);
        collectManagers(req, team, users);
        team.addTeamIntoDB();
        team_id = 0;
        team_name = "";
        action = "";
      }
    }
    // set Attributes
    req.setAttribute("action", action);
    req.setAttribute("team_id", team_id);
    req.setAttribute("team_name", team_name);
    if (!action.toString().equals("")) {
      String[] optmanagers = req.getParameterValues("optmanagers");
      req.setAttribute("optmanagers", optmanagers);
      String[] reqmanagers = req.getParameterValues("reqmanagers");
      req.setAttribute("reqmanagers", reqmanagers);
    }
    req.setAttribute("error", error);
    // refresh teamList
    TeamsList teams = new TeamsList();
    req.setAttribute("teams", teams);
    // send to JSP
    try {
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
  
  private String verif(String team_name, ResourceBundle res) {
    String expreg = "[\\p{Alnum}- àáâãäåçèéêëìíîïðòóôõöùúûüýÿ']{3,}";
    if (!team_name.matches(expreg)) {
      if (team_name.length() < 3)
        return res.getObject("name_too_short_error").toString();
      else
        return res.getObject("wrong_chars_error").toString();
    }
    return "";
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
}