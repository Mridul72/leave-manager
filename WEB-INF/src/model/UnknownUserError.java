package model;

public class UnknownUserError extends Exception {
  private int user_id;
  private String user_login;
  
  public UnknownUserError(int id) {
    user_id = id;
    user_login = null;
  }
  public UnknownUserError(String login) {
    user_login = login;
  }
  
  public String getMessage() {
    if (user_login != null)
      return "unknown user login : " + user_login;
    else return "unknown user id : " + user_id;
  }
}
