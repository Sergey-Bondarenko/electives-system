package com.epamtraining.filter;

import com.epamtraining.commands.ActionCommand;
import com.epamtraining.entities.Account;
import com.epamtraining.helper.ControllerHelper;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * command access filter
 */
public class CommandAccessFilter implements Filter {

    private PathManager pathManager = PathManager.INSTANCE;
    private Logger logger = Logger.getRootLogger();

    public void destroy() {
        //blank
    }

    /**
     * Checks the user role before executing command
     * If user doesn't have required role a 403 error
     * page will be displayed
     *
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        ControllerHelper requestHelper = ControllerHelper.INSTANCE;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        ActionCommand command = requestHelper.getCommand(request);

        Account account = AuthenticationService.account(request);

        if (command.checkAccess(account)){
            chain.doFilter(req, resp);
        } else{
            response.setStatus(403);
            logger.error(String.format("Access denied for %s to the following command: %s", (account != null) ? account : "anonymous user", command));
            request.getRequestDispatcher(pathManager.getString("path.error403")).forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // blank
    }

}
