package com.manunin.score.exception;

public class ServiceException extends RuntimeException {
    private final ResultType type;
    private final String message;
    private final Object[] params;

    public ServiceException(final ResultType type, final String message, final Object... params) {
        super(message);
        this.type = type;
        this.message = message;
        this.params = params;
    }

    public ResultType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object[] getParams() {
        return params;
    }
}

