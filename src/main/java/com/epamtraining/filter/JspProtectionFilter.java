package com.epamtraining.filter;

import com.epamtraining.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jsp pages protection filter
 */
public class JspProtectionFilter implements Filter {

    /**
     * path manager
     */
    private PathManager pathManager = PathManager.INSTANCE;

    /**
     * logger
     */
    private Logger logger = Logger.getRootLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // blank
    }

    /**
     * Protects from direct jsp page viewing
     * @param req
     * @param resp
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setStatus(404);
        logger.error("Someone tried to access jsp file directly");
        request.getRequestDispatcher(pathManager.getString("path.error404")).forward(req, resp);
    }

    @Override
    public void destroy() {
        // blank
    }
}
