package com.company.template.server.web.handlers.exceptions;

import lombok.Getter;

@Getter
public class UniqueFieldException extends RuntimeException {

    private static final long serialVersionUID = -2308752260602001806L;

    public final String field;
    public final String rejectedValue;

    public UniqueFieldException(String message, String field, String rejectedValue) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
}
