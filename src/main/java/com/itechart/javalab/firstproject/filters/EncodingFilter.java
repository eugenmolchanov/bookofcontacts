package com.itechart.javalab.firstproject.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private String encoding;
    private String contentType;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
        contentType = "text/html";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        servletResponse.setContentType(contentType);
        servletResponse.setCharacterEncoding(encoding);
    }

    @Override
    public void destroy() {

    }
}
