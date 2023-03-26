package com.bw.reference.constant;

public enum ActivityLogActionConstant {
    LOGIN("Portal login"),
    LOGOUT("Portal logout out"),
    CREATE_EVENTS("CREATE EVENTS"),
    SEARCH_EVENTS("SEARCH EVENTS"),
    BOOKED_EVENTS("BOOKED EVENTS"),
    UPDATE_BOOKED_EVENTS("UPDATE BOOKED EVENTS"),
    DELETE_BOOKED_EVENT("BOOKED EVENT DELETED");

    private final String description;

    ActivityLogActionConstant(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}