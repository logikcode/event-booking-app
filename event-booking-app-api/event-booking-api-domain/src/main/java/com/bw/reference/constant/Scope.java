package com.bw.reference.constant;

public enum Scope {
    NOTIFICATION_SUBSCRIBE_WRITE("notification.sub.write",
            "Subscribe to notifications"),
    CREATE_USER("reference.user.write",
            "Create a new user"),
    SERVICE_CONSUME_NOTIFICATION("notification.service.consume",
            "Service consumption notification"),
    CREATE_CONTACT("contact.create", "Create contact"),
    VIEW_CONTACT("contact.view", "View contacts"),
    DELETE_CONTACT("contact.delete", "Delete contact");

    private final String code;
    private final String description;

    Scope(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

