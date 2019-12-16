package com.mobAppWS.ui.model.response;

import java.util.Date;

public class ErrorMessage {
    private Date date;
    private String message;

    public ErrorMessage() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
