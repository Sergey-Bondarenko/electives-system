package com.epamtraining.notification;

/**
 *  Flash notification
 *  @author Sergey Bondarenko
 */
public class Notification {
    public enum Type{
        INFO, WARNING, ERROR
    }

    public Notification(String message) {
        this.message = message;
        this.type = Type.INFO;
    }

    public Notification(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    private String message;

    /**
     * notification type
     */
    private Type type;

    /**
     * get notification getMessage
     * @return
     */
    public String getMessage(){
        return message;
    }


    /**
     * sets type
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * gets type
     * @return
     */
    public Type getType() {
        return type;
    }
}
