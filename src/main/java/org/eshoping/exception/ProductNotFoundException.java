package org.eshoping.exception;

public class ProductNotFoundException extends RuntimeException {

    private final String error;

    public ProductNotFoundException(String err) {
        super(err);
        this.error = err;
    }

    public String getMessage(){
        return error;
    }
}
