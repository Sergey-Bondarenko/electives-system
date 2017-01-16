package com.epamtraining.notification;

import com.epamtraining.resource.MessageManager;

import java.util.Locale;

/**
 * Simplifies notification creation
 */
public class NotificationCreator {

    /**
     * Create notification by type and property key
     *
     *
     * @param propertyName
     * @param type
     * @return
     */
    public static Notification createFromProperty(String propertyName, Notification.Type type, Locale locale){

        Notification notification = new Notification("????? " + propertyName + "??????", type);

        String message = MessageManager.INSTANCE.getMessage(propertyName, locale);
        if (message != null){
            notification = new Notification(message, type);
        }

        return notification;
    }

    /**
     * Create notification by type and property key
     *
     * @param propertyName
     * @return
     */
    public static Notification createFromProperty(String propertyName, Locale locale){

        return createFromProperty(propertyName, Notification.Type.INFO, locale);
    }
}
