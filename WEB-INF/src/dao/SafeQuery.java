package dao;

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
  
  public void setPreparedQuery(String query) {
    preparedquery = query;
  }
  
  public String getPreparedQuery() {
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
    try {
      return datalist.get(i);
    } catch (IndexOutOfBoundsException ex) {}
    return null;
  }
  
  public String getDataType(int i) {
    try {
      return datatypes.get(i);
    } catch (IndexOutOfBoundsException ex) {}
    return null;
  }
}
