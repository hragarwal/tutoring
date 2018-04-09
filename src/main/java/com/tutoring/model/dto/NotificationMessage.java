package com.tutoring.model.dto;

public class NotificationMessage {

    private int type;

    private Object data;

    private String title;

    private String message;

    /**
     *
     * @param type
     * @param title
     * @param message
     * @param data
     */
    public NotificationMessage(int type, String title, String message, Object data) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
