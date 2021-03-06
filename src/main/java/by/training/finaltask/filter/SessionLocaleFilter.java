package by.training.finaltask.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter that detects and sets language/locale of the web page content.
 */
public final class SessionLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getParameter("lang") != null) {
            req.getSession().setAttribute("sessionLang", req.getParameter("lang"));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
