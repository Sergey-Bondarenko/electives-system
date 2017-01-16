package com.epamtraining.notification;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Iterator;

/**
 * Renders out notifications stored in session
 * Example of usage:
 *
 */
public class FlashesTag extends BodyTagSupport {
    private static final String MESSAGE = "{message}";
    private static final String TYPE = "{type}";
    private BodyContent bodyContent = null;
    private String pattern = null;
	private Iterator<Notification> iterator;

    @Override
    public int doStartTag() throws JspException {
        iterator = NotificationService.getNotifications(pageContext.getSession()).iterator();

        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        bodyContent = getBodyContent();

        if (pattern == null){
            String rawPattern = getBodyContent().getString();
            if (rawPattern == null || rawPattern.trim().isEmpty()){
                return SKIP_BODY;
            }

            pattern = rawPattern;
        }

        //While have notifications
        if (iterator != null && iterator.hasNext()){

            Notification notification = iterator.next();
            iterator.remove();
			if (notification != null){
            String message = formatMessage(notification);

            if (message != null){
                try {
                    bodyContent.getEnclosingWriter().write(message);
                } catch (IOException e) {
                }
            }
			}
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    /**
     * Create a getMessage from pattern
     * @param notification
     * @return
     */
    private String formatMessage(Notification notification){

        StringBuilder content = new StringBuilder(pattern);
        String message = notification.getMessage();

        if (message != null && content.length() > 0){

            // Replace {type} with notification type
            int i = content.indexOf(TYPE);
            if (i > 0){ content.replace(i, i + TYPE.length(), notification.getType().toString().toLowerCase()); }

            // Replace {getMessage} with actual getMessage
            i = content.indexOf(MESSAGE);
            if (i > 0){ content.replace(i, i + MESSAGE.length(), message); }

            return content.toString();
        }

        return null;
    }
}
