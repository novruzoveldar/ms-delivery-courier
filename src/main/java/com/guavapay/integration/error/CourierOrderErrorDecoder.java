package com.guavapay.integration.error;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class CourierOrderErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        CourierOrderException courierOrderException;
        try {
            courierOrderException = new CourierOrderException(methodKey, response);
        } catch (IOException ex) {
            throw new BaseException(ex.getMessage());
        }
        return courierOrderException;
    }
}
