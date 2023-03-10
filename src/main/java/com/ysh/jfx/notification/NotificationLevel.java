package com.ysh.jfx.notification;

public enum NotificationLevel {
    SUCCESS("success"), INFO("info"), WARNING("warning"), ERROR("error");
    
    NotificationLevel(String styleClass) {
        this.styleClass = styleClass;
    }
    
    private final String styleClass;
    
    public String getStyleClass() {
        return styleClass;
    }
    
}
