package com.epamtraining.servlet;

import com.epamtraining.commands.ActionCommand;
import com.epamtraining.exception.CommandException;
import com.epamtraining.helper.ControllerHelper;
import com.epamtraining.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.MissingResourceException;

/**
 * Servlet main controller
 * @author Sergey Bondarenko
 */
public class Controller extends HttpServlet {

    private final ControllerHelper controllerHelper = ControllerHelper.INSTANCE;
    private final Logger logger = Logger.getRootLogger();
    private String errorPagePath;

    @Override
    public void init() throws ServletException {
        try{
            errorPagePath = PathManager.INSTANCE.getString("path.error500");
        } catch (MissingResourceException e){
            logger.error(e.getMessage());
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = controllerHelper.getCommand(request);

        try{
            logger.info("Command: " + command.getClass().getSimpleName());
            String page = command.execute(request, response);
            if (page != null){
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException e) {
            if (errorPagePath != null){
                request.getRequestDispatcher(errorPagePath).forward(request, response);
            }

            logger.error(e.getMessage(), e);
        }
    }

}
