package by.training.finaltask.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter that sets page and character UTF-8 encoding.
 */
public final class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
