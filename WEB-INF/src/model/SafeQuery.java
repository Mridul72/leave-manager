package model;

import java.sql.Date;
import java.util.ArrayList;

public class SafeQuery {
  private String preparedquery = new String();
  private ArrayList<Object> datalist = new ArrayList<Object>();
  private ArrayList<String> datatypes = new ArrayList<String>();
  
  public SafeQuery() {}
  
  public void reset() {
    preparedquery = "";
    datalist.clear();
    datatypes.clear();
  }
  
  public void setPreparedquery(String query) {
    preparedquery = query;
  }
  
  public String getPreparedquery() {
    return preparedquery;
  }
  
  public void addArgument(int data) {
    datalist.add(data);
    datatypes.add("int");
  }
  
  public void addArgument(String data) {
    datalist.add(data);
    datatypes.add("String");
  }
  
  public void addArgument(Date data) {
    datalist.add(data);
    datatypes.add("Date");
  }
  
  public int getSize() {
    return datalist.size();
  }
  
  public Object getData(int i) {
    return datalist.get(i);
  }
  
  public String getDataType(int i) {
    return datatypes.get(i);
  }
}
