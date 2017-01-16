package com.epamtraining.listener;

import com.epamtraining.connection.JdbcConnectionPool;
import com.epamtraining.exception.ConnectionPoolException;
import com.epamtraining.resource.LocaleManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Locale;

public class ApplicationListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener {

    private Logger logger = Logger.getRootLogger();
    private LocaleManager localeManager = LocaleManager.INSTANCE;


    // Public constructor is required by servlet spec
    public ApplicationListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        context.setAttribute("locales", LocaleManager.INSTANCE.getLocales());

        Locale.setDefault(Locale.ENGLISH);

    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            JdbcConnectionPool connectionPool = JdbcConnectionPool.getInstance();
            connectionPool.shutDown();
        } catch (ConnectionPoolException e) {
            logger.error(e.getMessage());
        }
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {

    }



    public void sessionDestroyed(HttpSessionEvent se) {

    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest req = servletRequestEvent.getServletRequest();
        Locale locale = localeManager.resolveLocale(req);
        req.setAttribute("locale", locale);
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getQueryString() != null) {
            String query = request.getQueryString().replaceAll("&lang...", "");
            request.setAttribute("query", query);
        }
    }
}
