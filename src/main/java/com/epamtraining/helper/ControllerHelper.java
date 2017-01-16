package com.epamtraining.helper;

import com.epamtraining.commands.*;
import com.epamtraining.commands.admin.*;
import com.epamtraining.commands.student.ActiveCoursesCommand;
import com.epamtraining.commands.student.ApplyCourseCommand;
import com.epamtraining.commands.student.LeaveCourseCommand;
import com.epamtraining.commands.student.StudentCoursesCommand;
import com.epamtraining.commands.teacher.AccountCommand;
import com.epamtraining.commands.teacher.CompleteCourseCommand;
import com.epamtraining.commands.teacher.EditRatingCommand;

import javax.servlet.ServletRequest;
import java.util.HashMap;

/**
 * ControllerHelper find command for main controller.
 * @author Sergey Bondarenko
 */
public enum ControllerHelper {

    INSTANCE;

    /**
    * Request parameter name for command
    */
    public static final String COMMAND_PARAMETER = "c";

    /**
     * action commands
     */
    private HashMap<String, ActionCommand> commands = new HashMap<String, ActionCommand>();
    {
            //Everyone commands
            commands.put("login", new LoginCommand());
            commands.put("logout", new LogoutCommand());
            commands.put("registration", new RegistrationCommand());
            commands.put("all_courses", new ViewCoursesCommand());
            commands.put("archive", new ArchiveCoursesCommand());
            //Admin commands
            commands.put("manager", new ManagerCommand());
            commands.put("add_course", new AddCourseCommand());
            commands.put("delete_course", new DeleteCourseCommand());
            commands.put("update_course", new EditCourseCommand());
            commands.put("remove_student", new RemoveStudentCommand());
            commands.put("accounts", new AccountsCommand());
            commands.put("add_account", new AddAccountCommand());
            commands.put("delete_account", new DeleteAccountCommand());
            commands.put("update_account", new EditAccountCommand());
            //Student commands
            commands.put("student", new StudentCoursesCommand());
            commands.put("leave_course", new LeaveCourseCommand());
            commands.put("courses", new ActiveCoursesCommand());
            commands.put("apply", new ApplyCourseCommand());
            //Teacher commands
            commands.put("teacher", new AccountCommand());
            commands.put("edit_rating", new EditRatingCommand());
            commands.put("end_course", new CompleteCourseCommand());
    }

    /**
     * Find command from request
     * @param request
     * @return ActionCommand
     */
    public ActionCommand getCommand(ServletRequest request){
        String action = request.getParameter(COMMAND_PARAMETER);
        return getCommand(action);
        }

    /**
     * Find command by name
     * @param action
     * @return ActionCommand
     */
    public ActionCommand getCommand(String action){
        ActionCommand command = commands.get(action);
        if (command == null){
            command = new EmptyCommand();
        }
        return command;
    }
}

