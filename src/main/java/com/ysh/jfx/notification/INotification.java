package com.ysh.jfx.notification;

public interface INotification {
    public Notification success(String message);
    
    public Notification success(String title, String message);
    
    public Notification success(String title, String message, int duration);
    
    public Notification info(String message);
    
    public Notification info(String title, String message);
    
    public Notification info(String title, String message, int duration);
    
    public Notification warning(String message);
    
    public Notification warning(String title, String message);
    
    public Notification warning(String title, String message, int duration);
    
    public Notification error(String message);
    
    public Notification error(String title, String message);
    
    public Notification error(String title, String message, int duration);
}
