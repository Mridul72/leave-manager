package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetEncodingFilter implements Filter {
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
      throws IOException, ServletException {
    
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");
    
    chain.doFilter(req, resp);
  }

  public void destroy() {
  }
}
