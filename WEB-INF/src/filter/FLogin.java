package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FLogin implements Filter {

  private ArrayList<String> urlList;
  
  public void destroy() { }

  public void doFilter(ServletRequest req, ServletResponse resp,
    FilterChain chain) throws IOException, ServletException {
    
    try {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) resp;
      String url = request.getServletPath();
      if (!urlList.contains(url)) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("r") == null) {
          System.err.println("identification fail");
          response.sendRedirect("index.jsp");
        }
      }
      chain.doFilter(req, resp);
    
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    } catch (ServletException ex) {
      System.err.println(ex.getMessage());
    }
  }

  public void init(FilterConfig config) throws ServletException {
    
    String urls = config.getInitParameter("avoid-urls");
    StringTokenizer token = new StringTokenizer(urls, ",");

    urlList = new ArrayList<String>();

    while (token.hasMoreTokens()) {
      urlList.add(token.nextToken());
    }
  }
}
